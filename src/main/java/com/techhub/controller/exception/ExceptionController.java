package com.techhub.controller.exception;

import com.techhub.aspect.LoggingAspect;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e){
        System.err.println("An error occurred!");
        e.printStackTrace();
        logger.error("Error"+ e);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleException(DataIntegrityViolationException e){
        System.err.println("An error occurred!");
        e.printStackTrace();
        logger.error("Error"+ e);
    }
}
