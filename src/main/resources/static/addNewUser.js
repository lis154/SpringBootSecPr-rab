async function addNewUser() {
    var data = {
        id: $("#id", "#exampleModalLong").val(),
        name: $("#name", "#exampleModalLong").val(),
        password: $("#password", "#exampleModalLong").val(),
        age: $("#age", "#exampleModalLong").val(),
        roles: [$("#role", "#exampleModalLong").val()],
    };
    let response = await fetch('/admin/edit/json', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
    });
    let result = await response.json();
}