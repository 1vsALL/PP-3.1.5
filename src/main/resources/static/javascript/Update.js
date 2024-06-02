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
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: editForm.elements.namedItem("id").value,
            username: editForm.elements.namedItem("username").value,
            password: editForm.elements.namedItem("password").value,
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
