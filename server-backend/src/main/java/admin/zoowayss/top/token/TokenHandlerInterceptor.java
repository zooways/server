package admin.zoowayss.top.token;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TokenHandlerInterceptor implements HandlerInterceptor {

    public String tokenHeaderName = "Authorization";
    public static final String USER_ID_NAME = "pr.token.user.id";
    private String unauthorizedPrompt = "{\"success\":false, \"code\":\"401\", \"msg\": \"Unauthorized\"}";
    private boolean interceptByDefault = false;

    private boolean checkRole;
    private String includePrefix;
    private String excludePrefix;
    private List<TokenVerifier> verifier;

    public void addTokenVerifier(TokenVerifier tokenVerifier) {
        this.verifier.add(tokenVerifier);
    }

    public TokenHandlerInterceptor(List<TokenVerifier> verifier) {
        this.verifier = verifier;
    }

    public void setTokenHeaderName(String tokenHeaderName) {
        this.tokenHeaderName = tokenHeaderName;
    }

    public void setUnauthorizedPrompt(String unauthorizedPrompt) {
        this.unauthorizedPrompt = unauthorizedPrompt;
    }

    public void setInterceptByDefault(boolean interceptByDefault) {
        this.interceptByDefault = interceptByDefault;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            if (includePrefix != null && !request.getRequestURI().startsWith(includePrefix)) {
                return true;
            }
            if (excludePrefix != null && request.getRequestURI().startsWith(excludePrefix)) {
                return true;
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            boolean flag = interceptByDefault; // 是否默认校验token
            boolean isOptional = false;
            if (!flag) {
                for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
                    if (methodParameter.getParameterType().isAssignableFrom(TokenUser.class)) {
                        isOptional = methodParameter.hasParameterAnnotation(Optional.class);
                        flag = true;
                        break;
                    }
                }
            }
            if (flag) {
                String token = null;
                if (request.getCookies() != null) {
                    for (Cookie cookie : request.getCookies()) {
                        if (tokenHeaderName.equals(cookie.getName())) {
                            token = cookie.getValue();
                            break;
                        }
                    }
                }
                if (token == null) {
                    token = request.getHeader(tokenHeaderName);
                }
                if (!StringUtils.hasText(token)) {
                    token = request.getParameter(tokenHeaderName);
                }
                String userId = null;
                for (TokenVerifier tokenVerifier : verifier) {
                    if (token == null && isOptional == false) {
                        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                        response.getWriter().write(unauthorizedPrompt);
                        return false;
                    }
                    userId = tokenVerifier.verifyAndGetUserId(token);
                    if (userId != null) {
                        break;
                    }
                }
                if (userId == null) {
                    response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write(unauthorizedPrompt);
                    return false;
                }

                request.setAttribute(USER_ID_NAME, userId);
                if (checkRole) {
                    RoleRequired roleRequired = handlerMethod.getMethodAnnotation(RoleRequired.class);
                    if (roleRequired == null) {
                        roleRequired = AnnotatedElementUtils.findMergedAnnotation(handlerMethod.getBeanType(), RoleRequired.class);
                    }
                    if (roleRequired != null) {
                        boolean hasRole = false;
                        Set<String> roles = new HashSet<>();
                        for (TokenVerifier tokenVerifier : verifier) {
                            roles.addAll(tokenVerifier.getRoles(token));
                        }
                        if (!roles.isEmpty()) {
                            for (String role : roleRequired.value()) {
                                if (roles.contains(role)) {
                                    hasRole = true;
                                    break;
                                }
                            }
                        }
                        if (!hasRole) {
                            response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write(unauthorizedPrompt);
                            return false;
                        }
                    }
                }

                return true;
            }
        }
        return true;

    }

    public void setIncludePrefix(String includePrefix) {
        this.includePrefix = includePrefix;
    }

    public String getIncludePrefix() {
        return includePrefix;
    }

    public void setExcludePrefix(String excludePrefix) {
        this.excludePrefix = excludePrefix;
    }

    public String getExcludePrefix() {
        return excludePrefix;
    }

    public boolean isCheckRole() {
        return checkRole;
    }

    public void setCheckRole(boolean checkRole) {
        this.checkRole = checkRole;
    }
}
