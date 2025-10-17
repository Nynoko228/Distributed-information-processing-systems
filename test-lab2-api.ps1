# Lab2 API Test Script
# This script tests all CRUD endpoints for the Lab2 Video Games API
# Make sure the application is running before executing this script

$baseUrl = "http://localhost:8080/lab2"
$headers = @{
    "Content-Type" = "application/json"
}

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Lab2 Video Games API Test Script" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Function to make API calls and display results
function Test-Endpoint {
    param(
        [string]$Method,
        [string]$Url,
        [string]$Body = $null,
        [string]$Description
    )
    
    Write-Host "Testing: $Description" -ForegroundColor Yellow
    Write-Host "  Method: $Method" -ForegroundColor Gray
    Write-Host "  URL: $Url" -ForegroundColor Gray
    
    try {
        if ($Body) {
            Write-Host "  Body: $Body" -ForegroundColor Gray
            $response = Invoke-WebRequest -Uri $Url -Method $Method -Headers $headers -Body $Body -UseBasicParsing
        } else {
            $response = Invoke-WebRequest -Uri $Url -Method $Method -UseBasicParsing
        }
        
        Write-Host "  Status: $($response.StatusCode) $($response.StatusDescription)" -ForegroundColor Green
        $content = $response.Content | ConvertFrom-Json | ConvertTo-Json -Depth 10
        Write-Host "  Response: $content" -ForegroundColor Green
        Write-Host ""
        return $response
    }
    catch {
        Write-Host "  Error: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host ""
        return $null
    }
}

# Wait for user confirmation
Write-Host "Make sure the application is running on $baseUrl" -ForegroundColor Yellow
Write-Host "Press any key to start testing..." -ForegroundColor Yellow
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
Write-Host ""

# ========================================
# Test 1: Create Genres
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test 1: Create Genres" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

$genreBody1 = @{
    name = "RPG"
    description = "Role-playing games with character development"
} | ConvertTo-Json

$genre1 = Test-Endpoint -Method "POST" -Url "$baseUrl/genres" -Body $genreBody1 -Description "Create RPG Genre"

$genreBody2 = @{
    name = "Action"
    description = "Fast-paced action games"
} | ConvertTo-Json

$genre2 = Test-Endpoint -Method "POST" -Url "$baseUrl/genres" -Body $genreBody2 -Description "Create Action Genre"

# ========================================
# Test 2: Get All Genres
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test 2: Get All Genres" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

Test-Endpoint -Method "GET" -Url "$baseUrl/genres" -Description "Get All Genres"

# ========================================
# Test 3: Create Developers
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test 3: Create Developers" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

$developerBody1 = @{
    name = "CD Projekt Red"
    country = "Poland"
    foundedYear = 1994
} | ConvertTo-Json

Test-Endpoint -Method "POST" -Url "$baseUrl/developers" -Body $developerBody1 -Description "Create CD Projekt Red"

$developerBody2 = @{
    name = "FromSoftware"
    country = "Japan"
    foundedYear = 1986
} | ConvertTo-Json

Test-Endpoint -Method "POST" -Url "$baseUrl/developers" -Body $developerBody2 -Description "Create FromSoftware"

# ========================================
# Test 4: Get All Developers
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test 4: Get All Developers" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

Test-Endpoint -Method "GET" -Url "$baseUrl/developers" -Description "Get All Developers"

# ========================================
# Test 5: Create Publishers
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test 5: Create Publishers" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

$publisherBody1 = @{
    name = "CD Projekt"
    country = "Poland"
    foundedYear = 1994
} | ConvertTo-Json

Test-Endpoint -Method "POST" -Url "$baseUrl/publishers" -Body $publisherBody1 -Description "Create CD Projekt Publisher"

$publisherBody2 = @{
    name = "Bandai Namco"
    country = "Japan"
    foundedYear = 2005
} | ConvertTo-Json

Test-Endpoint -Method "POST" -Url "$baseUrl/publishers" -Body $publisherBody2 -Description "Create Bandai Namco Publisher"

# ========================================
# Test 6: Create Video Games
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test 6: Create Video Games" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

$gameBody1 = @{
    title = "The Witcher 3: Wild Hunt"
    releaseYear = 2015
    price = 39.99
    developerId = 1
    publisherId = 1
    genreId = 1
} | ConvertTo-Json

Test-Endpoint -Method "POST" -Url "$baseUrl/videogames" -Body $gameBody1 -Description "Create The Witcher 3"

$gameBody2 = @{
    title = "Elden Ring"
    releaseYear = 2022
    price = 59.99
    developerId = 2
    publisherId = 2
    genreId = 2
} | ConvertTo-Json

Test-Endpoint -Method "POST" -Url "$baseUrl/videogames" -Body $gameBody2 -Description "Create Elden Ring"

# ========================================
# Test 7: Get All Video Games
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test 7: Get All Video Games" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

Test-Endpoint -Method "GET" -Url "$baseUrl/videogames" -Description "Get All Video Games"

# ========================================
# Test 8: Get Video Game by ID
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test 8: Get Video Game by ID" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

Test-Endpoint -Method "GET" -Url "$baseUrl/videogames/1" -Description "Get Video Game ID 1"

# ========================================
# Test 9: Filter Video Games
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test 9: Filter Video Games" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

Test-Endpoint -Method "GET" -Url "$baseUrl/videogames?genreId=1" -Description "Filter by Genre (RPG)"
Test-Endpoint -Method "GET" -Url "$baseUrl/videogames?minPrice=40&maxPrice=70" -Description "Filter by Price Range (40-70)"

# ========================================
# Test 10: Update Video Game (PATCH)
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test 10: Partial Update Video Game" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

$patchBody = @{
    price = 29.99
} | ConvertTo-Json

Test-Endpoint -Method "PATCH" -Url "$baseUrl/videogames/1" -Body $patchBody -Description "Update Price of Witcher 3"

# ========================================
# Test 11: Update Developer (PUT)
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test 11: Update Developer" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

$updateDevBody = @{
    name = "CD Projekt Red"
    country = "Poland"
    foundedYear = 1994
} | ConvertTo-Json

Test-Endpoint -Method "PUT" -Url "$baseUrl/developers/1" -Body $updateDevBody -Description "Update CD Projekt Red"

# ========================================
# Test 12: Test Error Handling
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test 12: Error Handling Tests" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

Test-Endpoint -Method "GET" -Url "$baseUrl/videogames/999" -Description "Get Non-existent Game (expect 404)"

$invalidGameBody = @{
    title = "Invalid Game"
    releaseYear = 2100
    price = -10
    developerId = 1
    publisherId = 1
    genreId = 1
} | ConvertTo-Json

Test-Endpoint -Method "POST" -Url "$baseUrl/videogames" -Body $invalidGameBody -Description "Create Game with Invalid Data (expect 400)"

# ========================================
# Test Summary
# ========================================
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test Script Completed!" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "You can now verify the data persistence by:" -ForegroundColor Yellow
Write-Host "1. Stopping the application" -ForegroundColor Yellow
Write-Host "2. Restarting it" -ForegroundColor Yellow
Write-Host "3. Running: curl http://localhost:8080/lab2/videogames" -ForegroundColor Yellow
Write-Host "   The data should still be present!" -ForegroundColor Yellow
