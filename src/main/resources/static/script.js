// Адрес нашего API
const API = '/api';

// ========================================
// РАБОТА С ПРИМИТИВАМИ (ЧИСЛА, ТЕКСТ, ЛОГИКА)
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
    fetch(API + '/primitives/number', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ value: parseInt(number) })
    })
    .then(response => response.json())
    .then(data => {
        // Скрываем результат после сохранения
        document.getElementById('numberResult').classList.add('hidden');
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
    fetch(API + '/primitives/number')
    .then(response => response.json())
    .then(data => {
        // Показываем результат
        const resultElement = document.getElementById('numberResult');
        resultElement.classList.remove('hidden');
        
        // Показываем число или прочерк, если число не сохранено
        if (data.value !== null) {
            document.getElementById('numberValue').textContent = data.value;
        } else {
            document.getElementById('numberValue').textContent = '-';
        }
    })
    .catch(error => {
        alert('Ошибка при получении числа!');
        console.error(error);
    });
}

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
    fetch(API + '/primitives/string', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ value: text })
    })
    .then(response => response.json())
    .then(data => {
        // Скрываем результат после сохранения
        document.getElementById('textResult').classList.add('hidden');
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
    fetch(API + '/primitives/string')
    .then(response => response.json())
    .then(data => {
        // Показываем результат
        const resultElement = document.getElementById('textResult');
        resultElement.classList.remove('hidden');
        
        // Показываем текст или прочерк, если текст не сохранен
        if (data.value !== null) {
            document.getElementById('textValue').textContent = data.value;
        } else {
            document.getElementById('textValue').textContent = '-';
        }
    })
    .catch(error => {
        alert('Ошибка при получении текста!');
        console.error(error);
    });
}

// Функция для сохранения значения истина/ложь
function saveBoolean() {
    // Получаем выбранное значение
    const value = document.getElementById('booleanInput').value;
    const boolValue = (value === 'true');
    
    // Отправляем значение на сервер
    fetch(API + '/primitives/boolean', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ value: boolValue })
    })
    .then(response => response.json())
    .then(data => {
        // Скрываем результат после сохранения
        document.getElementById('booleanResult').classList.add('hidden');
    })
    .catch(error => {
        alert('Ошибка при сохранении значения!');
        console.error(error);
    });
}

// Функция для показа сохраненного значения истина/ложь
function showBoolean() {
    // Запрашиваем значение с сервера
    fetch(API + '/primitives/boolean')
    .then(response => response.json())
    .then(data => {
        // Показываем результат
        const resultElement = document.getElementById('booleanResult');
        resultElement.classList.remove('hidden');
        
        // Показываем значение или прочерк, если значение не сохранено
        if (data.value !== null) {
            document.getElementById('booleanValue').textContent = data.value ? 'Да' : 'Нет';
        } else {
            document.getElementById('booleanValue').textContent = '-';
        }
    })
    .catch(error => {
        alert('Ошибка при получении значения!');
        console.error(error);
    });
}

// ========================================
// РАБОТА СО СПИСКОМ ЧИСЕЛ (List<Int>)
// ========================================

// Функция для добавления числа в список
function addNumberToList() {
    const number = document.getElementById('numberListInput').value;
    
    if (number === '') {
        alert('Пожалуйста, введите число!');
        return;
    }
    
    fetch(API + '/collections/numbers', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ value: parseInt(number) })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('numberListInput').value = '';
        // Не показываем уведомление
    })
    .catch(error => {
        alert('Ошибка при добавлении числа!');
        console.error(error);
    });
}

// Функция для показа списка чисел
function showNumberList() {
    fetch(API + '/collections/numbers')
    .then(response => response.json())
    .then(data => {
        const container = document.getElementById('numberListContainer');
        const countWrapper = document.getElementById('numberListCountWrapper');
        
        container.classList.remove('hidden');
        countWrapper.classList.remove('hidden');
        
        if (data.numbers.length === 0) {
            container.innerHTML = '<p>Список пуст</p>';
        } else {
            let html = '';
            for (let i = 0; i < data.numbers.length; i++) {
                html += '<p>' + (i + 1) + '. ' + data.numbers[i] + '</p>';
            }
            container.innerHTML = html;
        }
        
        document.getElementById('numberListCount').textContent = data.count;
    })
    .catch(error => {
        alert('Ошибка при получении списка!');
        console.error(error);
    });
}

