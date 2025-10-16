package com.example.demo.controllers

import com.example.demo.models.*
import org.springframework.web.bind.annotation.*

// Контроллер для работы с множеством строк (Set<String>)
@RestController
@RequestMapping("/api/collections/strings")
class StringSetController {

    // Хранилище для множества строк (дубликаты не добавляются)
    private val stringSet: MutableSet<String> = mutableSetOf()

    // Метод для добавления строки в множество
    @PostMapping
    fun addString(@RequestBody request: StringRequest): AddToCollectionResponse {
        val wasAdded = stringSet.add(request.value)
        return AddToCollectionResponse(
            message = if (wasAdded) "Строка добавлена в множество" else "Строка уже существует в множестве",
            totalItems = stringSet.size
        )
    }

    // Метод для получения всех строк из множества
    @GetMapping
    fun getStrings(): StringSetResponse {
        return StringSetResponse(
            strings = stringSet.toList(),
            count = stringSet.size
        )
    }

    // Метод для очистки множества строк
    @DeleteMapping
    fun clearStrings(): ClearCollectionResponse {
        stringSet.clear()
        return ClearCollectionResponse(
            message = "Множество строк очищено"
        )
    }
}
