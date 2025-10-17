// API Base URL
const API_BASE = '/lab2';

// Текущая редактируемая игра
let currentEditGameId = null;

// Инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', function() {
    // Загружаем справочные данные
    loadGenres();
    loadDevelopers();
    loadPublishers();
    
    // Загружаем список игр
    loadGames();
});

// ==================== УПРАВЛЕНИЕ ТАБАМИ ====================

function switchTab(tabName) {
    // Скрываем все вкладки
    const tabs = document.querySelectorAll('.tab-content');
    tabs.forEach(tab => tab.classList.remove('active'));
    
    // Убираем активность со всех кнопок
    const buttons = document.querySelectorAll('.tab-button');
    buttons.forEach(btn => btn.classList.remove('active'));
    
    // Показываем выбранную вкладку
    document.getElementById(tabName + 'Tab').classList.add('active');
    event.target.classList.add('active');
    
    // Загружаем данные для вкладки
    switch(tabName) {
        case 'games':
            loadGames();
            break;
        case 'developers':
            loadDevelopers();
            break;
        case 'publishers':
            loadPublishers();
            break;
        case 'genres':
            loadGenres();
            break;
    }
}

// ==================== ЖАНРЫ ====================

async function loadGenres() {
    try {
        const response = await fetch(`${API_BASE}/genres`);
        const data = await response.json();
        
        displayGenres(data.genres || []);
        populateGenreSelects(data.genres || []);
    } catch (error) {
        console.error('Ошибка загрузки жанров:', error);
        showMessage('genreMessage', 'Ошибка загрузки жанров', 'error');
    }
}

function displayGenres(genres) {
    const container = document.getElementById('genresList');
    
    if (genres.length === 0) {
        container.innerHTML = '<p class="loading">Жанры не найдены</p>';
        return;
    }
    
    container.innerHTML = genres.map(genre => `
        <div class="item-card">
            <div class="item-header">
                <div>
                    <div class="item-title">${escapeHtml(genre.name)}</div>
                    <p style="color: #666; margin-top: 5px;">${escapeHtml(genre.description || 'Нет описания')}</p>
                </div>
                <div class="item-actions">
                    <span class="item-badge">${genre.gamesCount} игр</span>
                    <button class="btn-delete" onclick="deleteGenre(${genre.id})">Удалить</button>
                </div>
            </div>
        </div>
    `).join('');
}

function populateGenreSelects(genres) {
    const selects = ['gameGenre', 'editGameGenre', 'filterGenre'];
    
    selects.forEach(selectId => {
        const select = document.getElementById(selectId);
        if (!select) return;
        
        const currentValue = select.value;
        const isFilter = selectId === 'filterGenre';
        
        select.innerHTML = isFilter ? 
            '<option value="">Все жанры</option>' : 
            '<option value="">Выберите жанр</option>';
        
        genres.forEach(genre => {
            const option = document.createElement('option');
            option.value = genre.id;
            option.textContent = genre.name;
            select.appendChild(option);
        });
        
        if (currentValue) {
            select.value = currentValue;
        }
    });
}

async function createGenre() {
    const name = document.getElementById('genreName').value.trim();
    const description = document.getElementById('genreDesc').value.trim();
    
    if (!name) {
        showMessage('genreMessage', 'Введите название жанра', 'error');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/genres`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, description })
        });
        
        if (response.ok) {
            showMessage('genreMessage', 'Жанр успешно добавлен!', 'success');
            document.getElementById('genreName').value = '';
            document.getElementById('genreDesc').value = '';
            loadGenres();
        } else {
            const error = await response.json();
            showMessage('genreMessage', error.message || 'Ошибка создания жанра', 'error');
        }
    } catch (error) {
        showMessage('genreMessage', 'Ошибка соединения с сервером', 'error');
    }
}

async function deleteGenre(id) {
    if (!confirm('Удалить этот жанр?')) return;
    
    try {
        const response = await fetch(`${API_BASE}/genres/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            loadGenres();
        } else {
            const error = await response.json();
            alert(error.message || 'Ошибка удаления жанра');
        }
    } catch (error) {
        alert('Ошибка соединения с сервером');
    }
}

// ==================== РАЗРАБОТЧИКИ ====================

async function loadDevelopers() {
    try {
        const response = await fetch(`${API_BASE}/developers`);
        const data = await response.json();
        
        displayDevelopers(data.developers || []);
        populateDeveloperSelects(data.developers || []);
    } catch (error) {
        console.error('Ошибка загрузки разработчиков:', error);
        showMessage('devMessage', 'Ошибка загрузки разработчиков', 'error');
    }
}

