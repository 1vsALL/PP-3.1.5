const editForm = document.forms.namedItem("editForm");


editForm.addEventListener("submit", update);

function update(e) {
    e.preventDefault();
    const closeButton = document.getElementById("closeEdit")
    const rolesOption = editForm.elements.namedItem("roles");
    let selectedRoles = [];
    for (const rolesSelect of rolesOption) {
        if (rolesSelect.selected) {
            selectedRoles.push({
                id: rolesSelect.value,
                role: rolesSelect.text
            });
        }
    }
    fetch('/update', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: editForm.elements.namedItem("id").value,
            name: editForm.elements.namedItem("name").value,
            password: editForm.elements.namedItem("password").value,
            age: editForm.elements.namedItem("age").value,
            lastName: editForm.elements.namedItem("lastName").value,
            email: editForm.elements.namedItem("email").value,
            roles: selectedRoles
        }),
    })
        .then(response => {
            getUserList();
            closeButton.click();
            for (const rolesSelect of rolesOption) {
                rolesSelect.selected = false
            }
        })
        .catch((error) => {
            console.error('Ошибка:', error);
        });
}
