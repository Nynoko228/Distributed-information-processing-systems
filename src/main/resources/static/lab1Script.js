// Адрес нашего API для лабораторной работы №1
const LABS = '/lab1';

// Переменная для хранения текущего типа коллекции при работе с индексами
let currentCollectionType = '';

// ========================================
// РАБОТА С ПРИМИТИВАМИ
// ========================================

function saveNumber() {
    const number = document.getElementById('numberInput').value;
    if (number === '') {
        alert('Пожалуйста, введите число!');
        return;
    }
    fetch(LABS + '/primitives/number', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ value: parseInt(number) })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('numberResult').classList.add('hidden');
        document.getElementById('numberInput').value = '';
    })
    .catch(error => alert('Ошибка при сохранении числа!'));
}

function showNumber() {
    fetch(LABS + '/primitives/number')
    .then(response => response.json())
    .then(data => {
        const resultElement = document.getElementById('numberResult');
        resultElement.classList.remove('hidden');
        document.getElementById('numberValue').textContent = data.value !== null ? data.value : '-';
    })
    .catch(error => alert('Ошибка при получении числа!'));
}

function saveText() {
    const text = document.getElementById('textInput').value;
    if (text === '') {
        alert('Пожалуйста, введите текст!');
        return;
    }
    fetch(LABS + '/primitives/string', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ value: text })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('textResult').classList.add('hidden');
        document.getElementById('textInput').value = '';
    })
    .catch(error => alert('Ошибка при сохранении текста!'));
}

function showText() {
    fetch(LABS + '/primitives/string')
    .then(response => response.json())
    .then(data => {
        const resultElement = document.getElementById('textResult');
        resultElement.classList.remove('hidden');
        document.getElementById('textValue').textContent = data.value !== null ? data.value : '-';
    })
    .catch(error => alert('Ошибка при получении текста!'));
}

function saveBoolean() {
    const value = document.getElementById('booleanInput').value;
    const boolValue = (value === 'true');
    fetch(LABS + '/primitives/boolean', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ value: boolValue })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('booleanResult').classList.add('hidden');
    })
    .catch(error => alert('Ошибка при сохранении значения!'));
}

function showBoolean() {
    fetch(LABS + '/primitives/boolean')
    .then(response => response.json())
    .then(data => {
        const resultElement = document.getElementById('booleanResult');
        resultElement.classList.remove('hidden');
        document.getElementById('booleanValue').textContent = data.value !== null ? (data.value ? 'Да' : 'Нет') : '-';
    })
    .catch(error => alert('Ошибка при получении значения!'));
}

// ========================================
// СПИСОК ЧИСЕЛ (List<Int>)
// ========================================

function addNumberToList() {
    const number = document.getElementById('numberListInput').value;
    if (number === '') {
        alert('Пожалуйста, введите число!');
        return;
    }
    fetch(LABS + '/collections/numbers', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ value: parseInt(number) })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('numberListInput').value = '';
    })
    .catch(error => alert('Ошибка при добавлении числа!'));
}

function showNumberList() {
    fetch(LABS + '/collections/numbers')
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
                html += '<p>' + i + '. ' + data.numbers[i] + '</p>';
            }
            container.innerHTML = html;
        }
        document.getElementById('numberListCount').textContent = data.count;
    })
    .catch(error => alert('Ошибка при получении списка!'));
}

function clearNumberList() {
    if (!confirm('Вы уверены, что хотите очистить список?')) return;
    fetch(LABS + '/collections/numbers', { method: 'DELETE' })
    .then(response => response.json())
    .then(data => {
        document.getElementById('numberListContainer').innerHTML = '<p>Список пуст</p>';
        document.getElementById('numberListCount').textContent = '0';
        document.getElementById('numberListContainer').classList.add('hidden');
        document.getElementById('numberListCountWrapper').classList.add('hidden');
    })
    .catch(error => alert('Ошибка при очистке списка!'));
}

// ========================================
// СПИСОК СТРОК (List<String>)
// ========================================

function addStringToList() {
    const text = document.getElementById('stringListInput').value;
    if (text === '') {
        alert('Пожалуйста, введите текст!');
        return;
    }
    fetch(LABS + '/collections/strings', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ value: text })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('stringListInput').value = '';
    })
    .catch(error => alert('Ошибка при добавлении строки!'));
}

function showStringList() {
    fetch(LABS + '/collections/strings')
    .then(response => response.json())
    .then(data => {
        const container = document.getElementById('stringListContainer');
        const countWrapper = document.getElementById('stringListCountWrapper');
        container.classList.remove('hidden');
        countWrapper.classList.remove('hidden');
        if (data.strings.length === 0) {
            container.innerHTML = '<p>Список пуст</p>';
        } else {
            let html = '';
            for (let i = 0; i < data.strings.length; i++) {
                html += '<p>' + i + '. ' + data.strings[i] + '</p>';
            }
            container.innerHTML = html;
        }
        document.getElementById('stringListCount').textContent = data.count;
    })
    .catch(error => alert('Ошибка при получении списка!'));
}

function clearStringList() {
    if (!confirm('Вы уверены, что хотите очистить список?')) return;
    fetch(LABS + '/collections/strings', { method: 'DELETE' })
    .then(response => response.json())
    .then(data => {
        document.getElementById('stringListContainer').innerHTML = '<p>Список пуст</p>';
        document.getElementById('stringListCount').textContent = '0';
        document.getElementById('stringListContainer').classList.add('hidden');
        document.getElementById('stringListCountWrapper').classList.add('hidden');
    })
    .catch(error => alert('Ошибка при очистке списка!'));
}

