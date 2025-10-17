package com.example.demo.Lab1.models

// Модели запросов
data class NumberRequest(val value: Int)

data class StringRequest(val value: String)

data class BooleanRequest(val value: Boolean)

// Модель для добавления по индексу
data class AddAtIndexRequest(
    val index: Int,
    val value: String  // Универсальное значение в виде строки
)

// Модели ответов для примитивов
data class NumberResponse(val value: Int?)

data class StringResponse(val value: String?)

data class BooleanResponse(val value: Boolean?)

data class AddNumberResponse(val message: String, val value: Int)

data class AddStringResponse(val message: String, val value: String)

data class AddBooleanResponse(val message: String, val value: Boolean)

// Модели ответов для коллекций
data class AddToCollectionResponse(val message: String, val totalItems: Int)

data class ClearCollectionResponse(val message: String)

// Модель для получения элемента по индексу
data class GetAtIndexResponse(
    val success: Boolean,
    val value: String?,
    val message: String
)

// Модели для List<Int>
data class NumberListResponse(val numbers: List<Int>, val count: Int)

// Модели для List<String>
data class StringListResponse(val strings: List<String>, val count: Int)

// Модели для List<Boolean>
data class BooleanListResponse(val booleans: List<Boolean>, val count: Int)
