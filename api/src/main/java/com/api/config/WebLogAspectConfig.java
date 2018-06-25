package com.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Aspect
@Component
public class WebLogAspectConfig {

    static Logger logger = LoggerFactory.getLogger(WebLogAspectConfig.class);

    static ObjectMapper mapper = new ObjectMapper();

    @Pointcut("execution(public * com.*.*.*.api.controller..*.*(..))")
    public void weblog() { }

    @Before("weblog()")
    public void doBefore(JoinPoint joinpoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("Started " + request.getMethod() + " "
                + request.getRequestURL().toString() + " for "
                + request.getRemoteAddr() + " at " + LocalDateTime.now());
        logger.info("Processing by " + joinpoint.getSignature().getDeclaringTypeName() + "." + joinpoint.getSignature().getName()
                + " " + request.getContentType());
        logger.info("Parameters: " + mapper.writeValueAsString(request.getParameterMap()));
    }

    @AfterReturning(returning = "ret", pointcut = "weblog()")
    public void doAfterReturning(Object ret) throws Throwable {
    }

}
