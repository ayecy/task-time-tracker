package com.tasktracker.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    // 1. Обработка ошибок валидации (например, поле title пустое)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        // Возвращаем 400 Bad Request с описанием ошибок
        return ResponseEntity.badRequest().body(errors);
    }

    // 2. Обработка наших ошибок "Бизнес-логики" (например, Задача не найдена)
    // В сервисе мы делали throw new RuntimeException("Задача с ID ... не найдена")
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        
        // Если сообщение содержит "не найдена", считаем это ошибкой клиента (404 Not Found)
        // Иначе возвращаем 400 Bad Request
        HttpStatus status = ex.getMessage().contains("не найдена") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        
        return ResponseEntity.status(status).body(error);
    }

    // 3. Обработка любых других непредвиденных ошибок
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Внутренняя ошибка сервера. Попробуйте позже.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}