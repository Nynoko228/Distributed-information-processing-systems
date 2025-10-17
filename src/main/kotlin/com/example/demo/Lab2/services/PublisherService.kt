package com.example.demo.Lab2.services

import com.example.demo.Lab2.dto.PublisherDTO
import com.example.demo.Lab2.dto.PublisherResponseDTO
import com.example.demo.Lab2.entities.Publisher
import com.example.demo.Lab2.exceptions.ConflictException
import com.example.demo.Lab2.exceptions.ResourceNotFoundException
import com.example.demo.Lab2.repositories.PublisherRepository
import com.example.demo.Lab2.repositories.VideoGameRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Сервис для работы с издателями
 */
@Service
@Transactional
class PublisherService(
    private val publisherRepository: PublisherRepository,
    private val videoGameRepository: VideoGameRepository
) {
    
    /**
     * Создание нового издателя
     */
    fun createPublisher(dto: PublisherDTO): PublisherResponseDTO {
        if (publisherRepository.existsByName(dto.name)) {
            throw ConflictException("Publisher with name '${dto.name}' already exists")
        }
        
        val publisher = Publisher(
            name = dto.name,
            country = dto.country,
            foundedYear = dto.foundedYear
        )
        
        val saved = publisherRepository.save(publisher)
        return PublisherResponseDTO.from(saved, 0)
    }
    
    /**
     * Получение всех издателей
     */
    @Transactional(readOnly = true)
    fun getAllPublishers(): List<PublisherResponseDTO> {
        return publisherRepository.findAll().map { publisher ->
            val gamesCount = videoGameRepository.findByPublisherId(publisher.id!!).size
            PublisherResponseDTO.from(publisher, gamesCount)
        }
    }
    
    /**
     * Получение издателя по ID
     */
    @Transactional(readOnly = true)
    fun getPublisherById(id: Long): PublisherResponseDTO {
        val publisher = publisherRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Publisher with id $id not found") }
        
        val gamesCount = videoGameRepository.findByPublisherId(id).size
        return PublisherResponseDTO.from(publisher, gamesCount)
    }
    
    /**
     * Обновление издателя
     */
    fun updatePublisher(id: Long, dto: PublisherDTO): PublisherResponseDTO {
        val publisher = publisherRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Publisher with id $id not found") }
        
        // Проверка уникальности имени (если имя изменилось)
        if (publisher.name != dto.name && publisherRepository.existsByName(dto.name)) {
            throw ConflictException("Publisher with name '${dto.name}' already exists")
        }
        
        val updated = publisher.copy(
            name = dto.name,
            country = dto.country,
            foundedYear = dto.foundedYear
        )
        
        val saved = publisherRepository.save(updated)
        val gamesCount = videoGameRepository.findByPublisherId(id).size
        return PublisherResponseDTO.from(saved, gamesCount)
    }
    
    /**
     * Удаление издателя
     */
    fun deletePublisher(id: Long) {
        if (!publisherRepository.existsById(id)) {
            throw ResourceNotFoundException("Publisher with id $id not found")
        }
        
        val gamesCount = videoGameRepository.findByPublisherId(id).size
        if (gamesCount > 0) {
            throw ConflictException("Cannot delete publisher: $gamesCount games are associated with this publisher")
        }
        
        publisherRepository.deleteById(id)
    }
}
