import {listUsersTable} from "./users_table.js";

const url = "http://localhost:8080/people/"

export async function updateUser(id, modalForm, role) {
    console.log('Begin function editUser...');
    const response = await fetch(url + id, {
        method: "PATCH",
        headers: {"Accept": "application/json", "Content-Type": "application/json"},
        body: JSON.stringify({
            userDTO: {
                id: parseInt(modalForm.id.value),
                firstName: modalForm.firstName.value,
                lastName: modalForm.lastName.value,
                age: parseInt(modalForm.age.value),
                email: modalForm.email.value,
                password: modalForm.password.value
            },
            rolesDTO: [
                {role: role}
            ]
        })
    }).then(response => {
        $('#closeButtonUpdate').click();
        if(response.ok) {
            listUsersTable()
        }
    });

}