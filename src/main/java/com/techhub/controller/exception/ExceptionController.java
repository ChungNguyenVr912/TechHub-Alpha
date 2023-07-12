package com.techhub.controller.exception;

import com.techhub.aspect.LoggingAspect;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        e.printStackTrace();
        logger.error("Error"+ e.getMessage() + e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleException(DataIntegrityViolationException e){
        logger.error("Error"+ e);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public void handleException(ExpiredJwtException e){
        e.printStackTrace();
    }
}
