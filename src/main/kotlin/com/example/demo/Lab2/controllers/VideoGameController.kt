package com.example.demo.Lab2.controllers

import com.example.demo.Lab2.dto.*
import com.example.demo.Lab2.services.VideoGameService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.net.URI

/**
 * REST контроллер для управления видеоиграми
 */
@RestController
@RequestMapping("/lab2/videogames")
class VideoGameController(private val videoGameService: VideoGameService) {
    
    /**
     * Создание новой видеоигры
     */
    @PostMapping
    fun createVideoGame(@Valid @RequestBody dto: VideoGameCreateDTO): ResponseEntity<VideoGameResponseDTO> {
        val created = videoGameService.createVideoGame(dto)
        return ResponseEntity
            .created(URI.create("/lab2/videogames/${created.id}"))
            .body(created)
    }
    
    /**
     * Получение всех видеоигр с фильтрацией
     */
    @GetMapping
    fun getAllVideoGames(
        @RequestParam(required = false) genreId: Long?,
        @RequestParam(required = false) developerId: Long?,
        @RequestParam(required = false) publisherId: Long?,
        @RequestParam(required = false) minPrice: BigDecimal?,
        @RequestParam(required = false) maxPrice: BigDecimal?,
        @RequestParam(required = false) releaseYear: Int?
    ): ResponseEntity<Map<String, Any>> {
        val games = videoGameService.getAllVideoGames(
            genreId = genreId,
            developerId = developerId,
            publisherId = publisherId,
            minPrice = minPrice,
            maxPrice = maxPrice,
            releaseYear = releaseYear
        )
        return ResponseEntity.ok(
            mapOf(
                "games" to games,
                "count" to games.size
            )
        )
    }
    
    /**
     * Получение видеоигры по ID
     */
    @GetMapping("/{id}")
    fun getVideoGameById(@PathVariable id: Long): ResponseEntity<VideoGameResponseDTO> {
        val game = videoGameService.getVideoGameById(id)
        return ResponseEntity.ok(game)
    }
    
    /**
     * Полное обновление видеоигры
     */
    @PutMapping("/{id}")
    fun updateVideoGame(
        @PathVariable id: Long,
        @Valid @RequestBody dto: VideoGameUpdateDTO
    ): ResponseEntity<VideoGameResponseDTO> {
        val updated = videoGameService.updateVideoGame(id, dto)
        return ResponseEntity.ok(updated)
    }
    
    /**
     * Частичное обновление видеоигры
     */
    @PatchMapping("/{id}")
    fun patchVideoGame(
        @PathVariable id: Long,
        @Valid @RequestBody dto: VideoGamePatchDTO
    ): ResponseEntity<VideoGameResponseDTO> {
        val updated = videoGameService.patchVideoGame(id, dto)
        return ResponseEntity.ok(updated)
    }
    
    /**
     * Удаление видеоигры
     */
    @DeleteMapping("/{id}")
    fun deleteVideoGame(@PathVariable id: Long): ResponseEntity<Void> {
        videoGameService.deleteVideoGame(id)
        return ResponseEntity.noContent().build()
    }
}
