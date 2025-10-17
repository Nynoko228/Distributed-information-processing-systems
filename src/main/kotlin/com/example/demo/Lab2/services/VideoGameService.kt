package com.example.demo.Lab2.services

import com.example.demo.Lab2.dto.*
import com.example.demo.Lab2.entities.VideoGame
import com.example.demo.Lab2.exceptions.ResourceNotFoundException
import com.example.demo.Lab2.exceptions.ValidationException
import com.example.demo.Lab2.repositories.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.Year

/**
 * Сервис для работы с видеоиграми
 */
@Service
@Transactional
class VideoGameService(
    private val videoGameRepository: VideoGameRepository,
    private val developerRepository: DeveloperRepository,
    private val publisherRepository: PublisherRepository,
    private val genreRepository: GenreRepository
) {
    
    /**
     * Создание новой видеоигры
     */
    fun createVideoGame(dto: VideoGameCreateDTO): VideoGameResponseDTO {
        validateReleaseYear(dto.releaseYear)
        
        val developer = developerRepository.findById(dto.developerId)
            .orElseThrow { ResourceNotFoundException("Developer with id ${dto.developerId} not found") }
        
        val publisher = publisherRepository.findById(dto.publisherId)
            .orElseThrow { ResourceNotFoundException("Publisher with id ${dto.publisherId} not found") }
        
        val genre = genreRepository.findById(dto.genreId)
            .orElseThrow { ResourceNotFoundException("Genre with id ${dto.genreId} not found") }
        
        val videoGame = VideoGame(
            title = dto.title,
            releaseYear = dto.releaseYear,
            price = dto.price,
            developer = developer,
            publisher = publisher,
            genre = genre
        )
        
        val saved = videoGameRepository.save(videoGame)
        return VideoGameResponseDTO.from(saved)
    }
    
    /**
     * Получение всех видеоигр с фильтрацией
     */
    @Transactional(readOnly = true)
    fun getAllVideoGames(
        genreId: Long? = null,
        developerId: Long? = null,
        publisherId: Long? = null,
        minPrice: BigDecimal? = null,
        maxPrice: BigDecimal? = null,
        releaseYear: Int? = null
    ): List<VideoGameResponseDTO> {
        var games = videoGameRepository.findAll()
        
        genreId?.let { games = games.filter { it.genre.id == genreId } }
        developerId?.let { games = games.filter { it.developer.id == developerId } }
        publisherId?.let { games = games.filter { it.publisher.id == publisherId } }
        releaseYear?.let { games = games.filter { it.releaseYear == releaseYear } }
        
        if (minPrice != null && maxPrice != null) {
            games = games.filter { it.price >= minPrice && it.price <= maxPrice }
        } else if (minPrice != null) {
            games = games.filter { it.price >= minPrice }
        } else if (maxPrice != null) {
            games = games.filter { it.price <= maxPrice }
        }
        
        return games.map { VideoGameResponseDTO.from(it) }
    }
    
    /**
     * Получение видеоигры по ID
     */
    @Transactional(readOnly = true)
    fun getVideoGameById(id: Long): VideoGameResponseDTO {
        val game = videoGameRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("VideoGame with id $id not found") }
        return VideoGameResponseDTO.from(game)
    }
    
    /**
     * Полное обновление видеоигры
     */
    fun updateVideoGame(id: Long, dto: VideoGameUpdateDTO): VideoGameResponseDTO {
        val existingGame = videoGameRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("VideoGame with id $id not found") }
        
        validateReleaseYear(dto.releaseYear)
        
        val developer = developerRepository.findById(dto.developerId)
            .orElseThrow { ResourceNotFoundException("Developer with id ${dto.developerId} not found") }
        
        val publisher = publisherRepository.findById(dto.publisherId)
            .orElseThrow { ResourceNotFoundException("Publisher with id ${dto.publisherId} not found") }
        
        val genre = genreRepository.findById(dto.genreId)
            .orElseThrow { ResourceNotFoundException("Genre with id ${dto.genreId} not found") }
        
        val updated = existingGame.copy(
            title = dto.title,
            releaseYear = dto.releaseYear,
            price = dto.price,
            developer = developer,
            publisher = publisher,
            genre = genre
        )
        
        val saved = videoGameRepository.save(updated)
        return VideoGameResponseDTO.from(saved)
    }
    
    /**
     * Частичное обновление видеоигры
     */
    fun patchVideoGame(id: Long, dto: VideoGamePatchDTO): VideoGameResponseDTO {
        val existingGame = videoGameRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("VideoGame with id $id not found") }
        
        dto.releaseYear?.let { validateReleaseYear(it) }
        
        val developer = dto.developerId?.let {
            developerRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Developer with id $it not found") }
        } ?: existingGame.developer
        
        val publisher = dto.publisherId?.let {
            publisherRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Publisher with id $it not found") }
        } ?: existingGame.publisher
        
        val genre = dto.genreId?.let {
            genreRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Genre with id $it not found") }
        } ?: existingGame.genre
        
        val updated = existingGame.copy(
            title = dto.title ?: existingGame.title,
            releaseYear = dto.releaseYear ?: existingGame.releaseYear,
            price = dto.price ?: existingGame.price,
            developer = developer,
            publisher = publisher,
            genre = genre
        )
        
        val saved = videoGameRepository.save(updated)
        return VideoGameResponseDTO.from(saved)
    }
    
    /**
     * Удаление видеоигры
     */
    fun deleteVideoGame(id: Long) {
        if (!videoGameRepository.existsById(id)) {
            throw ResourceNotFoundException("VideoGame with id $id not found")
        }
        videoGameRepository.deleteById(id)
    }
    
    /**
     * Валидация года выпуска
     */
    private fun validateReleaseYear(year: Int) {
        val currentYear = Year.now().value
        if (year > currentYear + 2) {
            throw ValidationException(
                "Release year cannot be more than 2 years in the future (current year: $currentYear)",
                "releaseYear"
            )
        }
    }
}
