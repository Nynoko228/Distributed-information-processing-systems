package com.example.demo.Lab2.services

import com.example.demo.Lab2.dto.DeveloperDTO
import com.example.demo.Lab2.dto.DeveloperResponseDTO
import com.example.demo.Lab2.entities.Developer
import com.example.demo.Lab2.exceptions.ConflictException
import com.example.demo.Lab2.exceptions.ResourceNotFoundException
import com.example.demo.Lab2.repositories.DeveloperRepository
import com.example.demo.Lab2.repositories.VideoGameRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Сервис для работы с разработчиками
 */
@Service
@Transactional
class DeveloperService(
    private val developerRepository: DeveloperRepository,
    private val videoGameRepository: VideoGameRepository
) {
    
    /**
     * Создание нового разработчика
     */
    fun createDeveloper(dto: DeveloperDTO): DeveloperResponseDTO {
        if (developerRepository.existsByName(dto.name)) {
            throw ConflictException("Developer with name '${dto.name}' already exists")
        }
        
        val developer = Developer(
            name = dto.name,
            country = dto.country,
            foundedYear = dto.foundedYear
        )
        
        val saved = developerRepository.save(developer)
        return DeveloperResponseDTO.from(saved, 0)
    }
    
    /**
     * Получение всех разработчиков
     */
    @Transactional(readOnly = true)
    fun getAllDevelopers(): List<DeveloperResponseDTO> {
        return developerRepository.findAll().map { developer ->
            val gamesCount = videoGameRepository.findByDeveloperId(developer.id!!).size
            DeveloperResponseDTO.from(developer, gamesCount)
        }
    }
    
    /**
     * Получение разработчика по ID
     */
    @Transactional(readOnly = true)
    fun getDeveloperById(id: Long): DeveloperResponseDTO {
        val developer = developerRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Developer with id $id not found") }
        
        val gamesCount = videoGameRepository.findByDeveloperId(id).size
        return DeveloperResponseDTO.from(developer, gamesCount)
    }
    
    /**
     * Обновление разработчика
     */
    fun updateDeveloper(id: Long, dto: DeveloperDTO): DeveloperResponseDTO {
        val developer = developerRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Developer with id $id not found") }
        
        // Проверка уникальности имени (если имя изменилось)
        if (developer.name != dto.name && developerRepository.existsByName(dto.name)) {
            throw ConflictException("Developer with name '${dto.name}' already exists")
        }
        
        val updated = developer.copy(
            name = dto.name,
            country = dto.country,
            foundedYear = dto.foundedYear
        )
        
        val saved = developerRepository.save(updated)
        val gamesCount = videoGameRepository.findByDeveloperId(id).size
        return DeveloperResponseDTO.from(saved, gamesCount)
    }
    
    /**
     * Удаление разработчика
     */
    fun deleteDeveloper(id: Long) {
        if (!developerRepository.existsById(id)) {
            throw ResourceNotFoundException("Developer with id $id not found")
        }
        
        val gamesCount = videoGameRepository.findByDeveloperId(id).size
        if (gamesCount > 0) {
            throw ConflictException("Cannot delete developer: $gamesCount games are associated with this developer")
        }
        
        developerRepository.deleteById(id)
    }
}
