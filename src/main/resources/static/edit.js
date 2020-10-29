async function edit() {
    var data = {
        id: $("#id", "#exampleModalLong").val(),
        email: $("#email", "#exampleModalLong").val(),
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
    var name_td_id = 'td_id_' + result.id;
    var name_td_email = 'td_email_' + result.id;
    var name_td_password = 'td_password_' + result.id;
    var name_td_age = 'td_age_' + result.id;
    var name_td_roles = 'td_roles_' + result.id;

    console.log(name_td_roles)

    $('#'+ name_td_id).html('');
    $('#'+ name_td_id).html('<p>' + result.id + '</p>');

    $('#'+ name_td_email).html('');
    $('#'+ name_td_email).html('<p>' + result.email + '</p>');

    $('#'+ name_td_age).html('');
    $('#'+ name_td_age).html('<p>' + result.age + '</p>');

    $('#'+ name_td_password).html('');
    $('#'+ name_td_password).html('<p>' + result.password + '</p>');

    $('#'+ name_td_roles).html('');
    $('#'+ name_td_roles).html('<p>' + result.roles[0].role + '</p>');

}