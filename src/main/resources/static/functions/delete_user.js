
const url = "http://localhost:8080/user/"
export async function deleteUser(id) {
    const response = await fetch(url + id, {
        method: "DELETE",
        headers: { "Accept": "application/json" }
    });
    if (response.ok === true) {
        document.querySelector("tr[data-rowid='" + id + "']").remove();
        $('#closeButtonDelete').click();
    }
}