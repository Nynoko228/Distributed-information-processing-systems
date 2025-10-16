package com.example.demo.controllers

import com.example.demo.models.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/Lab1/collections")
class CollectionController {

    // In-memory storage for collection items
    private val items: MutableList<String> = mutableListOf()

    @PostMapping("/items")
    fun addItem(@RequestBody request: ItemRequest): AddItemResponse {
        items.add(request.item)
        return AddItemResponse(
            message = "Item added successfully",
            item = request.item,
            totalItems = items.size
        )
    }

    @GetMapping("/items")
    fun getItems(): ItemsResponse {
        return ItemsResponse(
            items = items.toList(),
            count = items.size
        )
    }

    @DeleteMapping("/items")
    fun clearItems(): ClearCollectionResponse {
        items.clear()
        return ClearCollectionResponse(
            message = "Collection cleared successfully"
        )
    }
}
