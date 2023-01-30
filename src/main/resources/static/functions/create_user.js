import {listUsersTable} from "./users_table.js";

const url = "http://localhost:8080/addUser"

export async function createUser(modalForm, role) {
    console.log('Begin function editUser...');
    const response = await fetch(url, {
        method: "POST",
        headers: {"Accept": "application/json", "Content-Type": "application/json"},
        body: JSON.stringify({
            userDTO: {
                id: 0,
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
        modalForm.reset()
        if(response.ok) {
            listUsersTable()
        } else {
            console.log(response.json())
        }
    });

}