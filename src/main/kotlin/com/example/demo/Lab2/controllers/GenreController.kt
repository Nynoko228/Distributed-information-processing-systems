package com.example.demo.Lab2.controllers

import com.example.demo.Lab2.dto.GenreDTO
import com.example.demo.Lab2.dto.GenreResponseDTO
import com.example.demo.Lab2.services.GenreService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

/**
 * REST контроллер для управления жанрами
 */
@RestController
@RequestMapping("/lab2/genres")
class GenreController(private val genreService: GenreService) {
    
    /**
     * Создание нового жанра
     */
    @PostMapping
    fun createGenre(@Valid @RequestBody dto: GenreDTO): ResponseEntity<GenreResponseDTO> {
        val created = genreService.createGenre(dto)
        return ResponseEntity
            .created(URI.create("/lab2/genres/${created.id}"))
            .body(created)
    }
    
    /**
     * Получение всех жанров
     */
    @GetMapping
    fun getAllGenres(): ResponseEntity<Map<String, Any>> {
        val genres = genreService.getAllGenres()
        return ResponseEntity.ok(
            mapOf(
                "genres" to genres,
                "count" to genres.size
            )
        )
    }
    
    /**
     * Получение жанра по ID
     */
    @GetMapping("/{id}")
    fun getGenreById(@PathVariable id: Long): ResponseEntity<GenreResponseDTO> {
        val genre = genreService.getGenreById(id)
        return ResponseEntity.ok(genre)
    }
    
    /**
     * Обновление жанра
     */
    @PutMapping("/{id}")
    fun updateGenre(
        @PathVariable id: Long,
        @Valid @RequestBody dto: GenreDTO
    ): ResponseEntity<GenreResponseDTO> {
        val updated = genreService.updateGenre(id, dto)
        return ResponseEntity.ok(updated)
    }
    
    /**
     * Удаление жанра
     */
    @DeleteMapping("/{id}")
    fun deleteGenre(@PathVariable id: Long): ResponseEntity<Void> {
        genreService.deleteGenre(id)
        return ResponseEntity.noContent().build()
    }
}
