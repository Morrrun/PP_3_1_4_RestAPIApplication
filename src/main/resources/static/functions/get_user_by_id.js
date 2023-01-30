import {getRoles} from "./get_roles.js";

const url = "http://localhost:8080/user/"

export async function getUser(id, method= null) {
    const response = await fetch(url + id, {
        method: "GET",
        headers: { "Accept": "application/json" }
    });
    if (response.ok === true) {
        const ObjectJSON = await response.json();
        let user = ObjectJSON.userDTO;
        let form;

        if (method == null) {
            form = document.forms["userEditForm"];
        } else {
            form = document.forms["userDeleteForm"];
        }

        form.elements.id.value = user.id;
        form.elements.firstName.value = user.firstName;
        form.elements.lastName.value = user.lastName;
        form.elements.age.value = user.age;
        form.elements.email.value = user.email;

        getRoles(method)



    }
}