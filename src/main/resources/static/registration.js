import {createUser} from "./functions/create_user.js";


document.forms["createNewUser"].addEventListener("submit", e => {
    e.preventDefault();
    let form = document.forms["createNewUser"];
    let role = "nall";
    createUser(form, role);
    window.location.href = '/';
});