// Функция для очистки списка чисел
function clearNumberList() {
    if (!confirm('Вы уверены, что хотите очистить список?')) {
        return;
    }
    
    fetch(API + '/collections/numbers', {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('numberListContainer').innerHTML = '<p>Список пуст</p>';
        document.getElementById('numberListCount').textContent = '0';
        document.getElementById('numberListContainer').classList.add('hidden');
        document.getElementById('numberListCountWrapper').classList.add('hidden');
    })
    .catch(error => {
        alert('Ошибка при очистке списка!');
        console.error(error);
    });
}

// ========================================
// РАБОТА С МНОЖЕСТВОМ СТРОК (Set<String>)
// ========================================

// Функция для добавления строки в множество
function addStringToSet() {
    const text = document.getElementById('stringSetInput').value;
    
    if (text === '') {
        alert('Пожалуйста, введите текст!');
        return;
    }
    
    fetch(API + '/collections/strings', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ value: text })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('stringSetInput').value = '';
        // Не показываем уведомление
    })
    .catch(error => {
        alert('Ошибка при добавлении строки!');
        console.error(error);
    });
}

// Функция для показа множества строк
function showStringSet() {
    fetch(API + '/collections/strings')
    .then(response => response.json())
    .then(data => {
        const container = document.getElementById('stringSetContainer');
        const countWrapper = document.getElementById('stringSetCountWrapper');
        
        container.classList.remove('hidden');
        countWrapper.classList.remove('hidden');
        
        if (data.strings.length === 0) {
            container.innerHTML = '<p>Пустое множество</p>';
        } else {
            let html = '';
            for (let i = 0; i < data.strings.length; i++) {
                html += '<p>' + (i + 1) + '. ' + data.strings[i] + '</p>';
            }
            container.innerHTML = html;
        }
        
        document.getElementById('stringSetCount').textContent = data.count;
    })
    .catch(error => {
        alert('Ошибка при получении множества!');
        console.error(error);
    });
}

// Функция для очистки множества строк
function clearStringSet() {
    if (!confirm('Вы уверены, что хотите очистить множество?')) {
        return;
    }
    
    fetch(API + '/collections/strings', {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('stringSetContainer').innerHTML = '<p>Пустое множество</p>';
        document.getElementById('stringSetCount').textContent = '0';
        document.getElementById('stringSetContainer').classList.add('hidden');
        document.getElementById('stringSetCountWrapper').classList.add('hidden');
    })
    .catch(error => {
        alert('Ошибка при очистке множества!');
        console.error(error);
    });
}

// Функция для добавления логического значения в словарь
function addBooleanToMap() {
    const value = document.getElementById('booleanMapInput').value;
    const boolValue = (value === 'true');
    
    fetch(API + '/collections/booleans', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ value: boolValue })
    })
    .then(response => response.json())
    .then(data => {
        // Не показываем уведомление
    })
    .catch(error => {
        alert('Ошибка при добавлении значения!');
        console.error(error);
    });
}

// Функция для показа словаря логических значений
function showBooleanMap() {
    fetch(API + '/collections/booleans')
    .then(response => response.json())
    .then(data => {
        const container = document.getElementById('booleanMapContainer');
        const countWrapper = document.getElementById('booleanMapCountWrapper');
        
        container.classList.remove('hidden');
        countWrapper.classList.remove('hidden');
        
        if (data.count === 0) {
            container.innerHTML = '<p>Словарь пустой</p>';
        } else {
            let html = '';
            let index = 1;
            
            // Обрабатываем словарь Map<Boolean, Int>
            if (data.booleanMap.true !== undefined) {
                html += '<p>' + index + '. Да: ' + data.booleanMap.true + ' раз(а)</p>';
                index++;
            }
            if (data.booleanMap.false !== undefined) {
                html += '<p>' + index + '. Нет: ' + data.booleanMap.false + ' раз(а)</p>';
            }
            
            container.innerHTML = html;
        }
        
        document.getElementById('booleanMapCount').textContent = data.count;
    })
    .catch(error => {
        alert('Ошибка при получении словаря!');
        console.error(error);
    });
}

// Функция для очистки слоаваря логических значений
function clearBooleanMap() {
    if (!confirm('Вы уверены, что хотите очистить словарь?')) {
        return;
    }
    
    fetch(API + '/collections/booleans', {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('booleanMapContainer').innerHTML = '<p>Словарь пустой</p>';
        document.getElementById('booleanMapCount').textContent = '0';
        document.getElementById('booleanMapContainer').classList.add('hidden');
        document.getElementById('booleanMapCountWrapper').classList.add('hidden');
    })
    .catch(error => {
        alert('Ошибка при очистке словаря!');
        console.error(error);
    });
}