function displayDevelopers(developers) {
    const container = document.getElementById('developersList');
    
    if (developers.length === 0) {
        container.innerHTML = '<p class="loading">Разработчики не найдены</p>';
        return;
    }
    
    container.innerHTML = developers.map(dev => `
        <div class="item-card">
            <div class="item-header">
                <div>
                    <div class="item-title">${escapeHtml(dev.name)}</div>
                    <div class="item-details" style="margin-top: 8px;">
                        <div class="item-detail">
                            <strong>Страна:</strong> ${escapeHtml(dev.country || 'Не указана')}
                        </div>
                        <div class="item-detail">
                            <strong>Год основания:</strong> ${dev.foundedYear || 'Не указан'}
                        </div>
                    </div>
                </div>
                <div class="item-actions">
                    <span class="item-badge">${dev.gamesCount} игр</span>
                    <button class="btn-delete" onclick="deleteDeveloper(${dev.id})">Удалить</button>
                </div>
            </div>
        </div>
    `).join('');
}

function populateDeveloperSelects(developers) {
    const selects = ['gameDeveloper', 'editGameDeveloper', 'filterDeveloper'];
    
    selects.forEach(selectId => {
        const select = document.getElementById(selectId);
        if (!select) return;
        
        const currentValue = select.value;
        const isFilter = selectId === 'filterDeveloper';
        
        select.innerHTML = isFilter ? 
            '<option value="">Все разработчики</option>' : 
            '<option value="">Выберите разработчика</option>';
        
        developers.forEach(dev => {
            const option = document.createElement('option');
            option.value = dev.id;
            option.textContent = dev.name;
            select.appendChild(option);
        });
        
        if (currentValue) {
            select.value = currentValue;
        }
    });
}

async function createDeveloper() {
    const name = document.getElementById('devName').value.trim();
    const country = document.getElementById('devCountry').value.trim();
    const foundedYear = document.getElementById('devYear').value;
    
    if (!name) {
        showMessage('devMessage', 'Введите название компании', 'error');
        return;
    }
    
    const data = { name };
    if (country) data.country = country;
    if (foundedYear) data.foundedYear = parseInt(foundedYear);
    
    try {
        const response = await fetch(`${API_BASE}/developers`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            showMessage('devMessage', 'Разработчик успешно добавлен!', 'success');
            document.getElementById('devName').value = '';
            document.getElementById('devCountry').value = '';
            document.getElementById('devYear').value = '';
            loadDevelopers();
        } else {
            const error = await response.json();
            showMessage('devMessage', error.message || 'Ошибка создания разработчика', 'error');
        }
    } catch (error) {
        showMessage('devMessage', 'Ошибка соединения с сервером', 'error');
    }
}

