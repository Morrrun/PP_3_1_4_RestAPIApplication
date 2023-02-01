const url = 'http://localhost:8080/roles';

export async function getRoles(method = null) {
    console.warn('Begin "getRoles()" function');

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
        } else if (method === 'Del') {
            $('#selectorRolesDel').empty()
            selectorRoles = document.getElementById('selectorRoles' + method);
        } else {
            $('#selectorRolesNew').empty()
            selectorRoles = document.getElementById('selectorRoles' + method);
        }
        console.log('Roles------------------------')
        const labelRoles = document.createElement('label');
        labelRoles.setAttribute("class", "form-label fw-bold");

        selectorRoles.append(labelRoles)

        const tagSelect = document.createElement("select");
        tagSelect.setAttribute("class", "form-control");
        tagSelect.setAttribute("name", "role");

        if (method == null) {
            tagSelect.setAttribute("id", "role");
        } else {
            tagSelect.setAttribute("id", "role" + method);
        }

        if (method !== "Del") {
            tagSelect.setAttribute("required", "required");
        }
        tagSelect.setAttribute("multiple", "");
        tagSelect.setAttribute("size", "2");

        arrRole.forEach(role => {
            const tagOption = document.createElement("option");
            tagOption.setAttribute("value", role.role);
            tagOption.append(role.role);
            tagSelect.append(tagOption);
        });

        selectorRoles.appendChild(tagSelect);

        console.warn('End "getRoles()" function');

    }

}