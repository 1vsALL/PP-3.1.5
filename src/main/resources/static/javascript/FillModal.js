function getEditModal(id) {
    let form = document.forms.namedItem("editForm");
    fillModal(id, form)
}

function getDeleteModal(id) {
    let form = document.forms.namedItem("deleteForm");
    fillModal(id, form)
}

function fillModal(id, form) {
    fetch('/user/' + id)
        .then(response => response.json())
        .then(user => {
            form.elements.namedItem("id").value = user.id;
            form.elements.namedItem("firstName").value = user.name;
            form.elements.namedItem("lastName").value = user.lastName;
            form.elements.namedItem("age").value = user.ag;
            form.elements.namedItem("email").value = user.email;
            if (form.elements.namedItem("password") != null) {
                form.elements.namedItem("password").value = "";
            }
        })
        .catch(error => console.error('Ошибка:', error));
}



