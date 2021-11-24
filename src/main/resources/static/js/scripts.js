function passwordVisibility(){

    var pass_element = document.getElementById("password")

    if (pass_element.type === "password"){
        pass_element.type = "text"
    }
    else{
        pass_element.type = "password";
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

