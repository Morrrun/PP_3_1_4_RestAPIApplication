import {row} from "./create_row.js";

const url = 'http://localhost:8080/users';

//функция заполнения главной таблицы юзеров
export async function listUsersTable() {

    const usersTable = document.getElementById('usersTable')
    console.warn('Start "listUsersTable" function')

    $('#usersTable').empty();
    // отправляет запрос и получаем ответ
    const response = await fetch(url, {
        method: "GET",
        headers: {"Accept": "application/json"}
    });
    // если запрос прошел нормально
    if (response.ok === true) {
        // получаем данные
        const userDTO = await response.json();
        let rows = usersTable;
        userDTO.forEach(user => {
            // добавляем полученные элементы в таблицу
            rows.append(row(user));
        });
    }
}