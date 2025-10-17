package com.example.demo.Lab2.exceptions

/**
 * Исключение для случаев, когда ресурс не найден
 */
class ResourceNotFoundException(message: String) : RuntimeException(message)

/**
 * Исключение для случаев валидации данных
 */
class ValidationException(message: String, val field: String? = null) : RuntimeException(message)

/**
 * Исключение для конфликтов (дубликаты, нарушение ограничений)
 */
class ConflictException(message: String) : RuntimeException(message)

/**
 * Общее исключение для операций с видеоиграми
 */
class VideoGameException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
