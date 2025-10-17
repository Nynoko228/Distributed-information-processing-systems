# Lab2 API Testing Guide

## Prerequisites

1. **PostgreSQL must be installed and running**
   - Default connection: `localhost:5432`
   - Database: `lab2_videogames` (will be created automatically if using `ddl-auto=update`)
   - Username: `postgres`
   - Password: `postgres` (update in `application.properties` if different)

2. **Start the application**
   ```bash
   .\gradlew.bat bootRun
   ```
   
   Wait for the message: `Started DemoApplicationKt in X.XXX seconds`

## Quick Test Sequence

### Step 1: Create Reference Data

#### Create a Genre
```bash
curl -X POST http://localhost:8080/lab2/genres -H "Content-Type: application/json" -d "{\"name\":\"RPG\",\"description\":\"Role-playing games\"}"
```

Expected: Status 201, returns genre with `id: 1`

#### Create a Developer
```bash
curl -X POST http://localhost:8080/lab2/developers -H "Content-Type: application/json" -d "{\"name\":\"CD Projekt Red\",\"country\":\"Poland\",\"foundedYear\":1994}"
```

Expected: Status 201, returns developer with `id: 1`

#### Create a Publisher
```bash
curl -X POST http://localhost:8080/lab2/publishers -H "Content-Type: application/json" -d "{\"name\":\"CD Projekt\",\"country\":\"Poland\",\"foundedYear\":1994}"
```

Expected: Status 201, returns publisher with `id: 1`

### Step 2: Create a Video Game

```bash
curl -X POST http://localhost:8080/lab2/videogames -H "Content-Type: application/json" -d "{\"title\":\"The Witcher 3\",\"releaseYear\":2015,\"price\":39.99,\"developerId\":1,\"publisherId\":1,\"genreId\":1}"
```

Expected: Status 201, returns complete game object with nested developer, publisher, and genre

### Step 3: Retrieve Data

#### Get All Games
```bash
curl http://localhost:8080/lab2/videogames
```

#### Get Specific Game
```bash
curl http://localhost:8080/lab2/videogames/1
```

#### Get All Genres
```bash
curl http://localhost:8080/lab2/genres
```

### Step 4: Update Operations

#### Full Update (PUT)
```bash
curl -X PUT http://localhost:8080/lab2/videogames/1 -H "Content-Type: application/json" -d "{\"title\":\"The Witcher 3: Wild Hunt\",\"releaseYear\":2015,\"price\":29.99,\"developerId\":1,\"publisherId\":1,\"genreId\":1}"
```

#### Partial Update (PATCH) - Update only price
```bash
curl -X PATCH http://localhost:8080/lab2/videogames/1 -H "Content-Type: application/json" -d "{\"price\":19.99}"
```

### Step 5: Filtering

#### Filter by Genre
```bash
curl "http://localhost:8080/lab2/videogames?genreId=1"
```

#### Filter by Price Range
```bash
curl "http://localhost:8080/lab2/videogames?minPrice=20&maxPrice=50"
```

#### Filter by Release Year
```bash
curl "http://localhost:8080/lab2/videogames?releaseYear=2015"
```

### Step 6: Test Error Handling

#### Try to get non-existent game (expect 404)
```bash
curl http://localhost:8080/lab2/videogames/999
```

#### Try to create game with invalid price (expect 400)
```bash
curl -X POST http://localhost:8080/lab2/videogames -H "Content-Type: application/json" -d "{\"title\":\"Invalid\",\"releaseYear\":2020,\"price\":-10,\"developerId\":1,\"publisherId\":1,\"genreId\":1}"
```

#### Try to create duplicate developer (expect 409)
```bash
curl -X POST http://localhost:8080/lab2/developers -H "Content-Type: application/json" -d "{\"name\":\"CD Projekt Red\",\"country\":\"Poland\",\"foundedYear\":1994}"
```

### Step 7: Delete Operations

#### Try to delete genre with games (expect 409 - Conflict)
```bash
curl -X DELETE http://localhost:8080/lab2/genres/1
```

#### Delete a game first
```bash
curl -X DELETE http://localhost:8080/lab2/videogames/1
```

Expected: Status 204 No Content

#### Now delete the genre (should work)
```bash
curl -X DELETE http://localhost:8080/lab2/genres/1
```

Expected: Status 204 No Content

## Testing Data Persistence

1. **Stop the application** (Ctrl+C in the terminal)
2. **Restart it**: `.\gradlew.bat bootRun`
3. **Query the data**:
   ```bash
   curl http://localhost:8080/lab2/videogames
   ```
4. **Result**: All data should still be present (stored in PostgreSQL)

## Using PowerShell Test Script

For automated testing, run the provided PowerShell script:

```powershell
.\test-lab2-api.ps1
```

This will:
- Create multiple genres, developers, publishers
- Create video games
- Test all CRUD operations
- Test filtering
- Test error handling

## Verifying Database

You can connect to PostgreSQL to verify the data directly:

```sql
-- Connect to database
\c lab2_videogames

-- View tables
\dt

-- Query data
SELECT * FROM video_games;
SELECT * FROM developers;
SELECT * FROM publishers;
SELECT * FROM genres;

-- View game with relationships
SELECT vg.title, d.name as developer, p.name as publisher, g.name as genre, vg.price
FROM video_games vg
JOIN developers d ON vg.developer_id = d.id
JOIN publishers p ON vg.publisher_id = p.id
JOIN genres g ON vg.genre_id = g.id;
```

## Common Issues

### Issue: Connection refused
**Solution**: Make sure PostgreSQL is running and accessible on `localhost:5432`

### Issue: Database does not exist
**Solution**: Create it manually:
```sql
CREATE DATABASE lab2_videogames;
```

### Issue: Authentication failed
**Solution**: Update credentials in `src/main/resources/application.properties`

### Issue: Port 8080 already in use
**Solution**: Change port in `application.properties`:
```properties
server.port=8081
```

## Next Steps

After successful testing:
1. Review the code structure in `src/main/kotlin/com/example/demo/Lab2/`
2. Check the generated SQL in console output (when `show-sql=true`)
3. Explore JPA entity relationships
4. Review exception handling in `GlobalExceptionHandler`
5. Examine the service layer transaction management
