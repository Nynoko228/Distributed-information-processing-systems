package com.example.demo.Lab2.controllers

import com.example.demo.Lab2.exceptions.ConflictException
import com.example.demo.Lab2.exceptions.ResourceNotFoundException
import com.example.demo.Lab2.exceptions.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

/**
 * Глобальный обработчик исключений для Lab2
 */
@RestControllerAdvice(basePackages = ["com.example.demo.Lab2"])
class GlobalExceptionHandler {
    
    /**
     * Обработка исключений ResourceNotFoundException
     */
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            error = "Not Found",
            message = ex.message ?: "Resource not found",
            timestamp = LocalDateTime.now().toString()
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }
    
    /**
     * Обработка исключений ValidationException
     */
    @ExceptionHandler(ValidationException::class)
    fun handleValidation(ex: ValidationException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            error = "Bad Request",
            message = ex.message ?: "Validation failed",
            timestamp = LocalDateTime.now().toString(),
            field = ex.field
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
    
    /**
     * Обработка исключений ConflictException
     */
    @ExceptionHandler(ConflictException::class)
    fun handleConflict(ex: ConflictException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            error = "Conflict",
            message = ex.message ?: "Conflict occurred",
            timestamp = LocalDateTime.now().toString()
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }
    
    /**
     * Обработка исключений валидации Jakarta Bean Validation
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val firstError = ex.bindingResult.fieldErrors.firstOrNull()
        val errorResponse = ErrorResponse(
            error = "Bad Request",
            message = firstError?.defaultMessage ?: "Validation failed",
            timestamp = LocalDateTime.now().toString(),
            field = firstError?.field
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
    
    /**
     * Обработка всех остальных исключений
     */
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            error = "Internal Server Error",
            message = ex.message ?: "An unexpected error occurred",
            timestamp = LocalDateTime.now().toString()
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}

/**
 * Модель ответа об ошибке
 */
data class ErrorResponse(
    val error: String,
    val message: String,
    val timestamp: String,
    val field: String? = null
)
