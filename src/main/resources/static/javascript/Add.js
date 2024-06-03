const addForm = document.forms.namedItem("addForm");


addForm.addEventListener("submit", add);

function add(e) {
    e.preventDefault();
    const rolesOption = addForm.elements.namedItem("roles");
    let selectedRoles = [];
    for (const rolesSelect of rolesOption) {
        if (rolesSelect.selected) {
            selectedRoles.push({
                id: rolesSelect.value,
                role: rolesSelect.text
            });
        }
    }
    fetch('/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            name: addForm.elements.namedItem("name").value,
            password: addForm.elements.namedItem("password").value,
            age: addForm.elements.namedItem("age").value,
            lastName: addForm.elements.namedItem("lastName").value,
            email: addForm.elements.namedItem("email").value,
            roles: selectedRoles
        }),
    })
        .then(response => {
            getUserList();
            document.getElementById("tabTableUser").click()
            for (const formElem of addForm) {
                formElem.value = "";
            }
            for (const rolesSelect of rolesOption) {
                rolesSelect.selected = false
            }
        })
        .catch((error) => {
            console.error('Ошибка:', error);
        });
}
