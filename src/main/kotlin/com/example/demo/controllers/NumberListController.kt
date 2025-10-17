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

    // Метод для добавления числа в список по индексу
    @PostMapping("/index")
    fun addNumberAtIndex(@RequestBody request: AddAtIndexRequest): AddToCollectionResponse {
        return try {
            if (request.index < 0 || request.index > numberList.size) {
                throw IndexOutOfBoundsException("Индекс вне диапазона")
            }
            numberList.add(request.index, request.value.toInt())
            AddToCollectionResponse(
                message = "Число добавлено по индексу ${request.index}",
                totalItems = numberList.size
            )
        } catch (e: Exception) {
            AddToCollectionResponse(
                message = "Ошибка: ${e.message}",
                totalItems = numberList.size
            )
        }
    }

    // Метод для получения всех чисел из списка
    @GetMapping
    fun getNumbers(): NumberListResponse {
        return NumberListResponse(
            numbers = numberList.toList(),
            count = numberList.size
        )
    }

    // Метод для получения числа по индексу
    @GetMapping("/index/{index}")
    fun getNumberAtIndex(@PathVariable index: Int): GetAtIndexResponse {
        return try {
            if (index < 0 || index >= numberList.size) {
                throw IndexOutOfBoundsException("Индекс вне диапазона")
            }
            GetAtIndexResponse(
                success = true,
                value = numberList[index].toString(),
                message = "Элемент найден"
            )
        } catch (e: Exception) {
            GetAtIndexResponse(
                success = false,
                value = null,
                message = "Ошибка: ${e.message}"
            )
        }
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
