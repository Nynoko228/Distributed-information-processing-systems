# Lab2: Video Games Database Management System

## Overview

Lab2 implements a comprehensive REST API for managing a video game catalog using Spring Boot, Kotlin, and PostgreSQL. The system supports CRUD operations for video games, developers, publishers, and genres with full data persistence.

## Technology Stack

- **Language**: Kotlin
- **Framework**: Spring Boot 3.5.6
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Gradle
- **JDK**: Java 21

## Architecture

The application follows a layered architecture pattern:

```
Controllers (REST API) → Services (Business Logic) → Repositories (Data Access) → Database (PostgreSQL)
```

### Project Structure

```
src/main/kotlin/com/example/demo/Lab2/
├── controllers/        # REST API endpoints
├── services/          # Business logic layer
├── repositories/      # Data access layer
├── entities/          # JPA entities
├── dto/              # Data Transfer Objects
└── exceptions/       # Custom exceptions
```

## Database Setup

### Prerequisites

1. Install PostgreSQL (version 12 or higher)
2. Start PostgreSQL service

### Create Database

Connect to PostgreSQL and create the database:

```sql
CREATE DATABASE lab2_videogames;
```

### Configuration

The application is configured to connect to PostgreSQL in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/lab2_videogames
spring.datasource.username=postgres
spring.datasource.password=postgres
```

**Important**: Update the username and password if your PostgreSQL credentials are different.

### Database Initialization

On first run, Hibernate will automatically create the necessary tables:
- `developers`
- `publishers`
- `genres`
- `video_games`

The `data.sql` script will populate reference tables with initial data (genres, developers, publishers).

## Running the Application

### Build the Project

```bash
.\gradlew.bat build
```

### Run the Application

```bash
.\gradlew.bat bootRun
```

The application will start on `http://localhost:8080`

## API Endpoints

### Base Path: `/lab2`

### Genre Endpoints

#### Create Genre
```bash
curl -X POST http://localhost:8080/lab2/genres ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Adventure\",\"description\":\"Story-driven games\"}"
```

#### Get All Genres
```bash
curl http://localhost:8080/lab2/genres
```

#### Get Genre by ID
```bash
curl http://localhost:8080/lab2/genres/1
```

#### Update Genre
```bash
curl -X PUT http://localhost:8080/lab2/genres/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Adventure\",\"description\":\"Updated description\"}"
```

#### Delete Genre
```bash
curl -X DELETE http://localhost:8080/lab2/genres/1
```

### Developer Endpoints

#### Create Developer
```bash
curl -X POST http://localhost:8080/lab2/developers ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Naughty Dog\",\"country\":\"USA\",\"foundedYear\":1984}"
```

#### Get All Developers
```bash
curl http://localhost:8080/lab2/developers
```

#### Get Developer by ID
```bash
curl http://localhost:8080/lab2/developers/1
```

#### Update Developer
```bash
curl -X PUT http://localhost:8080/lab2/developers/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Naughty Dog\",\"country\":\"United States\",\"foundedYear\":1984}"
```

#### Delete Developer
```bash
curl -X DELETE http://localhost:8080/lab2/developers/1
```

### Publisher Endpoints

Endpoints follow the same pattern as Developer endpoints:
- `POST /lab2/publishers`
- `GET /lab2/publishers`
- `GET /lab2/publishers/{id}`
- `PUT /lab2/publishers/{id}`
- `DELETE /lab2/publishers/{id}`

### Video Game Endpoints

#### Create Video Game
```bash
curl -X POST http://localhost:8080/lab2/videogames ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"The Witcher 3\",\"releaseYear\":2015,\"price\":39.99,\"developerId\":1,\"publisherId\":1,\"genreId\":2}"
```

#### Get All Video Games (with optional filters)
```bash
# All games
curl http://localhost:8080/lab2/videogames

# Filter by genre
curl "http://localhost:8080/lab2/videogames?genreId=2"

# Filter by price range
curl "http://localhost:8080/lab2/videogames?minPrice=20&maxPrice=50"

# Filter by release year
curl "http://localhost:8080/lab2/videogames?releaseYear=2020"

# Multiple filters
curl "http://localhost:8080/lab2/videogames?genreId=1&minPrice=30&releaseYear=2020"
```

