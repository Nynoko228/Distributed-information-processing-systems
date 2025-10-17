package com.example.demo.Lab2.repositories

import com.example.demo.Lab2.entities.Developer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Репозиторий для работы с разработчиками
 */
@Repository
interface DeveloperRepository : JpaRepository<Developer, Long> {
    
    /**
     * Поиск разработчика по имени
     */
    fun findByName(name: String): Optional<Developer>
    
    /**
     * Проверка существования разработчика по имени
     */
    fun existsByName(name: String): Boolean
}
