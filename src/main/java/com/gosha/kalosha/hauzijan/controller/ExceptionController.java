package com.gosha.kalosha.hauzijan.controller;

import com.gosha.kalosha.hauzijan.exception_handing.NoSentencesFoundException;
import com.gosha.kalosha.hauzijan.exception_handing.NoWordsFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;

@ControllerAdvice
public class ExceptionController
{
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleNoSentencesFound(NoSentencesFoundException e)
    {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleNoWordsFound(NoWordsFoundException e)
    {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleUnknownError(NoHandlerFoundException e)
    {
        return new ResponseEntity<>(Map.of("error", "Page not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleIllegalState(IllegalStateException e)
    {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e)
    {
        return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}

