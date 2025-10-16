package com.example.demo.models

// Модели запросов
data class NumberRequest(val value: Int)

data class StringRequest(val value: String)

data class BooleanRequest(val value: Boolean)

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

// Модели для List<Int>
data class NumberListResponse(val numbers: List<Int>, val count: Int)

// Модели для Set<String>
data class StringSetResponse(val strings: List<String>, val count: Int)

// Модели для Map<Boolean, Int>
data class BooleanMapResponse(val booleanMap: Map<Boolean, Int>, val count: Int)
