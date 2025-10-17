package com.example.demo.Lab2.repositories

import com.example.demo.Lab2.entities.Developer
import com.example.demo.Lab2.entities.Genre
import com.example.demo.Lab2.entities.Publisher
import com.example.demo.Lab2.entities.VideoGame
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.math.BigDecimal

/**
 * Integration tests for VideoGameRepository
 */
@DataJpaTest
class VideoGameRepositoryTest {
    
    @Autowired
    private lateinit var entityManager: TestEntityManager
    
    @Autowired
    private lateinit var videoGameRepository: VideoGameRepository
    
    @Autowired
    private lateinit var developerRepository: DeveloperRepository
    
    @Autowired
    private lateinit var publisherRepository: PublisherRepository
    
    @Autowired
    private lateinit var genreRepository: GenreRepository
    
    private lateinit var developer: Developer
    private lateinit var publisher: Publisher
    private lateinit var genreAction: Genre
    private lateinit var genreRPG: Genre
    
    @BeforeEach
    fun setup() {
        // Create test data
        developer = developerRepository.save(
            Developer(
                name = "Test Developer",
                country = "USA",
                foundedYear = 2000
            )
        )
        
        publisher = publisherRepository.save(
            Publisher(
                name = "Test Publisher",
                country = "USA",
                foundedYear = 2000
            )
        )
        
        genreAction = genreRepository.save(
            Genre(
                name = "Action",
                description = "Action games"
            )
        )
        
        genreRPG = genreRepository.save(
            Genre(
                name = "RPG",
                description = "Role-playing games"
            )
        )
    }
    
    @Test
    fun `findByGenreId should return games of specified genre`() {
        // Arrange
        videoGameRepository.save(
            VideoGame(
                title = "Action Game",
                releaseYear = 2020,
                price = BigDecimal("59.99"),
                developer = developer,
                publisher = publisher,
                genre = genreAction
            )
        )
        
        videoGameRepository.save(
            VideoGame(
                title = "RPG Game",
                releaseYear = 2021,
                price = BigDecimal("49.99"),
                developer = developer,
                publisher = publisher,
                genre = genreRPG
            )
        )
        
        // Act
        val actionGames = videoGameRepository.findByGenreId(genreAction.id!!)
        
        // Assert
        assertEquals(1, actionGames.size)
        assertEquals("Action Game", actionGames[0].title)
        assertEquals(genreAction.id, actionGames[0].genre.id)
    }
    
    @Test
    fun `findByDeveloperId should return games from specified developer`() {
        // Arrange
        val anotherDeveloper = developerRepository.save(
            Developer(
                name = "Another Developer",
                country = "Japan",
                foundedYear = 1995
            )
        )
        
        videoGameRepository.save(
            VideoGame(
                title = "Game 1",
                releaseYear = 2020,
                price = BigDecimal("59.99"),
                developer = developer,
                publisher = publisher,
                genre = genreAction
            )
        )
        
        videoGameRepository.save(
            VideoGame(
                title = "Game 2",
                releaseYear = 2021,
                price = BigDecimal("49.99"),
                developer = anotherDeveloper,
                publisher = publisher,
                genre = genreAction
            )
        )
        
        // Act
        val games = videoGameRepository.findByDeveloperId(developer.id!!)
        
        // Assert
        assertEquals(1, games.size)
        assertEquals("Game 1", games[0].title)
        assertEquals(developer.id, games[0].developer.id)
    }
    
    @Test
    fun `findByPriceBetween should return games in price range`() {
        // Arrange
        videoGameRepository.save(
            VideoGame(
                title = "Cheap Game",
                releaseYear = 2020,
                price = BigDecimal("19.99"),
                developer = developer,
                publisher = publisher,
                genre = genreAction
            )
        )
        
        videoGameRepository.save(
            VideoGame(
                title = "Mid Price Game",
                releaseYear = 2021,
                price = BigDecimal("39.99"),
                developer = developer,
                publisher = publisher,
                genre = genreAction
            )
        )
        
        videoGameRepository.save(
            VideoGame(
                title = "Expensive Game",
                releaseYear = 2022,
                price = BigDecimal("69.99"),
                developer = developer,
                publisher = publisher,
                genre = genreAction
            )
        )
        
        // Act
        val games = videoGameRepository.findByPriceBetween(
            BigDecimal("20.00"),
            BigDecimal("50.00")
        )
        
        // Assert
        assertEquals(1, games.size)
        assertEquals("Mid Price Game", games[0].title)
        assertTrue(games[0].price >= BigDecimal("20.00"))
        assertTrue(games[0].price <= BigDecimal("50.00"))
    }
    
    @Test
    fun `findByReleaseYear should return games released in specified year`() {
        // Arrange
        videoGameRepository.save(
            VideoGame(
                title = "2020 Game",
                releaseYear = 2020,
                price = BigDecimal("59.99"),
                developer = developer,
                publisher = publisher,
                genre = genreAction
            )
        )
        
        videoGameRepository.save(
            VideoGame(
                title = "2021 Game",
                releaseYear = 2021,
                price = BigDecimal("49.99"),
                developer = developer,
                publisher = publisher,
                genre = genreAction
            )
        )
        
        // Act
        val games2020 = videoGameRepository.findByReleaseYear(2020)
        
        // Assert
        assertEquals(1, games2020.size)
        assertEquals("2020 Game", games2020[0].title)
        assertEquals(2020, games2020[0].releaseYear)
    }
    
    @Test
    fun `findByTitleContainingIgnoreCase should return matching games`() {
        // Arrange
        videoGameRepository.save(
            VideoGame(
                title = "The Witcher 3",
                releaseYear = 2015,
                price = BigDecimal("39.99"),
                developer = developer,
                publisher = publisher,
                genre = genreRPG
            )
        )
        
        videoGameRepository.save(
            VideoGame(
                title = "Cyberpunk 2077",
                releaseYear = 2020,
                price = BigDecimal("59.99"),
                developer = developer,
                publisher = publisher,
                genre = genreRPG
            )
        )
        
        // Act
        val games = videoGameRepository.findByTitleContainingIgnoreCase("witcher")
        
        // Assert
        assertEquals(1, games.size)
        assertEquals("The Witcher 3", games[0].title)
    }
}
