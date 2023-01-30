import {listUsersTable} from "./functions/users_table.js";
import {getRoles} from "./functions/get_roles.js";
import {updateUser} from "./functions/update_user.js";
import {deleteUser} from "./functions/delete_user.js";
import {createUser} from "./functions/create_user.js";

window.onload = () => {
    listUsersTable();
    getRoles("New");
}

$(document).ready(() => {
    document.forms["userEditForm"].addEventListener("submit", e => {
        e.preventDefault();
        let form = document.forms["userEditForm"];
        let id = form.elements.id.value;
        let role =  form.elements.role.value;
        updateUser(id, form, role);
    });

    document.forms["userDeleteForm"].addEventListener("submit", e => {
        e.preventDefault();
        let form = document.forms["userDeleteForm"];
        let id = form.elements.id.value;
        deleteUser(id);
    });

    document.forms["createNewUser"].addEventListener("submit", e => {
        e.preventDefault();
        let form = document.forms["createNewUser"];
        let role =  form.elements.role.value;
        createUser(form, role);
    });
})