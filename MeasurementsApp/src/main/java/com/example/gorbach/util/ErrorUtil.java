package com.example.gorbach.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;


public class ErrorUtil {
    public static void returnErrorsToClient(BindingResult result) {
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append(";");
        }

        throw new MeasurementException(errorMsg.toString());
    }
}