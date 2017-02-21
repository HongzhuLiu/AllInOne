package com.lhz.server.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

@Aspect
@Component
public class RestLogAspect {
    @Autowired
    private HttpSession session;
    private static final Logger logger = LoggerFactory.getLogger(RestLogAspect.class);

//    @Pointcut("execution(* com.lhz.controller..*Controller (..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void webLog() {}

    @Around("webLog()")
    public Object intercept(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod(); //获取被拦截的方法
        String methodName = method.getName(); //获取被拦截的方法名
        Object[] args = joinPoint.getArgs();
        logger.debug("-------------------- 请求方法 :  {} 开始-------------------", methodName);
        Object result = null;
        try {
            if (args == null) {
                result = joinPoint.proceed();
            } else {
                result = joinPoint.proceed(args);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        logger.debug("-------------------- 请求方法 :  {} 结束, 耗时 {} ms -------------------\n", methodName, (System.currentTimeMillis() - startTime));

        return result;
    }
}
