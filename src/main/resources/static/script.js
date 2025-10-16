// ========================================
// РАБОТА С ЧИСЛАМИ
// ========================================

// Функция для сохранения числа
function saveNumber() {
    // Получаем значение из поля ввода
    const number = document.getElementById('numberInput').value;
    
    // Проверяем, что число введено
    if (number === '') {
        alert('Пожалуйста, введите число!');
        return;
    }
    
    // Отправляем число на сервер
    fetch('/Lab1/primitives/number', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ value: parseInt(number) })
    })
    .then(response => response.json())
    .then(data => {
        // Показываем результат
        document.getElementById('numberResult').textContent = data.value;
        // Очищаем поле ввода
        document.getElementById('numberInput').value = '';
    })
    .catch(error => {
        alert('Ошибка при сохранении числа!');
        console.error(error);
    });
}

// Функция для показа сохраненного числа
function showNumber() {
    // Запрашиваем число с сервера
    fetch('/Lab1/primitives/number')
    .then(response => response.json())
    .then(data => {
        // Показываем число или прочерк, если число не сохранено
        if (data.value !== null) {
            document.getElementById('numberResult').textContent = data.value;
        } else {
            document.getElementById('numberResult').textContent = '-';
        }
    })
    .catch(error => {
        alert('Ошибка при получении числа!');
        console.error(error);
    });
}

// ========================================
// РАБОТА С ТЕКСТОМ
// ========================================

// Функция для сохранения текста
function saveText() {
    // Получаем текст из поля ввода
    const text = document.getElementById('textInput').value;
    
    // Проверяем, что текст введен
    if (text === '') {
        alert('Пожалуйста, введите текст!');
        return;
    }
    
    // Отправляем текст на сервер
    fetch('/Lab1/primitives/string', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ value: text })
    })
    .then(response => response.json())
    .then(data => {
        // Показываем результат
        document.getElementById('textResult').textContent = data.value;
        // Очищаем поле ввода
        document.getElementById('textInput').value = '';
    })
    .catch(error => {
        alert('Ошибка при сохранении текста!');
        console.error(error);
    });
}

// Функция для показа сохраненного текста
function showText() {
    // Запрашиваем текст с сервера
    fetch('/Lab1/primitives/string')
    .then(response => response.json())
    .then(data => {
        // Показываем текст или прочерк, если текст не сохранен
        if (data.value !== null) {
            document.getElementById('textResult').textContent = data.value;
        } else {
            document.getElementById('textResult').textContent = '-';
        }
    })
    .catch(error => {
        alert('Ошибка при получении текста!');
        console.error(error);
    });
}

// ========================================
// РАБОТА С ИСТИНОЙ/ЛОЖЬЮ
// ========================================

// Функция для сохранения значения истина/ложь
function saveBoolean() {
    // Получаем выбранное значение
    const value = document.getElementById('booleanInput').value;
    const boolValue = (value === 'true');
    
    // Отправляем значение на сервер
    fetch('/Lab1/primitives/boolean', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ value: boolValue })
    })
    .then(response => response.json())
    .then(data => {
        // Показываем результат (Да или Нет)
        document.getElementById('booleanResult').textContent = data.value ? 'Да' : 'Нет';
    })
    .catch(error => {
        alert('Ошибка при сохранении значения!');
        console.error(error);
    });
}

// Функция для показа сохраненного значения истина/ложь
function showBoolean() {
    // Запрашиваем значение с сервера
    fetch('/Lab1/primitives/boolean')
    .then(response => response.json())
    .then(data => {
        // Показываем значение или прочерк, если значение не сохранено
        if (data.value !== null) {
            document.getElementById('booleanResult').textContent = data.value ? 'Да' : 'Нет';
        } else {
            document.getElementById('booleanResult').textContent = '-';
        }
    })
    .catch(error => {
        alert('Ошибка при получении значения!');
        console.error(error);
    });
}

// ========================================
// РАБОТА СО СПИСКОМ
// ========================================

// Функция для добавления элемента в список
function addToList() {
    // Получаем текст из поля ввода
    const item = document.getElementById('itemInput').value;
    
    // Проверяем, что текст введен
    if (item === '') {
        alert('Пожалуйста, введите элемент списка!');
        return;
    }
    
    // Отправляем элемент на сервер
    fetch('/Lab1/collections/items', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ item: item })
    })
    .then(response => response.json())
    .then(data => {
        // Показываем сообщение об успехе
        const message = document.getElementById('message');
        message.textContent = 'Элемент добавлен! Всего: ' + data.totalItems;
        message.className = 'success';
        
        // Очищаем поле ввода
        document.getElementById('itemInput').value = '';
        
        // Обновляем список
        showList();
        
        // Через 3 секунды прячем сообщение
        setTimeout(() => {
            message.style.display = 'none';
        }, 3000);
    })
    .catch(error => {
        alert('Ошибка при добавлении элемента!');
        console.error(error);
    });
}

// Функция для показа всего списка
function showList() {
    // Запрашиваем список с сервера
    fetch('/Lab1/collections/items')
    .then(response => response.json())
    .then(data => {
        const container = document.getElementById('listContainer');
        
        // Если список пустой
        if (data.items.length === 0) {
            container.innerHTML = '<p>Список пуст</p>';
        } else {
            // Создаем список элементов
            let html = '';
            for (let i = 0; i < data.items.length; i++) {
                html += '<p>' + (i + 1) + '. ' + data.items[i] + '</p>';
            }
            container.innerHTML = html;
        }
        
        // Обновляем счетчик элементов
        document.getElementById('itemCount').textContent = data.count;
    })
    .catch(error => {
        alert('Ошибка при получении списка!');
        console.error(error);
    });
}

// Функция для очистки всего списка
function clearList() {
    // Спрашиваем подтверждение
    if (!confirm('Вы уверены, что хотите очистить весь список?')) {
        return;
    }
    
    // Отправляем запрос на очистку
    fetch('/Lab1/collections/items', {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then(data => {
        // Показываем сообщение
        const message = document.getElementById('message');
        message.textContent = 'Список очищен!';
        message.className = 'success';
        
        // Обновляем список
        showList();
        
        // Через 3 секунды прячем сообщение
        setTimeout(() => {
            message.style.display = 'none';
        }, 3000);
    })
    .catch(error => {
        alert('Ошибка при очистке списка!');
        console.error(error);
    });
}

// ========================================
// ЗАГРУЗКА ДАННЫХ ПРИ ОТКРЫТИИ СТРАНИЦЫ
// ========================================

// Когда страница загрузится, показываем все сохраненные данные
window.onload = function() {
    showNumber();
    showText();
    showBoolean();
    showList();
};
