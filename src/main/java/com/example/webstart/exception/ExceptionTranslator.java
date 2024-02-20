package com.example.webstart.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.validation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String courseNotFoundHandler(CourseNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String teacherNotFoundHandler(TeacherNotFoundException exception) {
        return exception.getMessage();
    }
    @ExceptionHandler(TeacherAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String teacherAlreadyExistsHandler(TeacherAlreadyExistsException exception) {
        return exception.getMessage();
    }
    @ExceptionHandler(TeacherDataIsTheSame.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String teacherDataIsTheSameHandler(TeacherDataIsTheSame exception) {
        return exception.getMessage();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        return handleExceptionInternal(
                ex, errors, headers,
                HttpStatus.BAD_REQUEST, request);

    }
}



