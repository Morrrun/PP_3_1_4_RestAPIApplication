import {getRoles} from "./get_roles.js";

const url = "http://localhost:8080/user/"

export async function getUser(id, method= null) {
    console.log('Begin function editUser...');

    const response = await fetch(url + id, {
        method: "GET",
        headers: { "Accept": "application/json" }
    });
    if (response.ok === true) {
        const userDTO = await response.json();
        let form;
        console.log(userDTO);
        if (method == null) {
            form = document.forms["userEditForm"];
        } else {
            form = document.forms["userDeleteForm"];
        }

        form.elements.id.value = userDTO.id;
        form.elements.firstName.value = userDTO.firstName;
        form.elements.lastName.value = userDTO.lastName;
        form.elements.age.value = userDTO.age;
        form.elements.email.value = userDTO.email;

        getRoles(method)

    }
}