// ========================================
// СПИСОК ЛОГИЧЕСКИХ ЗНАЧЕНИЙ (List<Boolean>)
// ========================================

function addBooleanToList() {
    const value = document.getElementById('booleanListInput').value;
    const boolValue = (value === 'true');
    fetch(LABS + '/collections/booleans', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ value: boolValue })
    })
    .then(response => response.json())
    .then(data => {})
    .catch(error => alert('Ошибка при добавлении значения!'));
}

function showBooleanList() {
    fetch(LABS + '/collections/booleans')
    .then(response => response.json())
    .then(data => {
        const container = document.getElementById('booleanListContainer');
        const countWrapper = document.getElementById('booleanListCountWrapper');
        container.classList.remove('hidden');
        countWrapper.classList.remove('hidden');
        if (data.booleans.length === 0) {
            container.innerHTML = '<p>Список пуст</p>';
        } else {
            let html = '';
            for (let i = 0; i < data.booleans.length; i++) {
                const displayValue = data.booleans[i] ? 'Да' : 'Нет';
                html += '<p>' + i + '. ' + displayValue + '</p>';
            }
            container.innerHTML = html;
        }
        document.getElementById('booleanListCount').textContent = data.count;
    })
    .catch(error => alert('Ошибка при получении списка!'));
}

function clearBooleanList() {
    if (!confirm('Вы уверены, что хотите очистить список?')) return;
    fetch(LABS + '/collections/booleans', { method: 'DELETE' })
    .then(response => response.json())
    .then(data => {
        document.getElementById('booleanListContainer').innerHTML = '<p>Список пуст</p>';
        document.getElementById('booleanListCount').textContent = '0';
        document.getElementById('booleanListContainer').classList.add('hidden');
        document.getElementById('booleanListCountWrapper').classList.add('hidden');
    })
    .catch(error => alert('Ошибка при очистке списка!'));
}

// ========================================
// РАБОТА С ИНДЕКСАМИ
// ========================================

function showGetByIndexModal(type) {
    currentCollectionType = type;
    document.getElementById('getIndexModal').style.display = 'block';
    document.getElementById('getIndexInput').value = '';
    document.getElementById('getIndexResult').textContent = '';
}

function closeGetIndexModal() {
    document.getElementById('getIndexModal').style.display = 'none';
}

function getElementByIndex() {
    const index = parseInt(document.getElementById('getIndexInput').value);
    if (isNaN(index) || index < 0) {
        alert('Пожалуйста, введите корректный индекс!');
        return;
    }
    
    const url = LABS + '/collections/' + (currentCollectionType === 'number' ? 'numbers' : 
                      currentCollectionType === 'string' ? 'strings' : 'booleans') + '/index/' + index;
    
    fetch(url)
    .then(response => response.json())
    .then(data => {
        const resultElement = document.getElementById('getIndexResult');
        if (data.success) {
            resultElement.textContent = 'Элемент [' + index + ']: ' + data.value;
            resultElement.style.color = '#4CAF50';
        } else {
            resultElement.textContent = data.message;
            resultElement.style.color = '#f44336';
        }
    })
    .catch(error => {
        alert('Ошибка при получении элемента!');
    });
}

function showAddByIndexModal(type) {
    currentCollectionType = type;
    document.getElementById('addIndexModal').style.display = 'block';
    document.getElementById('addIndexInput').value = '';
    document.getElementById('addIndexValue').value = '';
    document.getElementById('addIndexResult').textContent = '';
    
    // Изменяем placeholder в зависимости от типа
    const valueInput = document.getElementById('addIndexValue');
    if (type === 'number') {
        valueInput.placeholder = 'Введите число';
        valueInput.type = 'number';
    } else if (type === 'string') {
        valueInput.placeholder = 'Введите текст';
        valueInput.type = 'text';
    } else {
        valueInput.placeholder = 'Введите true или false';
        valueInput.type = 'text';
    }
}

function closeAddIndexModal() {
    document.getElementById('addIndexModal').style.display = 'none';
}

function addElementByIndex() {
    const index = parseInt(document.getElementById('addIndexInput').value);
    const value = document.getElementById('addIndexValue').value;
    
    if (isNaN(index) || index < 0) {
        alert('Пожалуйста, введите корректный индекс!');
        return;
    }
    
    if (value === '') {
        alert('Пожалуйста, введите значение!');
        return;
    }
    
    const url = LABS + '/collections/' + (currentCollectionType === 'number' ? 'numbers' : 
                      currentCollectionType === 'string' ? 'strings' : 'booleans') + '/index';
    
    fetch(url, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ index: index, value: value })
    })
    .then(response => response.json())
    .then(data => {
        const resultElement = document.getElementById('addIndexResult');
        resultElement.textContent = data.message;
        resultElement.style.color = data.message.includes('Ошибка') ? '#f44336' : '#4CAF50';
    })
    .catch(error => {
        alert('Ошибка при добавлении элемента!');
    });
}

// Закрытие модальных окон при клике вне их
window.onclick = function(event) {
    if (event.target.id === 'getIndexModal') {
        closeGetIndexModal();
    } else if (event.target.id === 'addIndexModal') {
        closeAddIndexModal();
    }
}