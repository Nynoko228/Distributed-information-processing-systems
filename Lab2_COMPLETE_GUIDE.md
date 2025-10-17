# 🎮 Lab2 Complete Setup Guide

## ✅ What Has Been Created

### Backend (Kotlin/Spring Boot)
- ✅ 4 JPA Entities with relationships
- ✅ 4 Repositories with custom queries
- ✅ 4 Services with business logic
- ✅ 4 REST Controllers with full CRUD
- ✅ Global exception handler
- ✅ PostgreSQL integration
- ✅ Database seed data
- ✅ 14 unit & integration tests

### Frontend (HTML/CSS/JavaScript)
- ✅ **index.html** - Main page with Lab2 link
- ✅ **lab2.html** - Database management interface
- ✅ **lab2Script.js** - Complete API integration
- ✅ **lab2Style.css** - Professional styling

## 🚀 Quick Start (5 Steps)

### Step 1: Setup PostgreSQL Database

Open PostgreSQL and run:
```sql
CREATE DATABASE lab2_videogames;
```

Or update credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/lab2_videogames
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### Step 2: Build the Application

```bash
.\gradlew.bat build
```

Expected: `BUILD SUCCESSFUL`

### Step 3: Run the Application

```bash
.\gradlew.bat bootRun
```

Wait for: `Started DemoApplicationKt in X.XXX seconds`

### Step 4: Open in Browser

Navigate to:
```
http://localhost:8080
```

You should see the main page with Lab1 and Lab2 cards.

### Step 5: Start Using Lab2

Click on **"Лабораторная работа №2"** card.

## 📋 Complete Testing Workflow

### A. Add Reference Data (Required First)

#### 1. Add Genres
- Click **"Жанры"** tab
- Enter name: `RPG`
- Enter description: `Role-playing games`
- Click **"Добавить жанр"**
- Repeat for: `Action`, `Strategy`, etc.

#### 2. Add Developers
- Click **"Разработчики"** tab
- Enter name: `CD Projekt Red`
- Enter country: `Poland`
- Enter year: `1994`
- Click **"Добавить разработчика"**
- Repeat for other developers

#### 3. Add Publishers
- Click **"Издатели"** tab
- Enter name: `CD Projekt`
- Enter country: `Poland`
- Enter year: `1994`
- Click **"Добавить издателя"**
- Repeat for other publishers

### B. Manage Video Games

#### 1. Create a Game
- Return to **"Видеоигры"** tab
- Fill the form:
  - **Название**: `The Witcher 3: Wild Hunt`
  - **Год выпуска**: `2015`
  - **Цена**: `39.99`
  - **Разработчик**: Select from dropdown
  - **Издатель**: Select from dropdown
  - **Жанр**: Select from dropdown
- Click **"Добавить игру"**
- Game appears in the list below

#### 2. Filter Games
- Use filter dropdowns:
  - **Жанр**: Select to filter by genre
  - **Разработчик**: Select to filter by developer
  - **Год выпуска**: Enter year
  - **Мин. цена / Макс. цена**: Set price range
- Results update automatically
- Click **"Сбросить фильтры"** to clear

#### 3. Edit a Game
- Find game in list
- Click **"Редактировать"** button
- Modal window opens
- Modify any fields
- Click **"Сохранить"**
- Changes persist in database

#### 4. Delete a Game
- Click **"Удалить"** button
- Confirm deletion
- Game removed from database

### C. Test Cascade Protection

Try deleting a genre/developer/publisher that has games:
- The system will prevent deletion
- Error message shows: "Cannot delete: X games associated"
- Must delete/reassign games first

### D. Verify Data Persistence

1. Add several games
2. Stop the application (Ctrl+C)
3. Restart: `.\gradlew.bat bootRun`
4. Refresh browser
5. **All data still there!** ✅

## 🎨 Frontend Features

### Tab Navigation
- **Видеоигры**: Manage game catalog
- **Разработчики**: Manage developers
- **Издатели**: Manage publishers
- **Жанры**: Manage genres

### Game Management
- ➕ Add new games
- ✏️ Edit existing games
- 🗑️ Delete games
- 🔍 Filter by multiple criteria
- 🔄 Real-time updates

### User Experience
- ✅ Form validation
- ✅ Success/error messages
- ✅ Confirmation dialogs
- ✅ Loading indicators
- ✅ Responsive design (mobile-friendly)
- ✅ Modern gradient UI
- ✅ Smooth animations

## 🌐 API Endpoints Used

The frontend connects to these backend endpoints:

