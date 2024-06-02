function getUserList() {
    const tableBody = document.getElementById("usersTBody");
    fetch('/users')
        .then(response => response.json())
        .then(users => {
            tableBody.innerHTML = '';
            for (const user of users) {
                let row = tableBody.insertRow();
                row.insertCell().innerHTML = user.id
                row.insertCell().innerHTML = user.username
                row.insertCell().innerHTML = user.password
                row.insertCell().innerHTML = user.email;
                row.insertCell().innerHTML = user.roles.map(function (role) {
                    return ' ' + role.name;
                });
                row.insertCell().innerHTML =
                    '<button class="btn btn-info" type="submit" onclick="getEditModal(' + user.id + ')" data-toggle="modal" data-target="#modalEdit">Edit</button>';
                row.insertCell().innerHTML =
                    '<button class="btn btn-danger" type="submit" onclick="getDeleteModal(' + user.id + ')" data-toggle="modal" data-target="#modalDelete">Delete</button>';
            }
        })
        .catch(error => console.error('Ошибка:', error));
}