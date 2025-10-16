package com.example.demo.controllers

import com.example.demo.models.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/primitives")
class PrimitiveController {

    // In-memory storage for primitive values
    private var storedNumber: Int? = null
    private var storedString: String? = null
    private var storedBoolean: Boolean? = null

    @PostMapping("/number")
    fun addNumber(@RequestBody request: NumberRequest): AddNumberResponse {
        storedNumber = request.value
        return AddNumberResponse(
            message = "Number added successfully",
            value = request.value
        )
    }

    @GetMapping("/number")
    fun getNumber(): NumberResponse {
        return NumberResponse(value = storedNumber)
    }

    @PostMapping("/string")
    fun addString(@RequestBody request: StringRequest): AddStringResponse {
        storedString = request.value
        return AddStringResponse(
            message = "String added successfully",
            value = request.value
        )
    }

    @GetMapping("/string")
    fun getString(): StringResponse {
        return StringResponse(value = storedString)
    }

    @PostMapping("/boolean")
    fun addBoolean(@RequestBody request: BooleanRequest): AddBooleanResponse {
        storedBoolean = request.value
        return AddBooleanResponse(
            message = "Boolean added successfully",
            value = request.value
        )
    }

    @GetMapping("/boolean")
    fun getBoolean(): BooleanResponse {
        return BooleanResponse(value = storedBoolean)
    }
}
