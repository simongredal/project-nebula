// Runs when document is "ready"
document.addEventListener("DOMContentLoaded", (event) => {
    const tabs = document.querySelectorAll(".tab-group>.tab-menu>*");

    // Adds an onclick function to each tab
    for (const tab of tabs) {
        tab.addEventListener("click", switchToTab);
    }
})

function switchToTab(event) {
    const clickedTabButton = event.target;
    const tabTarget = document.querySelector(clickedTabButton.dataset.target);

    const tabButtons = event.target.parentNode.children;
    for (const tab of tabButtons) { tab.classList.remove("active"); }
    event.target.classList.add("active");

    const allTabs = tabTarget.parentNode.children;
    for (const tab of allTabs) { tab.classList.remove("active"); }
    tabTarget.classList.add("active");
}


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

function thankYou(){
    const name = document.getElementById("name").value
    const email = document.getElementById("email").value
    const message = document.getElementById("message").value

    if (name && email && message !== ""){
        alert("Thank you for your message!\nWe aim to answer as soon as possible!")
    }
}