function passwordVisibility() {
    const password_input = document.querySelector("#password");
    const password_repeat = document.querySelector("#repeat-password");
    const password_toggle = document.querySelector("#password-toggle");

    if (password_input.type === "password") {
        password_input.type = "text";
        password_toggle.children.item(0).className = "bi-eye-slash";
        if (password_repeat) { password_repeat.type = "text"; }
    } else {
        password_input.type = "password";
        password_toggle.children.item(0).className = "bi-eye"
        if (password_repeat) { password_repeat.type = "password"; }
    }
}

function addTeamInvitation() {
    const invitations_div = document.querySelector("#team-members");
    const invitation_button = document.querySelector("#team-members-button");
    const input_wrapper = document.createElement("div");
    input_wrapper.className = "form-control";


    const invitation_input = document.createElement("input");
    invitation_input.name = "invitations";
    invitation_input.className = "w-12";
    invitation_input.type = "email";
    invitation_input.setAttribute("list","accounts");
    invitation_input.autocomplete = "off";

    input_wrapper.appendChild(invitation_input)
    invitations_div.insertBefore(input_wrapper, null);
}

function addField(){
    const add_field_div = document.querySelector("#add-field");
    const add_field_btn = document.querySelector("#add-field-btn");
    const field_input = document.createElement("input");

    field_input.name = "task"
    field_input.type = "text";
    field_input.placeholder = "Set a task";


    add_field_div.insertBefore(field_input,null);
}

function thankYou(){
    const name = document.getElementById("name").value
    const email = document.getElementById("email").value
    const message = document.getElementById("message").value

    if (name && email && message !== ""){
        alert("Thank you for your message!\nWe aim to answer as soon as possible!")
    }
}


function addRowToTable(elemId) {
    var totalRowCount = elem.rows.length; // 5
    var row = table.insertRow(totalRowCount);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    cell1.innerHTML = " ";
    cell2.innerHTML = " ";
}