package org.sandbox.api;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.sandbox.config.Messages;
import org.sandbox.domain.ErrorCollector;
import org.sandbox.domain.ErrorCollectorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerAssist {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }

    @ExceptionHandler({ErrorCollectorException.class})
    protected ResponseEntity<Object> handleErrorCollectorException(ErrorCollectorException exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<>(exception.getErrorCollector(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<Object> handleJsonMappingException(HttpMessageNotReadableException exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (exception.getCause() instanceof InvalidFormatException) {
            ErrorCollector errorCollector = new ErrorCollector(Messages.ERROR_PARSE_VALUE_FORMAT, ((InvalidFormatException) exception.getCause()).getValue());
            return new ResponseEntity<>(errorCollector, headers, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(exception, headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
