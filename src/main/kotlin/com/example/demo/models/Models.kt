package com.example.demo.models

// Request models
data class NumberRequest(val value: Int)

data class StringRequest(val value: String)

data class BooleanRequest(val value: Boolean)

data class ItemRequest(val item: String)

// Response models
data class NumberResponse(val value: Int?)

data class StringResponse(val value: String?)

data class BooleanResponse(val value: Boolean?)

data class AddNumberResponse(val message: String, val value: Int)

data class AddStringResponse(val message: String, val value: String)

data class AddBooleanResponse(val message: String, val value: Boolean)

data class AddItemResponse(val message: String, val item: String, val totalItems: Int)

data class ItemsResponse(val items: List<String>, val count: Int)

data class ClearCollectionResponse(val message: String)
