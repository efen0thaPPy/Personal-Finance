package com.n0thaPPy.PersonalFinance.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BalanceInsufficient.class)
    public ResponseEntity<?>handleInsufficientBalance(BalanceInsufficient ex)
    {
       Map<String,Object> error=new HashMap<>();
       error.put("errorCode","Insufficient Balance");
       error.put("message",ex.getMessage());
       error.put("status", HttpStatus.BAD_REQUEST.value());
       error.put("timestamp", LocalDate.now());

       return ResponseEntity.badRequest().body(error);

    }
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<?>handleUsernameNotFound(UserNotFound ex)
    {
        Map<String,Object> error=new HashMap<>();
        error.put("errorCode","Username Not Found");
        error.put("message",ex.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("timestamp", LocalDate.now());

        return ResponseEntity.badRequest().body(error);
    }


}
