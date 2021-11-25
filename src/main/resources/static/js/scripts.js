function passwordVisibility(){
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

function addRowToTable(elemId) {
    var totalRowCount = elem.rows.length; // 5
    var row = table.insertRow(totalRowCount);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    cell1.innerHTML = " ";
    cell2.innerHTML = " ";
}
function fake(){
    return 2;
}

