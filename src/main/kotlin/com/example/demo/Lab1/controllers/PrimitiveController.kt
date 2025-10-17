package com.example.demo.Lab1.controllers

import com.example.demo.Lab1.models.*
import org.springframework.web.bind.annotation.*

// Контроллер для работы с примитивными типами данных
@RestController
@RequestMapping("/lab1/primitives")
class PrimitiveController {

    // Хранилище для примитивных значений в памяти
    private var storedNumber: Int? = null
    private var storedString: String? = null
    private var storedBoolean: Boolean? = null

    // Метод для сохранения числа
    @PostMapping("/number")
    fun addNumber(@RequestBody request: NumberRequest): AddNumberResponse {
        storedNumber = request.value
        return AddNumberResponse(
            message = "Number added successfully",
            value = request.value
        )
    }

    // Метод для получения сохраненного числа
    @GetMapping("/number")
    fun getNumber(): NumberResponse {
        return NumberResponse(value = storedNumber)
    }

    // Метод для сохранения строки
    @PostMapping("/string")
    fun addString(@RequestBody request: StringRequest): AddStringResponse {
        storedString = request.value
        return AddStringResponse(
            message = "String added successfully",
            value = request.value
        )
    }

    // Метод для получения сохраненной строки
    @GetMapping("/string")
    fun getString(): StringResponse {
        return StringResponse(value = storedString)
    }

    // Метод для сохранения логического значения
    @PostMapping("/boolean")
    fun addBoolean(@RequestBody request: BooleanRequest): AddBooleanResponse {
        storedBoolean = request.value
        return AddBooleanResponse(
            message = "Boolean added successfully",
            value = request.value
        )
    }

    // Метод для получения сохраненного логического значения
    @GetMapping("/boolean")
    fun getBoolean(): BooleanResponse {
        return BooleanResponse(value = storedBoolean)
    }
}
