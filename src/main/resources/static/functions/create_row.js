import {getUser} from "./get_user_by_id.js";

export function row(user) {
    console.warn('Begin "row()" function')
    console.log('Create row for user ' + user.firstName);
    const tr = document.createElement("tr");
    tr.setAttribute("data-rowid", user.id);

    const idTd = document.createElement("td");
    idTd.append(user.id);
    tr.append(idTd);

    const nameTd = document.createElement("td");
    nameTd.append(user.firstName);
    tr.append(nameTd);

    const lastNameTd = document.createElement("td");
    lastNameTd.append(user.lastName);
    tr.append(lastNameTd);

    const ageTd = document.createElement("td");
    ageTd.append(user.age);
    tr.append(ageTd);

    const emailTd = document.createElement("td");
    emailTd.append(user.email);
    tr.append(emailTd);

    const rolesTd = document.createElement("td");
    user.roles.forEach(role => {rolesTd.append(role.role + ' ');})
    tr.append(rolesTd);

    //Кнопки управления--------------------------------------------------
    const editTd = document.createElement("td");

    const editButton = document.createElement("button");

    editButton.setAttribute("type", "button");
    editButton.setAttribute("class", "btn btn-info");
    editButton.setAttribute("data-bs-toggle", "modal");
    editButton.setAttribute("data-bs-target", "#userEdit");
    editButton.setAttribute("data-index", user.id);
    editButton.addEventListener("click", e => {
        e.preventDefault();
        getUser(user.id);
    })
    editButton.append("Изменить");
    editTd.append(editButton);
    tr.appendChild(editTd);

    const deleteTd = document.createElement("td");

    const deleteButton = document.createElement("button");
    deleteButton.setAttribute("type", "button");
    deleteButton.setAttribute("class", "btn btn-danger");
    deleteButton.setAttribute("data-bs-toggle", "modal");
    deleteButton.setAttribute("data-bs-target", "#deleteEdit");
    deleteButton.setAttribute("data-index", user.id);
    deleteButton.addEventListener("click", e => {
        e.preventDefault();
        getUser(user.id, "Del");
    })

    deleteButton.append("Удалить");
    deleteTd.append(deleteButton);
    // ---------------------------------------------------------------------
    tr.appendChild(deleteTd);

    console.warn('End "row()" function');

    return tr;
}