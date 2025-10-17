package com.example.demo.Lab2.controllers

import com.example.demo.Lab2.dto.DeveloperDTO
import com.example.demo.Lab2.dto.DeveloperResponseDTO
import com.example.demo.Lab2.services.DeveloperService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

/**
 * REST контроллер для управления разработчиками
 */
@RestController
@RequestMapping("/lab2/developers")
class DeveloperController(private val developerService: DeveloperService) {
    
    /**
     * Создание нового разработчика
     */
    @PostMapping
    fun createDeveloper(@Valid @RequestBody dto: DeveloperDTO): ResponseEntity<DeveloperResponseDTO> {
        val created = developerService.createDeveloper(dto)
        return ResponseEntity
            .created(URI.create("/lab2/developers/${created.id}"))
            .body(created)
    }
    
    /**
     * Получение всех разработчиков
     */
    @GetMapping
    fun getAllDevelopers(): ResponseEntity<Map<String, Any>> {
        val developers = developerService.getAllDevelopers()
        return ResponseEntity.ok(
            mapOf(
                "developers" to developers,
                "count" to developers.size
            )
        )
    }
    
    /**
     * Получение разработчика по ID
     */
    @GetMapping("/{id}")
    fun getDeveloperById(@PathVariable id: Long): ResponseEntity<DeveloperResponseDTO> {
        val developer = developerService.getDeveloperById(id)
        return ResponseEntity.ok(developer)
    }
    
    /**
     * Обновление разработчика
     */
    @PutMapping("/{id}")
    fun updateDeveloper(
        @PathVariable id: Long,
        @Valid @RequestBody dto: DeveloperDTO
    ): ResponseEntity<DeveloperResponseDTO> {
        val updated = developerService.updateDeveloper(id, dto)
        return ResponseEntity.ok(updated)
    }
    
    /**
     * Удаление разработчика
     */
    @DeleteMapping("/{id}")
    fun deleteDeveloper(@PathVariable id: Long): ResponseEntity<Void> {
        developerService.deleteDeveloper(id)
        return ResponseEntity.noContent().build()
    }
}
