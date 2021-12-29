package com.egs.atm.aop;

import com.egs.atm.utils.Utils;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;


@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * @param joinPoint trigger of any request on Controller package
     *                  log request info
     */
    @SneakyThrows
    @Around("execution(* com.egs.atm.controller.*.*(..))")
    public ResponseEntity<?> logger(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        Map<String, Object> args = new LinkedHashMap<>();
        int i = 0;
        for (String parameterName : codeSignature.getParameterNames()) {
            Object arg = joinPoint.getArgs()[i];
            if (parameterName.equalsIgnoreCase("cvv")) {
                arg = "***";
            }
            if (parameterName.equalsIgnoreCase("cardNo")) {
                arg = Utils.maskCardNumber(arg.toString());
            }
            if (!parameterName.equalsIgnoreCase("session")) {
                args.put(parameterName, arg);
            }
            i++;
        }

        if (request != null) {
            LOGGER.info("=============Received Http Request============");
            LOGGER.info("Http Method = {}", request.getMethod());
            LOGGER.info("URI = {}", request.getRequestURI());
            LOGGER.info("CLASS Method = {}", joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getName());
            LOGGER.info("ARGS = {}", args);
            LOGGER.info("REQUESTER IP = {}", request.getRemoteAddr());
        }
        ResponseEntity<?> response = (ResponseEntity<?>) joinPoint.proceed();

        LOGGER.info("Http Response = {}", response.getBody());

        return response;
    }
}