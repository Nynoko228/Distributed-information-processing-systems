package com.example.demo.Lab2.repositories

import com.example.demo.Lab2.entities.Genre
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Репозиторий для работы с жанрами
 */
@Repository
interface GenreRepository : JpaRepository<Genre, Long> {
    
    /**
     * Поиск жанра по имени
     */
    fun findByName(name: String): Optional<Genre>
    
    /**
     * Проверка существования жанра по имени
     */
    fun existsByName(name: String): Boolean
}
