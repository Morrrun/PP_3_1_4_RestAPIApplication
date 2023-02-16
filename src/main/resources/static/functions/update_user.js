import {listUsersTable} from "./users_table.js";

const url = "http://localhost:8080/user"

export async function updateUser(modalForm) {
    console.log('Begin function editUser...');
    let role = $('#role').val();
    const arrRoles = [];

    role.forEach( el => {
        let str = el.split('-');

        arrRoles.push({
            id: parseInt(str[0]),
            role: str[1]
        });
    })

    const response = await fetch(url, {
        method: "PATCH",
        headers: {"Accept": "application/json", "Content-Type": "application/json"},
        body: JSON.stringify({
            id: parseInt(modalForm.id.value),
            firstName: modalForm.firstName.value,
            lastName: modalForm.lastName.value,
            age: parseInt(modalForm.age.value),
            email: modalForm.email.value,
            password: modalForm.password.value,
            roles: arrRoles
        })
    }).then(response => {
        if (response.ok) {
            $('#closeButtonUpdate').click();
            modalForm.reset();
            cleanForm();
            listUsersTable();
        } else {
            const err = response.json();
            err.then(data => {
                let arr = data['message'].split(';');
                arr.forEach(el => {
                    console.log(el)

                    if (el.includes('firstName')) {
                        let block = document.getElementById('firstNameError');
                        let str = el.split(' - ');
                        block.innerText = str[1];
                        block.style.display = 'block';
                    } else if (el.includes('lastName')) {
                        let block1 = document.getElementById('lastNameError');
                        let str1 = el.split(' - ');
                        block1.innerText = str1[1];
                        block1.style.display = 'block';
                    } else if (el.includes('age')) {
                        let block2 = document.getElementById('ageError');
                        let str2 = el.split(' - ');
                        block2.innerText = str2[1];
                        block2.style.display = 'block';
                    } else if (el.includes('email')) {
                        let block3 = document.getElementById('emailError');
                        let str3 = el.split(' - ');
                        block3.innerText = str3[1];
                        block3.style.display = 'block';
                    } else if (el.includes('password')) {
                        let block4 = document.getElementById('passwordError');
                        let str4 = el.split(' - ');
                        block4.innerText = str4[1];
                        block4.style.display = 'block';
                    }
                })

            })
            throw new Error('Остановочка!');
        }
    });

}

function cleanForm() {
    document.getElementById('firstNameError').style.display = 'none'
    document.getElementById('lastNameError').style.display = 'none'
    document.getElementById('ageError').style.display = 'none'
    document.getElementById('emailError').style.display = 'none'
    document.getElementById('passwordError').style.display = 'none'
}