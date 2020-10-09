async function deleteUser() {
    var data = {
        id: $("#idD", "#ModalDelete").val(),
        name: $("#nameD", "#ModalDelete").val(),
        password: $("#passwordD", "#ModalDelete").val(),
        age: $("#ageD", "#ModalDelete").val(),
        roles: [$("#roleD", "#ModalDelete").val()],
    };

    let response = await fetch('/admin/delete/json', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
    });
    let result = await response.text();
    console.log(result);
    allUsers();
}