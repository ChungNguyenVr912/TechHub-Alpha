package com.techhub.aspect;

import com.techhub.dto.UserRegisterDto;
import com.techhub.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    static {
        PropertyConfigurator.configure("src/main/resources/log4j_config/log4j.properties");
    }
    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);
    @Pointcut("execution(* com.techhub.controller.exception.ExceptionController.handleException(..))")
    public void exceptionMethod(){}
    @Pointcut("execution(* com.techhub.service.impl.UserServiceImpl.save(..))")
    public void saveUserMethod(){}

    @Before("exceptionMethod()")
    public void exceptionLogging(JoinPoint joinPoint){
        Exception e = (Exception) joinPoint.getArgs()[0];
        logger.error("Final ex handler: "+e);
    }
    @Before("saveUserMethod()")
    public void newUserLogging(JoinPoint joinPoint){
        try {
            UserRegisterDto user = (UserRegisterDto) joinPoint.getArgs()[0];
            logger.info("Trying to create user:  " + user.getUsername());
        }catch (Exception e){
            logger.error(e);
        }
    }
    @AfterReturning("saveUserMethod()")
    public void newUserLogging(){
            logger.info("New user created!" );
    }
    @AfterThrowing(value = "saveUserMethod()", throwing = "e")
    public void createUserFailLogging(Exception e){
        logger.info("Fail to create user!" + e);
    }
}
