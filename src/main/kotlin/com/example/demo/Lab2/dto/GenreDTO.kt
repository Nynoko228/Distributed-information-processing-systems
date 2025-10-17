package com.example.demo.Lab2.dto

import com.example.demo.Lab2.entities.Genre
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * DTO для создания/обновления жанра
 */
data class GenreDTO(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 100, message = "Name cannot exceed 100 characters")
    val name: String,

    val description: String? = null
)

/**
 * DTO для ответа с информацией о жанре
 */
data class GenreResponseDTO(
    val id: Long,
    val name: String,
    val description: String?,
    val gamesCount: Int = 0
) {
    companion object {
        fun from(genre: Genre, gamesCount: Int = 0): GenreResponseDTO {
            return GenreResponseDTO(
                id = genre.id!!,
                name = genre.name,
                description = genre.description,
                gamesCount = gamesCount
            )
        }
    }
}
