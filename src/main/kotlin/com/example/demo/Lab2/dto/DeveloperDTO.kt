package com.example.demo.Lab2.dto

import com.example.demo.Lab2.entities.Developer
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * DTO для создания/обновления разработчика
 */
data class DeveloperDTO(
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 255, message = "Name cannot exceed 255 characters")
    val name: String,

    @field:Size(max = 100, message = "Country cannot exceed 100 characters")
    val country: String? = null,

    @field:Min(value = 1950, message = "Founded year must be at least 1950")
    val foundedYear: Int? = null
)

/**
 * DTO для ответа с информацией о разработчике
 */
data class DeveloperResponseDTO(
    val id: Long,
    val name: String,
    val country: String?,
    val foundedYear: Int?,
    val gamesCount: Int = 0
) {
    companion object {
        fun from(developer: Developer, gamesCount: Int = 0): DeveloperResponseDTO {
            return DeveloperResponseDTO(
                id = developer.id!!,
                name = developer.name,
                country = developer.country,
                foundedYear = developer.foundedYear,
                gamesCount = gamesCount
            )
        }
    }
}
