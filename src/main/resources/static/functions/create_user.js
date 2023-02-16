import {listUsersTable} from "./users_table.js";

const url = "http://localhost:8080/addUser"

export async function createUser(modalForm) {
    console.warn('Begin "editUser()" function');
    let role = $('#roleNew').val();

    const arrRoles = [];

    role.forEach( el => {
        let str = el.split('-');

        arrRoles.push({
            id: parseInt(str[0]),
            role: str[1]
        });
    })


    const response = await fetch(url, {
        method: "POST",
        headers: {"Accept": "application/json", "Content-Type": "application/json"},
        body: JSON.stringify({
            id: 0,
            firstName: modalForm.firstName.value,
            lastName: modalForm.lastName.value,
            age: parseInt(modalForm.age.value),
            email: modalForm.email.value,
            password: modalForm.password.value,
            roles: arrRoles
        })
    }).then(response => {
        if (response.ok) {
            modalForm.reset();
            cleanForm();
            listUsersTable();
        } else {
            cleanForm();
            const err = response.json();
            err.then(data => {
                let arr = data['message'].split(';');
                arr.forEach(el => {
                    if (el.includes('firstName')) {
                        console.log(el)

                        let block = document.getElementById('firstNameErrorNew');
                        let str = el.split(' - ');
                        block.innerText = str[1];
                        block.style.display = 'block';
                    } else if (el.includes('lastName')) {
                        let block1 = document.getElementById('lastNameErrorNew');
                        let str1 = el.split(' - ');
                        block1.innerText = str1[1];
                        block1.style.display = 'block';
                    } else if (el.includes('age')) {
                        let block2 = document.getElementById('ageErrorNew');
                        let str2 = el.split(' - ');
                        block2.innerText = str2[1];
                        block2.style.display = 'block';
                    } else if (el.includes('email')) {
                        let block3 = document.getElementById('emailErrorNew');
                        let str3 = el.split(' - ');
                        block3.innerText = str3[1];
                        block3.style.display = 'block';
                    } else if (el.includes('password')) {
                        let block4 = document.getElementById('passwordErrorNew');
                        let str4 = el.split(' - ');
                        block4.innerText = str4[1];
                        block4.style.display = 'block';
                    }
                })

            })
        }
    });


    console.warn('End "editUser()" function');
}

function cleanForm() {
    document.getElementById('firstNameErrorNew').style.display = 'none';
    document.getElementById('lastNameErrorNew').style.display = 'none';
    document.getElementById('ageErrorNew').style.display = 'none';
    document.getElementById('emailErrorNew').style.display = 'none';
    document.getElementById('passwordErrorNew').style.display = 'none';
}