#### Get Video Game by ID
```bash
curl http://localhost:8080/lab2/videogames/1
```

#### Update Video Game (Full Update)
```bash
curl -X PUT http://localhost:8080/lab2/videogames/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"The Witcher 3: Wild Hunt\",\"releaseYear\":2015,\"price\":29.99,\"developerId\":1,\"publisherId\":1,\"genreId\":2}"
```

#### Partial Update Video Game
```bash
# Update only price
curl -X PATCH http://localhost:8080/lab2/videogames/1 ^
  -H "Content-Type: application/json" ^
  -d "{\"price\":19.99}"
```

#### Delete Video Game
```bash
curl -X DELETE http://localhost:8080/lab2/videogames/1
```

## Testing

### Run Unit Tests
```bash
.\gradlew.bat test --tests "com.example.demo.Lab2.*"
```

### Run All Tests
```bash
.\gradlew.bat test
```

## Sample Test Workflow

Here's a complete workflow to test the application:

1. **Start the application**
   ```bash
   .\gradlew.bat bootRun
   ```

2. **Create a genre**
   ```bash
   curl -X POST http://localhost:8080/lab2/genres -H "Content-Type: application/json" -d "{\"name\":\"RPG\",\"description\":\"Role-playing games\"}"
   ```

3. **Create a developer**
   ```bash
   curl -X POST http://localhost:8080/lab2/developers -H "Content-Type: application/json" -d "{\"name\":\"CD Projekt Red\",\"country\":\"Poland\",\"foundedYear\":1994}"
   ```

4. **Create a publisher**
   ```bash
   curl -X POST http://localhost:8080/lab2/publishers -H "Content-Type: application/json" -d "{\"name\":\"CD Projekt\",\"country\":\"Poland\",\"foundedYear\":1994}"
   ```

5. **Create a video game** (use IDs from previous steps)
   ```bash
   curl -X POST http://localhost:8080/lab2/videogames -H "Content-Type: application/json" -d "{\"title\":\"The Witcher 3\",\"releaseYear\":2015,\"price\":39.99,\"developerId\":1,\"publisherId\":1,\"genreId\":1}"
   ```

6. **Retrieve all games**
   ```bash
   curl http://localhost:8080/lab2/videogames
   ```

## Error Handling

The API returns standardized error responses:

### 404 Not Found
```json
{
  "error": "Not Found",
  "message": "VideoGame with id 999 not found",
  "timestamp": "2025-10-17T21:30:00"
}
```

### 400 Bad Request
```json
{
  "error": "Bad Request",
  "message": "Price must be non-negative",
  "timestamp": "2025-10-17T21:30:00",
  "field": "price"
}
```

### 409 Conflict
```json
{
  "error": "Conflict",
  "message": "Developer with name 'CD Projekt Red' already exists",
  "timestamp": "2025-10-17T21:30:00"
}
```

## Business Rules

1. **Video Games**:
   - Title is required (max 255 characters)
   - Release year must be between 1970 and current year + 2
   - Price must be non-negative
   - Must have valid developer, publisher, and genre references

2. **Developers/Publishers**:
   - Name must be unique
   - Cannot delete if associated with games
   - Founded year must be >= 1950

3. **Genres**:
   - Name must be unique
   - Cannot delete if games belong to this genre
   - Genre names are automatically capitalized

## Troubleshooting

### Database Connection Issues

If you see connection errors:
1. Verify PostgreSQL is running
2. Check database credentials in `application.properties`
3. Ensure database `lab2_videogames` exists

### Port Already in Use

If port 8080 is occupied, add to `application.properties`:
```properties
server.port=8081
```

## Migration from Lab1

Key differences from Lab1:
- **Persistent Storage**: Data survives application restarts
- **Relational Model**: Entities are linked via foreign keys
- **Advanced Queries**: Support for filtering and complex lookups
- **Transaction Management**: ACID guarantees for data operations
- **Layered Architecture**: Clear separation of concerns (Controller → Service → Repository)

## License

Educational project for Spring Boot and PostgreSQL demonstration.