async function deleteDeveloper(id) {
    if (!confirm('Удалить этого разработчика?')) return;
    
    try {
        const response = await fetch(`${API_BASE}/developers/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            loadDevelopers();
        } else {
            const error = await response.json();
            alert(error.message || 'Ошибка удаления разработчика');
        }
    } catch (error) {
        alert('Ошибка соединения с сервером');
    }
}

// ==================== ИЗДАТЕЛИ ====================

async function loadPublishers() {
    try {
        const response = await fetch(`${API_BASE}/publishers`);
        const data = await response.json();
        
        displayPublishers(data.publishers || []);
        populatePublisherSelects(data.publishers || []);
    } catch (error) {
        console.error('Ошибка загрузки издателей:', error);
        showMessage('pubMessage', 'Ошибка загрузки издателей', 'error');
    }
}

function displayPublishers(publishers) {
    const container = document.getElementById('publishersList');
    
    if (publishers.length === 0) {
        container.innerHTML = '<p class="loading">Издатели не найдены</p>';
        return;
    }
    
    container.innerHTML = publishers.map(pub => `
        <div class="item-card">
            <div class="item-header">
                <div>
                    <div class="item-title">${escapeHtml(pub.name)}</div>
                    <div class="item-details" style="margin-top: 8px;">
                        <div class="item-detail">
                            <strong>Страна:</strong> ${escapeHtml(pub.country || 'Не указана')}
                        </div>
                        <div class="item-detail">
                            <strong>Год основания:</strong> ${pub.foundedYear || 'Не указан'}
                        </div>
                    </div>
                </div>
                <div class="item-actions">
                    <span class="item-badge">${pub.gamesCount} игр</span>
                    <button class="btn-delete" onclick="deletePublisher(${pub.id})">Удалить</button>
                </div>
            </div>
        </div>
    `).join('');
}

function populatePublisherSelects(publishers) {
    const selects = ['gamePublisher', 'editGamePublisher'];
    
    selects.forEach(selectId => {
        const select = document.getElementById(selectId);
        if (!select) return;
        
        const currentValue = select.value;
        
        select.innerHTML = '<option value="">Выберите издателя</option>';
        
        publishers.forEach(pub => {
            const option = document.createElement('option');
            option.value = pub.id;
            option.textContent = pub.name;
            select.appendChild(option);
        });
        
        if (currentValue) {
            select.value = currentValue;
        }
    });
}

async function createPublisher() {
    const name = document.getElementById('pubName').value.trim();
    const country = document.getElementById('pubCountry').value.trim();
    const foundedYear = document.getElementById('pubYear').value;
    
    if (!name) {
        showMessage('pubMessage', 'Введите название компании', 'error');
        return;
    }
    
    const data = { name };
    if (country) data.country = country;
    if (foundedYear) data.foundedYear = parseInt(foundedYear);
    
    try {
        const response = await fetch(`${API_BASE}/publishers`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            showMessage('pubMessage', 'Издатель успешно добавлен!', 'success');
            document.getElementById('pubName').value = '';
            document.getElementById('pubCountry').value = '';
            document.getElementById('pubYear').value = '';
            loadPublishers();
        } else {
            const error = await response.json();
            showMessage('pubMessage', error.message || 'Ошибка создания издателя', 'error');
        }
    } catch (error) {
        showMessage('pubMessage', 'Ошибка соединения с сервером', 'error');
    }
}

async function deletePublisher(id) {
    if (!confirm('Удалить этого издателя?')) return;
    
    try {
        const response = await fetch(`${API_BASE}/publishers/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            loadPublishers();
        } else {
            const error = await response.json();
            alert(error.message || 'Ошибка удаления издателя');
        }
    } catch (error) {
        alert('Ошибка соединения с сервером');
    }
}

// ==================== ВИДЕОИГРЫ ====================

async function loadGames() {
    try {
        // Собираем параметры фильтрации
        const params = new URLSearchParams();
        
        const genreId = document.getElementById('filterGenre')?.value;
        const developerId = document.getElementById('filterDeveloper')?.value;
        const year = document.getElementById('filterYear')?.value;
        const minPrice = document.getElementById('filterMinPrice')?.value;
        const maxPrice = document.getElementById('filterMaxPrice')?.value;
        
        if (genreId) params.append('genreId', genreId);
        if (developerId) params.append('developerId', developerId);
        if (year) params.append('releaseYear', year);
        if (minPrice) params.append('minPrice', minPrice);
        if (maxPrice) params.append('maxPrice', maxPrice);
        
        const url = `${API_BASE}/videogames${params.toString() ? '?' + params.toString() : ''}`;
        const response = await fetch(url);
        const data = await response.json();
        
        displayGames(data.games || []);
    } catch (error) {
        console.error('Ошибка загрузки игр:', error);
        document.getElementById('gamesList').innerHTML = '<p class="loading">Ошибка загрузки игр</p>';
    }
}

function displayGames(games) {
    const container = document.getElementById('gamesList');
    
    if (games.length === 0) {
        container.innerHTML = '<p class="loading">Игры не найдены</p>';
        return;
    }
    
    container.innerHTML = games.map(game => `
        <div class="item-card">
            <div class="item-header">
                <div style="flex: 1;">
                    <div class="item-title">${escapeHtml(game.title)}</div>
                    <div class="item-details" style="margin-top: 10px;">
                        <div class="item-detail">
                            <strong>Год:</strong> ${game.releaseYear}
                        </div>
                        <div class="item-detail">
                            <strong>Цена:</strong> $${game.price}
                        </div>
                        <div class="item-detail">
                            <strong>Разработчик:</strong> ${escapeHtml(game.developer.name)}
                        </div>
                        <div class="item-detail">
                            <strong>Издатель:</strong> ${escapeHtml(game.publisher.name)}
                        </div>
                        <div class="item-detail">
                            <strong>Жанр:</strong> ${escapeHtml(game.genre.name)}
                        </div>
                    </div>
                </div>
                <div class="item-actions">
                    <button class="btn-edit" onclick="openEditModal(${game.id})">Редактировать</button>
                    <button class="btn-delete" onclick="deleteGame(${game.id})">Удалить</button>
                </div>
            </div>
        </div>
    `).join('');
}

