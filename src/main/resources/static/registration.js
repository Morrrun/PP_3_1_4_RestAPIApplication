import {registrationUser} from "./functions/registration_user.js";


document.forms["createNewUser"].addEventListener("submit", e => {
    e.preventDefault();
    let form = document.forms["createNewUser"];
    registrationUser(form);
});
