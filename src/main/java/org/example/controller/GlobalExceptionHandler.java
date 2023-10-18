package org.example.controller;

import org.example.utils.ApiException;
import org.example.utils.ApiResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = {"org.example"})
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public @ResponseBody
    ApiResponse<String> handleException(ApiException ex) {
        return new ApiResponse<>(ex.getMessage());
    }

}
