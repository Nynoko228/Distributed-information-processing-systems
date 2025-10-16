package com.example.demo.controllers

import com.example.demo.models.*
import org.springframework.web.bind.annotation.*

// Контроллер для работы со списком чисел (List<Int>)
@RestController
@RequestMapping("/api/collections/numbers")
class NumberListController {

    // Хранилище для списка чисел
    private val numberList: MutableList<Int> = mutableListOf()

    // Метод для добавления числа в список
    @PostMapping
    fun addNumber(@RequestBody request: NumberRequest): AddToCollectionResponse {
        numberList.add(request.value)
        return AddToCollectionResponse(
            message = "Число добавлено в список",
            totalItems = numberList.size
        )
    }

    // Метод для получения всех чисел из списка
    @GetMapping
    fun getNumbers(): NumberListResponse {
        return NumberListResponse(
            numbers = numberList.toList(),
            count = numberList.size
        )
    }

    // Метод для очистки списка чисел
    @DeleteMapping
    fun clearNumbers(): ClearCollectionResponse {
        numberList.clear()
        return ClearCollectionResponse(
            message = "Список чисел очищен"
        )
    }
}
