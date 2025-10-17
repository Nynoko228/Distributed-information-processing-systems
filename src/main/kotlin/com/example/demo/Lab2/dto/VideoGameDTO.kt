package com.example.demo.Lab2.dto

import com.example.demo.Lab2.entities.VideoGame
import jakarta.validation.constraints.*
import java.math.BigDecimal

/**
 * DTO для создания видеоигры
 */
data class VideoGameCreateDTO(
    @field:NotBlank(message = "Title cannot be blank")
    @field:Size(max = 255, message = "Title cannot exceed 255 characters")
    val title: String,

    @field:NotNull(message = "Release year is required")
    @field:Min(value = 1970, message = "Release year must be at least 1970")
    @field:Max(value = 2100, message = "Release year cannot exceed 2100")
    val releaseYear: Int,

    @field:NotNull(message = "Price is required")
    @field:DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
    val price: BigDecimal,

    @field:NotNull(message = "Developer ID is required")
    val developerId: Long,

    @field:NotNull(message = "Publisher ID is required")
    val publisherId: Long,

    @field:NotNull(message = "Genre ID is required")
    val genreId: Long
)

/**
 * DTO для полного обновления видеоигры
 */
data class VideoGameUpdateDTO(
    @field:NotBlank(message = "Title cannot be blank")
    @field:Size(max = 255, message = "Title cannot exceed 255 characters")
    val title: String,

    @field:NotNull(message = "Release year is required")
    @field:Min(value = 1970, message = "Release year must be at least 1970")
    @field:Max(value = 2100, message = "Release year cannot exceed 2100")
    val releaseYear: Int,

    @field:NotNull(message = "Price is required")
    @field:DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
    val price: BigDecimal,

    @field:NotNull(message = "Developer ID is required")
    val developerId: Long,

    @field:NotNull(message = "Publisher ID is required")
    val publisherId: Long,

    @field:NotNull(message = "Genre ID is required")
    val genreId: Long
)

/**
 * DTO для частичного обновления видеоигры
 */
data class VideoGamePatchDTO(
    @field:Size(max = 255, message = "Title cannot exceed 255 characters")
    val title: String? = null,

    @field:Min(value = 1970, message = "Release year must be at least 1970")
    @field:Max(value = 2100, message = "Release year cannot exceed 2100")
    val releaseYear: Int? = null,

    @field:DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
    val price: BigDecimal? = null,

    val developerId: Long? = null,

    val publisherId: Long? = null,

    val genreId: Long? = null
)

/**
 * DTO для ответа с информацией о видеоигре
 */
data class VideoGameResponseDTO(
    val id: Long,
    val title: String,
    val releaseYear: Int,
    val price: BigDecimal,
    val developer: DeveloperResponseDTO,
    val publisher: PublisherResponseDTO,
    val genre: GenreResponseDTO,
    val createdAt: String,
    val updatedAt: String
) {
    companion object {
        fun from(game: VideoGame): VideoGameResponseDTO {
            return VideoGameResponseDTO(
                id = game.id!!,
                title = game.title,
                releaseYear = game.releaseYear,
                price = game.price,
                developer = DeveloperResponseDTO.from(game.developer),
                publisher = PublisherResponseDTO.from(game.publisher),
                genre = GenreResponseDTO.from(game.genre),
                createdAt = game.createdAt.toString(),
                updatedAt = game.updatedAt.toString()
            )
        }
    }
}
