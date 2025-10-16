package com.example.demo.controllers

import com.example.demo.models.*
import org.springframework.web.bind.annotation.*

// Контроллер для работы со словарём логических значений (Map<Boolean, Int>)
@RestController
@RequestMapping("/api/collections/booleans")
class BooleanMapController {

    // Хранилище для словаря: ключ - логическое значение, значение - счетчик
    private val booleanMap: MutableMap<Boolean, Int> = mutableMapOf()

    // Метод для добавления логического значения в словарь
    @PostMapping
    fun addBoolean(@RequestBody request: BooleanRequest): AddToCollectionResponse {
        // Увеличиваем счетчик для данного логического значения
        booleanMap[request.value] = booleanMap.getOrDefault(request.value, 0) + 1
        
        return AddToCollectionResponse(
            message = "Значение добавлено в словарь",
            totalItems = booleanMap.values.sum()
        )
    }

    // Метод для получения всего словаря
    @GetMapping
    fun getBooleans(): BooleanMapResponse {
        return BooleanMapResponse(
            booleanMap = booleanMap.toMap(),
            count = booleanMap.values.sum()
        )
    }

    // Метод для очистки словаря
    @DeleteMapping
    fun clearBooleans(): ClearCollectionResponse {
        booleanMap.clear()
        return ClearCollectionResponse(
            message = "Словарь логических значений очищена"
        )
    }
}
