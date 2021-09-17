package com.gosha.kalosha.hauzijan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController
{
    @RequestMapping("error")
    public ResponseEntity<Map<String, String>> handleUnknownError()
    {
        return new ResponseEntity<>(Map.of("error", "Unknown error"), HttpStatus.NOT_FOUND);
    }
}
