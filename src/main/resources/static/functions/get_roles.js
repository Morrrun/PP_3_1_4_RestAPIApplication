
const url = 'http://localhost:8080/roles';
export async function getRoles(method = null) {
    console.log('Start "getRoles" function')

    // отправляет запрос и получаем ответ
    const response = await fetch(url, {
        method: "GET",
        headers: {"Accept": "application/json"}
    });
    // если запрос прошел нормально
    if (response.ok === true) {

        let arrRole = await response.json();

        let selectorRoles;

        if (method == null) {
            $('#selectorRoles').empty()
            selectorRoles = document.getElementById('selectorRoles');
        } else {
            $('#selectorRolesDel').empty()
            $('#selectorRolesNew').empty()
            selectorRoles = document.getElementById('selectorRoles' + method);
        }

        const labelRoles = document.createElement('label');
        labelRoles.setAttribute("class", "form-label fw-bold");

        selectorRoles.append(labelRoles)

        const tagSelect = document.createElement("select");
        tagSelect.setAttribute("multiple", "");
        tagSelect.setAttribute("class", "form-control");
        tagSelect.setAttribute("name", "role");
        tagSelect.setAttribute("required", "required");
        tagSelect.setAttribute("size", "2");

        arrRole.forEach(role => {
            const tagOption = document.createElement("option");
            tagOption.setAttribute("value", role.role);
            tagOption.append(role.role);
            tagSelect.append(tagOption);
        });

        selectorRoles.appendChild(tagSelect);

    }

}