### Video Games
- `GET /lab2/videogames` - List all (with filters)
- `POST /lab2/videogames` - Create new
- `GET /lab2/videogames/{id}` - Get by ID
- `PUT /lab2/videogames/{id}` - Update
- `DELETE /lab2/videogames/{id}` - Delete

### Developers
- `GET /lab2/developers` - List all
- `POST /lab2/developers` - Create new
- `DELETE /lab2/developers/{id}` - Delete

### Publishers
- `GET /lab2/publishers` - List all
- `POST /lab2/publishers` - Create new
- `DELETE /lab2/publishers/{id}` - Delete

### Genres
- `GET /lab2/genres` - List all
- `POST /lab2/genres` - Create new
- `DELETE /lab2/genres/{id}` - Delete

## 🔧 Troubleshooting

### Issue: "Failed to fetch"
**Cause**: Backend not running
**Solution**: 
```bash
.\gradlew.bat bootRun
```

### Issue: Connection refused to PostgreSQL
**Cause**: PostgreSQL not running
**Solution**: Start PostgreSQL service

### Issue: Database does not exist
**Cause**: Database not created
**Solution**:
```sql
CREATE DATABASE lab2_videogames;
```

### Issue: Empty dropdowns
**Cause**: No reference data
**Solution**: Add genres, developers, publishers first

### Issue: Port 8080 in use
**Solution**: Change port in `application.properties`:
```properties
server.port=8081
```
Then use `http://localhost:8081`

## 📊 Feature Comparison

| Feature | Lab1 | Lab2 |
|---------|------|------|
| Storage | In-memory | PostgreSQL |
| Persistence | ❌ Lost on restart | ✅ Permanent |
| Data Type | Primitives, Lists | Entities with relations |
| API | Simple CRUD | RESTful with filtering |
| UI | Basic forms | Modern tabs & modals |
| Validation | Frontend only | Frontend + Backend |
| Error Handling | Basic alerts | Comprehensive messages |

## 📁 Project Structure

```
demo/
├── src/
│   ├── main/
│   │   ├── kotlin/com/example/demo/
│   │   │   ├── Lab1/          (Lab1 controllers)
│   │   │   ├── Lab2/          (Lab2 backend)
│   │   │   │   ├── controllers/
│   │   │   │   ├── services/
│   │   │   │   ├── repositories/
│   │   │   │   ├── entities/
│   │   │   │   ├── dto/
│   │   │   │   └── exceptions/
│   │   │   └── DemoApplication.kt
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html      ← Main page
│   │       │   ├── lab1.html       ← Lab1 UI
│   │       │   ├── lab2.html       ← Lab2 UI (NEW)
│   │       │   ├── lab2Script.js   ← API calls (NEW)
│   │       │   └── lab2Style.css   ← Styling (NEW)
│   │       └── application.properties
│   └── test/                   (14 tests)
├── build.gradle
└── Documentation/
    ├── Lab2_README.md
    ├── Lab2_TESTING.md
    ├── Lab2_FRONTEND_SUMMARY.md
    └── QUICKSTART.md
```

## 🎯 Success Indicators

You've successfully set up Lab2 if:
- ✅ Application starts without errors
- ✅ You can access `http://localhost:8080`
- ✅ Lab2 card is clickable (not grayed out)
- ✅ Lab2 page loads with 4 tabs
- ✅ You can add genres, developers, publishers
- ✅ You can create video games
- ✅ Filters work correctly
- ✅ Edit modal opens and saves
- ✅ Data persists after restart

## 📚 Next Steps

1. **Explore the code**:
   - Backend: `src/main/kotlin/com/example/demo/Lab2/`
   - Frontend: `src/main/resources/static/lab2*`

2. **Read documentation**:
   - `Lab2_README.md` - Comprehensive guide
   - `Lab2_TESTING.md` - API testing
   - `Lab2_FRONTEND_SUMMARY.md` - UI details

3. **Experiment**:
   - Add more games
   - Try different filters
   - Test error scenarios
   - Check database directly

4. **Customize**:
   - Modify colors in `lab2Style.css`
   - Add new fields to entities
   - Create new filters
   - Enhance UI with more features

## 🌟 Summary

**Lab2 is now fully operational!**

- ✅ Backend: 4-layer architecture with PostgreSQL
- ✅ Frontend: Modern, responsive web interface
- ✅ Full CRUD: Create, Read, Update, Delete
- ✅ Advanced features: Filtering, validation, error handling
- ✅ Production-ready: Tests passing, documentation complete

**Total Implementation**:
- ~2,000 lines of Kotlin (backend)
- ~1,300 lines of HTML/CSS/JS (frontend)
- 14 automated tests
- Complete documentation

Enjoy managing your video game catalog! 🎮✨
