const deleteForm = document.forms.namedItem("deleteForm");
deleteForm.addEventListener("submit", remove);

function remove(e) {
    e.preventDefault();

    const closeButton = document.getElementById("closeDelete")
    let id = deleteForm.elements.namedItem("id").value

    fetch('/delete/' + id, {
        method: 'DELETE',
        headers: {"Content-type": "application/json; charset=UTF-8"}
    })
        .then(response => {
            getUserList();
            closeButton.click();
        })

        .catch(error => console.error('Ошибка:', error));
}