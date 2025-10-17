package com.example.demo.Lab2.services

import com.example.demo.Lab2.dto.VideoGameCreateDTO
import com.example.demo.Lab2.dto.VideoGamePatchDTO
import com.example.demo.Lab2.entities.Developer
import com.example.demo.Lab2.entities.Genre
import com.example.demo.Lab2.entities.Publisher
import com.example.demo.Lab2.entities.VideoGame
import com.example.demo.Lab2.exceptions.ResourceNotFoundException
import com.example.demo.Lab2.exceptions.ValidationException
import com.example.demo.Lab2.repositories.DeveloperRepository
import com.example.demo.Lab2.repositories.GenreRepository
import com.example.demo.Lab2.repositories.PublisherRepository
import com.example.demo.Lab2.repositories.VideoGameRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.util.*

/**
 * Unit tests for VideoGameService
 */
@ExtendWith(MockitoExtension::class)
class VideoGameServiceTest {
    
    @Mock
    private lateinit var videoGameRepository: VideoGameRepository
    
    @Mock
    private lateinit var developerRepository: DeveloperRepository
    
    @Mock
    private lateinit var publisherRepository: PublisherRepository
    
    @Mock
    private lateinit var genreRepository: GenreRepository
    
    @InjectMocks
    private lateinit var videoGameService: VideoGameService
    
    private fun createMockDeveloper() = Developer(
        id = 1L,
        name = "Test Developer",
        country = "USA",
        foundedYear = 2000
    )
    
    private fun createMockPublisher() = Publisher(
        id = 1L,
        name = "Test Publisher",
        country = "USA",
        foundedYear = 2000
    )
    
    private fun createMockGenre() = Genre(
        id = 1L,
        name = "Action",
        description = "Action games"
    )
    
    private fun createMockVideoGame() = VideoGame(
        id = 1L,
        title = "Test Game",
        releaseYear = 2020,
        price = BigDecimal("59.99"),
        developer = createMockDeveloper(),
        publisher = createMockPublisher(),
        genre = createMockGenre()
    )
    
    @Test
    fun `createVideoGame should save and return video game when data is valid`() {
        // Arrange
        val dto = VideoGameCreateDTO(
            title = "New Game",
            releaseYear = 2023,
            price = BigDecimal("49.99"),
            developerId = 1L,
            publisherId = 1L,
            genreId = 1L
        )
        
        val developer = createMockDeveloper()
        val publisher = createMockPublisher()
        val genre = createMockGenre()
        val expectedGame = VideoGame(
            id = 1L,
            title = dto.title,
            releaseYear = dto.releaseYear,
            price = dto.price,
            developer = developer,
            publisher = publisher,
            genre = genre
        )
        
        `when`(developerRepository.findById(1L)).thenReturn(Optional.of(developer))
        `when`(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher))
        `when`(genreRepository.findById(1L)).thenReturn(Optional.of(genre))
        `when`(videoGameRepository.save(any(VideoGame::class.java))).thenReturn(expectedGame)
        
        // Act
        val result = videoGameService.createVideoGame(dto)
        
        // Assert
        assertNotNull(result)
        assertEquals(dto.title, result.title)
        assertEquals(dto.price, result.price)
        verify(videoGameRepository, times(1)).save(any(VideoGame::class.java))
    }
    
    @Test
    fun `createVideoGame should throw exception when release year is too far in future`() {
        // Arrange
        val dto = VideoGameCreateDTO(
            title = "Future Game",
            releaseYear = 2100,
            price = BigDecimal("59.99"),
            developerId = 1L,
            publisherId = 1L,
            genreId = 1L
        )
        
        // Act & Assert
        assertThrows(ValidationException::class.java) {
            videoGameService.createVideoGame(dto)
        }
    }
    
    @Test
    fun `createVideoGame should throw exception when developer not found`() {
        // Arrange
        val dto = VideoGameCreateDTO(
            title = "New Game",
            releaseYear = 2023,
            price = BigDecimal("49.99"),
            developerId = 999L,
            publisherId = 1L,
            genreId = 1L
        )
        
        `when`(developerRepository.findById(999L)).thenReturn(Optional.empty())
        
        // Act & Assert
        assertThrows(ResourceNotFoundException::class.java) {
            videoGameService.createVideoGame(dto)
        }
    }
    
    @Test
    fun `getVideoGameById should return video game when found`() {
        // Arrange
        val game = createMockVideoGame()
        `when`(videoGameRepository.findById(1L)).thenReturn(Optional.of(game))
        
        // Act
        val result = videoGameService.getVideoGameById(1L)
        
        // Assert
        assertNotNull(result)
        assertEquals(game.title, result.title)
        verify(videoGameRepository, times(1)).findById(1L)
    }
    
    @Test
    fun `getVideoGameById should throw exception when not found`() {
        // Arrange
        `when`(videoGameRepository.findById(999L)).thenReturn(Optional.empty())
        
        // Act & Assert
        assertThrows(ResourceNotFoundException::class.java) {
            videoGameService.getVideoGameById(999L)
        }
    }
    
    @Test
    fun `deleteVideoGame should call repository delete when game exists`() {
        // Arrange
        `when`(videoGameRepository.existsById(1L)).thenReturn(true)
        
        // Act
        videoGameService.deleteVideoGame(1L)
        
        // Assert
        verify(videoGameRepository, times(1)).deleteById(1L)
    }
    
    @Test
    fun `deleteVideoGame should throw exception when game not found`() {
        // Arrange
        `when`(videoGameRepository.existsById(999L)).thenReturn(false)
        
        // Act & Assert
        assertThrows(ResourceNotFoundException::class.java) {
            videoGameService.deleteVideoGame(999L)
        }
    }
    
    @Test
    fun `patchVideoGame should update only provided fields`() {
        // Arrange
        val existingGame = createMockVideoGame()
        val patchDTO = VideoGamePatchDTO(
            price = BigDecimal("39.99")
        )
        
        `when`(videoGameRepository.findById(1L)).thenReturn(Optional.of(existingGame))
        `when`(videoGameRepository.save(any(VideoGame::class.java))).thenAnswer { it.arguments[0] }
        
        // Act
        val result = videoGameService.patchVideoGame(1L, patchDTO)
        
        // Assert
        assertNotNull(result)
        assertEquals(patchDTO.price, result.price)
        assertEquals(existingGame.title, result.title) // Title should remain unchanged
        verify(videoGameRepository, times(1)).save(any(VideoGame::class.java))
    }
}
