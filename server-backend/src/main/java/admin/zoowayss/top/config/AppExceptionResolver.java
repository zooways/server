package admin.zoowayss.top.config;

import admin.zoowayss.top.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AppExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ModelAndView mv = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        mv.setView(view);
        int statusCode = 500;
        if (ex instanceof BindException) {
            BindException e = ((BindException) ex);
            Map<String, String> errors = new HashMap<>();
            mv.addObject("code", "400");
            e.getAllErrors().forEach(error -> {
                String field = ((FieldError) error).getField();
                String defaultMessage = error.getDefaultMessage();
                mv.addObject("msg", field + ":" + defaultMessage);
                errors.put(field, defaultMessage);
            });
            statusCode = 200;
        } else if (ex instanceof BusinessException || ex instanceof IllegalArgumentException) {
            mv.addObject("code", "error");
            mv.addObject("success", true);
            mv.addObject("msg", ex.getMessage());
            statusCode = 200;
        } else if (ex instanceof InvalidDataAccessResourceUsageException) {
            log.error("server db error", ex);
            mv.addObject("msg", "server db error");
        } else {
            log.error("Request exception for "
                            + request.getMethod()
                            + " "
                            + request.getRequestURI()
                            + (request.getQueryString() == null ? "" : "?" + request.getQueryString())
                    , ex);
            mv.addObject("msg", "internal error");
            mv.addObject("code", "500");
            mv.addObject("data", null);
            mv.addObject("success", false);
        }
        mv.setStatus(HttpStatus.resolve(statusCode));
        return mv;
    }
}
