// async function allUsers() {
//     let response = await fetch('/admin/json', {
//         method: 'GET'
//     });
//     let result = await response.json();
//     for (i = 0; i < result.length; i++) {
//         alert(result[i]);
//     }
// }
async function allUsers() {
    let response = await fetch('/admin/json', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },

    });
    let json = await response.json()

    var htmlCode = '';
    for (i = 0; i < json.length; i++) {
         htmlCode += "<tr id=\"div_" + json[i].id + "\">\n" +
            "                                <td id=\"td_id_" + json[i].id + "\"><p>" + json[i].id + "</p></td>\n" +
            "                                <td id=\"td_name_" + json[i].id + "\"><p>" + json[i].name + "</p></td>\n" +
            "                                <td id=\"td_password_'" + json[i].id + "\"><p>" + json[i].password + "</p></td>\n" +
            "                                <td id=\"td_age_" + json[i].id + "\"><p>" + json[i].age + "</p></td>\n" +
            "                                <td id=\"td_roles_" + json[i].id + "\"><p>" + json[i].roles[0].role + "</p></td>\n" +
            "                                <td><a data-toggle=\"modal\" data-name=\"" + json[i].name + "\" data-age=\"" + json[i].age + "\" data-password=\"" + json[i].password + "\" data-id=\"" + json[i].id + "\" title=\"Add this item\" class=\"open-AddBookDialog btn btn-primary\" href=\"#exampleModalLong\">Edit</a></td>\n" +
            "                                <td><a data-toggle=\"modal\" data-name=\"" + json[i].name + "\" data-age=\"" + json[i].age + "\" data-password=\"" + json[i].password + "\" data-id=\"" + json[i].id + "\" title=\"Add this item\" class=\"open-DeleteDel btn btn-danger\" href=\"#ModalDelete\">Delete</a></td>\n</tr>" +
            "                                "
        console.log(htmlCode)
    }
    //$('#prob_1').html("");
    $('#prob_1').html(htmlCode);

   // allUsers();


}