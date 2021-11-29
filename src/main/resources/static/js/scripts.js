function passwordVisibility() {
    const password_input = document.querySelector("#password")
    const password_toggle = document.querySelector("#password-toggle")

    if (password_input.type === "password") {
        password_input.type = "text"
        password_toggle.className = "bi-eye"
    } else{
        password_input.type = "password";
        password_toggle.className = "bi-eye-slash"
    }
}

function addTeamInvitation() {
    const invitations_div = document.querySelector("#team-members");
    const invitation_button = document.querySelector("#team-members-button");
    const invitation_input = document.createElement("input");

    invitation_input.name = "invitations";
    invitation_input.className = "w-12";
    invitation_input.setAttribute("list", "accounts");
    invitation_input.type = "email";
    invitation_input.setAttribute("list","accounts");
    invitation_input.autocomplete = "off";

    invitations_div.insertBefore(invitation_input, null);
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

function addRowToTable(elemId) {
    var totalRowCount = elem.rows.length; // 5
    var row = table.insertRow(totalRowCount);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    cell1.innerHTML = " ";
    cell2.innerHTML = " ";
}