async function createGame() {
    const title = document.getElementById('gameTitle').value.trim();
    const releaseYear = parseInt(document.getElementById('gameYear').value);
    const price = parseFloat(document.getElementById('gamePrice').value);
    const developerId = parseInt(document.getElementById('gameDeveloper').value);
    const publisherId = parseInt(document.getElementById('gamePublisher').value);
    const genreId = parseInt(document.getElementById('gameGenre').value);
    
    if (!title || !releaseYear || !price || !developerId || !publisherId || !genreId) {
        showMessage('gameMessage', 'Заполните все поля', 'error');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/videogames`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                title,
                releaseYear,
                price,
                developerId,
                publisherId,
                genreId
            })
        });
        
        if (response.ok) {
            showMessage('gameMessage', 'Игра успешно добавлена!', 'success');
            // Очищаем форму
            document.getElementById('gameTitle').value = '';
            document.getElementById('gameYear').value = '';
            document.getElementById('gamePrice').value = '';
            document.getElementById('gameDeveloper').value = '';
            document.getElementById('gamePublisher').value = '';
            document.getElementById('gameGenre').value = '';
            loadGames();
        } else {
            const error = await response.json();
            showMessage('gameMessage', error.message || 'Ошибка создания игры', 'error');
        }
    } catch (error) {
        showMessage('gameMessage', 'Ошибка соединения с сервером', 'error');
    }
}

async function openEditModal(gameId) {
    try {
        const response = await fetch(`${API_BASE}/videogames/${gameId}`);
        const game = await response.json();
        
        currentEditGameId = gameId;
        
        // Заполняем форму редактирования
        document.getElementById('editGameTitle').value = game.title;
        document.getElementById('editGameYear').value = game.releaseYear;
        document.getElementById('editGamePrice').value = game.price;
        document.getElementById('editGameDeveloper').value = game.developer.id;
        document.getElementById('editGamePublisher').value = game.publisher.id;
        document.getElementById('editGameGenre').value = game.genre.id;
        
        // Показываем модальное окно
        document.getElementById('editGameModal').classList.add('active');
    } catch (error) {
        alert('Ошибка загрузки данных игры');
    }
}

function closeEditModal() {
    document.getElementById('editGameModal').classList.remove('active');
    currentEditGameId = null;
}

async function updateGame() {
    if (!currentEditGameId) return;
    
    const title = document.getElementById('editGameTitle').value.trim();
    const releaseYear = parseInt(document.getElementById('editGameYear').value);
    const price = parseFloat(document.getElementById('editGamePrice').value);
    const developerId = parseInt(document.getElementById('editGameDeveloper').value);
    const publisherId = parseInt(document.getElementById('editGamePublisher').value);
    const genreId = parseInt(document.getElementById('editGameGenre').value);
    
    if (!title || !releaseYear || !price || !developerId || !publisherId || !genreId) {
        alert('Заполните все поля');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/videogames/${currentEditGameId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                title,
                releaseYear,
                price,
                developerId,
                publisherId,
                genreId
            })
        });
        
        if (response.ok) {
            closeEditModal();
            loadGames();
        } else {
            const error = await response.json();
            alert(error.message || 'Ошибка обновления игры');
        }
    } catch (error) {
        alert('Ошибка соединения с сервером');
    }
}

async function deleteGame(id) {
    if (!confirm('Удалить эту игру?')) return;
    
    try {
        const response = await fetch(`${API_BASE}/videogames/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            loadGames();
        } else {
            const error = await response.json();
            alert(error.message || 'Ошибка удаления игры');
        }
    } catch (error) {
        alert('Ошибка соединения с сервером');
    }
}

function clearFilters() {
    document.getElementById('filterGenre').value = '';
    document.getElementById('filterDeveloper').value = '';
    document.getElementById('filterYear').value = '';
    document.getElementById('filterMinPrice').value = '';
    document.getElementById('filterMaxPrice').value = '';
    loadGames();
}

// ==================== ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ====================

function showMessage(elementId, message, type) {
    const element = document.getElementById(elementId);
    element.textContent = message;
    element.className = 'message ' + type;
    
    setTimeout(() => {
        element.textContent = '';
        element.className = 'message';
    }, 5000);
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Закрытие модального окна по клику вне его
document.addEventListener('click', function(event) {
    const modal = document.getElementById('editGameModal');
    if (event.target === modal) {
        closeEditModal();
    }
});
