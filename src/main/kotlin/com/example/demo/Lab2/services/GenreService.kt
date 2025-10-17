package com.example.demo.Lab2.services

import com.example.demo.Lab2.dto.GenreDTO
import com.example.demo.Lab2.dto.GenreResponseDTO
import com.example.demo.Lab2.entities.Genre
import com.example.demo.Lab2.exceptions.ConflictException
import com.example.demo.Lab2.exceptions.ResourceNotFoundException
import com.example.demo.Lab2.repositories.GenreRepository
import com.example.demo.Lab2.repositories.VideoGameRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Сервис для работы с жанрами
 */
@Service
@Transactional
class GenreService(
    private val genreRepository: GenreRepository,
    private val videoGameRepository: VideoGameRepository
) {
    
    /**
     * Создание нового жанра
     */
    fun createGenre(dto: GenreDTO): GenreResponseDTO {
        val normalizedName = dto.name.replaceFirstChar { it.uppercase() }
        
        if (genreRepository.existsByName(normalizedName)) {
            throw ConflictException("Genre with name '$normalizedName' already exists")
        }
        
        val genre = Genre(
            name = normalizedName,
            description = dto.description
        )
        
        val saved = genreRepository.save(genre)
        return GenreResponseDTO.from(saved, 0)
    }
    
    /**
     * Получение всех жанров
     */
    @Transactional(readOnly = true)
    fun getAllGenres(): List<GenreResponseDTO> {
        return genreRepository.findAll().map { genre ->
            val gamesCount = videoGameRepository.findByGenreId(genre.id!!).size
            GenreResponseDTO.from(genre, gamesCount)
        }
    }
    
    /**
     * Получение жанра по ID
     */
    @Transactional(readOnly = true)
    fun getGenreById(id: Long): GenreResponseDTO {
        val genre = genreRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Genre with id $id not found") }
        
        val gamesCount = videoGameRepository.findByGenreId(id).size
        return GenreResponseDTO.from(genre, gamesCount)
    }
    
    /**
     * Обновление жанра
     */
    fun updateGenre(id: Long, dto: GenreDTO): GenreResponseDTO {
        val genre = genreRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Genre with id $id not found") }
        
        val normalizedName = dto.name.replaceFirstChar { it.uppercase() }
        
        // Проверка уникальности имени (если имя изменилось)
        if (genre.name != normalizedName && genreRepository.existsByName(normalizedName)) {
            throw ConflictException("Genre with name '$normalizedName' already exists")
        }
        
        val updated = genre.copy(
            name = normalizedName,
            description = dto.description
        )
        
        val saved = genreRepository.save(updated)
        val gamesCount = videoGameRepository.findByGenreId(id).size
        return GenreResponseDTO.from(saved, gamesCount)
    }
    
    /**
     * Удаление жанра
     */
    fun deleteGenre(id: Long) {
        if (!genreRepository.existsById(id)) {
            throw ResourceNotFoundException("Genre with id $id not found")
        }
        
        val gamesCount = videoGameRepository.findByGenreId(id).size
        if (gamesCount > 0) {
            throw ConflictException("Cannot delete genre: $gamesCount games belong to this genre")
        }
        
        genreRepository.deleteById(id)
    }
}
