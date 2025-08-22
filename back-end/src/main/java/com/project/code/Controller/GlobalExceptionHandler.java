package com.project.code.Controller;
// Completed
import jakarta.servlet.http.PushBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends Exception {

// 1. Set Up the Global Exception Handler Class:
//    - Annotate the class with `@RestControllerAdvice` to enable global exception handling for REST controllers.
//    - This allows the class to handle exceptions thrown in any of the controllers globally.

// 2. Define the `handleJsonParseException` Method:
//    - Annotate with `@ExceptionHandler(HttpMessageNotReadableException.class)` to handle cases where the request body is not correctly formatted (e.g., invalid JSON).
//    - The `HttpMessageNotReadableException` typically occurs when the input data cannot be deserialized or is improperly formatted.
//    - Use `@ResponseStatus(HttpStatus.BAD_REQUEST)` to specify that the response status will be **400 Bad Request** when this exception is thrown.
//    - The method should return a `Map<String, Object>` with the following key:
//        - **`message`**: The error message should indicate that the input provided is invalid. The value should be `"Invalid input: The data provided is not valid."`.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ResponseEntity <Map<String, String>> handleJsonParseExeption(HttpMessageNotReadableException ex){

        Map<String,String> errors = new HashMap<>();
        errors.put("message", "Invalid input.  The data provided is not valid.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


    public static class StoreNotFoundException extends RuntimeException {

        public StoreNotFoundException(Long id) {
            super("Store not found with ID: " + id);
        }
    }

    public class ProductNotAvailableException {

        public ProductNotAvailableException( String message) {
            super(message);
        }
    }

}
