
const url = "http://localhost:8080/user/"
export async function deleteUser(id) {
    console.warn('Begin "deleteUser()" function');
    const response = await fetch(url + id, {
        method: "DELETE",
        headers: { "Accept": "application/json" }
    });
    if (response.ok === true) {
        document.querySelector("tr[data-rowid='" + id + "']").remove();
        $('#closeButtonDelete').click();
    }
    console.warn('End "deleteUser()" function');
}