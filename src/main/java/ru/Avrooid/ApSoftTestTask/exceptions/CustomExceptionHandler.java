package ru.Avrooid.ApSoftTestTask.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import java.io.FileNotFoundException;

/**
 * Класс для автоматической обработки ошибок
 */
@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ResponseError> fileNotFoundException(FileNotFoundException exception) {
        log.error("Error: " + exception.getMessage());
        return new ResponseEntity<>(new ResponseError(exception.getMessage(), Code.FILE_NOT_FOUND), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ResponseError> multipartException(MultipartException exception) {
        log.error("Error: " + exception.getMessage());
        return new ResponseEntity<>(new ResponseError(exception.getMessage(), Code.FILE_ERROR), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseError> entityNotFoundException(EntityNotFoundException exception) {
        log.error("Error: " + exception.getMessage());
        return new ResponseEntity<>(new ResponseError(exception.getMessage(), Code.ID_NOT_EXIST), HttpStatus.BAD_REQUEST);
    }
}
