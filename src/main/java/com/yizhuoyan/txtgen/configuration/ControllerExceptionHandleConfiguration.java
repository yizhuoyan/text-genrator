package com.yizhuoyan.txtgen.configuration;

import com.yizhuoyan.common.Maps;
import com.yizhuoyan.common.exception.ThisApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandleConfiguration {

    @ExceptionHandler
    public ResponseEntity<Map> handleAllException(Throwable e){
        e.printStackTrace();
        return ResponseEntity.status(500).body(Maps.of( "code", "unknown","message", e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map> handleAllException(ThisApplicationException  e){
        return ResponseEntity.status(400).body(Maps.of("code", e.getCode(), "message", e.getMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<Map> handleConstraintViolationException(ConstraintViolationException e){
        StringBuilder message=new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            message.append(constraintViolation.getMessage()).append("\n");
        }
        return ResponseEntity.status(400).body(Maps.of("code", "violation", "message",message));
    }

}