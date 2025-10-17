package com.example.demo.Lab2.repositories

import com.example.demo.Lab2.entities.Publisher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Репозиторий для работы с издателями
 */
@Repository
interface PublisherRepository : JpaRepository<Publisher, Long> {
    
    /**
     * Поиск издателя по имени
     */
    fun findByName(name: String): Optional<Publisher>
    
    /**
     * Проверка существования издателя по имени
     */
    fun existsByName(name: String): Boolean
}
