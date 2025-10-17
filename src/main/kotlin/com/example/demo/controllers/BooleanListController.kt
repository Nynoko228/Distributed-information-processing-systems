package com.example.demo.controllers

import com.example.demo.models.*
import org.springframework.web.bind.annotation.*

// Контроллер для работы со списком логических значений (List<Boolean>)
@RestController
@RequestMapping("/api/collections/booleans")
class BooleanListController {

    // Хранилище для списка логических значений
    private val booleanList: MutableList<Boolean> = mutableListOf()

    // Метод для добавления логического значения в список
    @PostMapping
    fun addBoolean(@RequestBody request: BooleanRequest): AddToCollectionResponse {
        booleanList.add(request.value)
        return AddToCollectionResponse(
            message = "Значение добавлено в список",
            totalItems = booleanList.size
        )
    }

    // Метод для добавления логического значения в список по индексу
    @PostMapping("/index")
    fun addBooleanAtIndex(@RequestBody request: AddAtIndexRequest): AddToCollectionResponse {
        return try {
            if (request.index < 0 || request.index > booleanList.size) {
                throw IndexOutOfBoundsException("Индекс вне диапазона")
            }
            val boolValue = request.value.toBoolean()
            booleanList.add(request.index, boolValue)
            AddToCollectionResponse(
                message = "Значение добавлено по индексу ${request.index}",
                totalItems = booleanList.size
            )
        } catch (e: Exception) {
            AddToCollectionResponse(
                message = "Ошибка: ${e.message}",
                totalItems = booleanList.size
            )
        }
    }

    // Метод для получения всех логических значений из списка
    @GetMapping
    fun getBooleans(): BooleanListResponse {
        return BooleanListResponse(
            booleans = booleanList.toList(),
            count = booleanList.size
        )
    }

    // Метод для получения логического значения по индексу
    @GetMapping("/index/{index}")
    fun getBooleanAtIndex(@PathVariable index: Int): GetAtIndexResponse {
        return try {
            if (index < 0 || index >= booleanList.size) {
                throw IndexOutOfBoundsException("Индекс вне диапазона")
            }
            val displayValue = if (booleanList[index]) "Да" else "Нет"
            GetAtIndexResponse(
                success = true,
                value = displayValue,
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

    // Метод для очистки списка логических значений
    @DeleteMapping
    fun clearBooleans(): ClearCollectionResponse {
        booleanList.clear()
        return ClearCollectionResponse(
            message = "Список логических значений очищен"
        )
    }
}
