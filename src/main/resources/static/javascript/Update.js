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
            id: editForm.elements.namedItem("idUpd").value,
            name: editForm.elements.namedItem("nameUpd").value,
            password: editForm.elements.namedItem("passwordUpd").value,
            age: editForm.elements.namedItem("ageUpd").value,
            lastName: editForm.elements.namedItem("lastNameUpd").value,
            email: editForm.elements.namedItem("emailUpd").value,
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
