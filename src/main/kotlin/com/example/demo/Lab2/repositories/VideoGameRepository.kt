package com.example.demo.Lab2.repositories

import com.example.demo.Lab2.entities.VideoGame
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

/**
 * Репозиторий для работы с видеоиграми
 */
@Repository
interface VideoGameRepository : JpaRepository<VideoGame, Long> {
    
    /**
     * Поиск игр по жанру
     */
    fun findByGenreId(genreId: Long): List<VideoGame>
    
    /**
     * Поиск игр по разработчику
     */
    fun findByDeveloperId(developerId: Long): List<VideoGame>
    
    /**
     * Поиск игр по издателю
     */
    fun findByPublisherId(publisherId: Long): List<VideoGame>
    
    /**
     * Поиск игр по году выпуска
     */
    fun findByReleaseYear(releaseYear: Int): List<VideoGame>
    
    /**
     * Поиск игр в диапазоне цен
     */
    fun findByPriceBetween(minPrice: BigDecimal, maxPrice: BigDecimal): List<VideoGame>
    
    /**
     * Поиск игр по части названия (игнорируя регистр)
     */
    fun findByTitleContainingIgnoreCase(title: String): List<VideoGame>
}
