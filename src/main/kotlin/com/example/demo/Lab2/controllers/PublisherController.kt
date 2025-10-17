package com.example.demo.Lab2.controllers

import com.example.demo.Lab2.dto.PublisherDTO
import com.example.demo.Lab2.dto.PublisherResponseDTO
import com.example.demo.Lab2.services.PublisherService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

/**
 * REST контроллер для управления издателями
 */
@RestController
@RequestMapping("/lab2/publishers")
class PublisherController(private val publisherService: PublisherService) {
    
    /**
     * Создание нового издателя
     */
    @PostMapping
    fun createPublisher(@Valid @RequestBody dto: PublisherDTO): ResponseEntity<PublisherResponseDTO> {
        val created = publisherService.createPublisher(dto)
        return ResponseEntity
            .created(URI.create("/lab2/publishers/${created.id}"))
            .body(created)
    }
    
    /**
     * Получение всех издателей
     */
    @GetMapping
    fun getAllPublishers(): ResponseEntity<Map<String, Any>> {
        val publishers = publisherService.getAllPublishers()
        return ResponseEntity.ok(
            mapOf(
                "publishers" to publishers,
                "count" to publishers.size
            )
        )
    }
    
    /**
     * Получение издателя по ID
     */
    @GetMapping("/{id}")
    fun getPublisherById(@PathVariable id: Long): ResponseEntity<PublisherResponseDTO> {
        val publisher = publisherService.getPublisherById(id)
        return ResponseEntity.ok(publisher)
    }
    
    /**
     * Обновление издателя
     */
    @PutMapping("/{id}")
    fun updatePublisher(
        @PathVariable id: Long,
        @Valid @RequestBody dto: PublisherDTO
    ): ResponseEntity<PublisherResponseDTO> {
        val updated = publisherService.updatePublisher(id, dto)
        return ResponseEntity.ok(updated)
    }
    
    /**
     * Удаление издателя
     */
    @DeleteMapping("/{id}")
    fun deletePublisher(@PathVariable id: Long): ResponseEntity<Void> {
        publisherService.deletePublisher(id)
        return ResponseEntity.noContent().build()
    }
}
