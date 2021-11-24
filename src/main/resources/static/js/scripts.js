function passwordVisibility(){

    var pass_element = document.getElementById("password")

    if (pass_element.type === "password"){
        pass_element.type = "text"
    }
    else{
        pass_element.type = "password";
    }
}