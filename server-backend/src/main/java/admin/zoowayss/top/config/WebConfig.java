package admin.zoowayss.top.config;

import admin.zoowayss.top.service.AdminUserService;
import admin.zoowayss.top.token.TokenHandlerInterceptor;
import admin.zoowayss.top.token.TokenMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Resource
    private CoreProperties coreProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(true).allowedMethods("GET", "POST", "PUT", "DELETE").maxAge(3600);
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new AppExceptionResolver());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new TokenMethodArgumentResolver());
        resolvers.add(new PageableHandlerMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TokenHandlerInterceptor adminInterceptor = (TokenHandlerInterceptor) applicationContext.getBean("adminTokenHandlerInterceptor");
//        SecretKeyHandlerInterceptor secretKeyHandlerInterceptor = (SecretKeyHandlerInterceptor) applicationContext.getBean("secretKeyHandlerInterceptor");
        coreProperties.getAnonymousPathPatterns().add("/user/login/**");
        coreProperties.getAnonymousPathPatterns().add("/admin/user/login/**");
        registry.addInterceptor(adminInterceptor)
                .excludePathPatterns(coreProperties.getAnonymousPathPatterns());
    }
    @Bean("adminTokenHandlerInterceptor")
    public TokenHandlerInterceptor adminTokenHandlerInterceptor(AdminUserService tokenVerifier) {
        TokenHandlerInterceptor interceptor = new TokenHandlerInterceptor(Arrays.asList(tokenVerifier));
        interceptor.setTokenHeaderName(Constants.ADMIN_TOKEN_HEADER);
        interceptor.setInterceptByDefault(true);
        interceptor.setCheckRole(true);
        interceptor.setIncludePrefix("/admin");
        return interceptor;
    }
}
