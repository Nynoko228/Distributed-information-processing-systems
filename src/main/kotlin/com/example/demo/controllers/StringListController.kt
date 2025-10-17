package com.example.demo.controllers

import com.example.demo.models.*
import org.springframework.web.bind.annotation.*

// Контроллер для работы со списком строк (List<String>)
@RestController
@RequestMapping("/api/collections/strings")
class StringListController {

    // Хранилище для списка строк
    private val stringList: MutableList<String> = mutableListOf()

    // Метод для добавления строки в список
    @PostMapping
    fun addString(@RequestBody request: StringRequest): AddToCollectionResponse {
        stringList.add(request.value)
        return AddToCollectionResponse(
            message = "Строка добавлена в список",
            totalItems = stringList.size
        )
    }

    // Метод для добавления строки в список по индексу
    @PostMapping("/index")
    fun addStringAtIndex(@RequestBody request: AddAtIndexRequest): AddToCollectionResponse {
        return try {
            if (request.index < 0 || request.index > stringList.size) {
                throw IndexOutOfBoundsException("Индекс вне диапазона")
            }
            stringList.add(request.index, request.value)
            AddToCollectionResponse(
                message = "Строка добавлена по индексу ${request.index}",
                totalItems = stringList.size
            )
        } catch (e: Exception) {
            AddToCollectionResponse(
                message = "Ошибка: ${e.message}",
                totalItems = stringList.size
            )
        }
    }

    // Метод для получения всех строк из списка
    @GetMapping
    fun getStrings(): StringListResponse {
        return StringListResponse(
            strings = stringList.toList(),
            count = stringList.size
        )
    }

    // Метод для получения строки по индексу
    @GetMapping("/index/{index}")
    fun getStringAtIndex(@PathVariable index: Int): GetAtIndexResponse {
        return try {
            if (index < 0 || index >= stringList.size) {
                throw IndexOutOfBoundsException("Индекс вне диапазона")
            }
            GetAtIndexResponse(
                success = true,
                value = stringList[index],
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

    // Метод для очистки списка строк
    @DeleteMapping
    fun clearStrings(): ClearCollectionResponse {
        stringList.clear()
        return ClearCollectionResponse(
            message = "Список строк очищен"
        )
    }
}
