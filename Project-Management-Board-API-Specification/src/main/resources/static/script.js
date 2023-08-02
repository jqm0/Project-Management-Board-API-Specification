// Function to set the title of the board
function setTitle() {
    const titleInput = document.getElementById('titleInput');
    const title = titleInput.value.trim(); // Trim any leading/trailing white spaces
    const displayElement = document.getElementById('displayTitle');

    // Check if the title is not empty
    if (title !== '') {
        displayElement.textContent = title;
    } else {
        displayElement.textContent = "Project Management Board"; // If the title is empty, show the default value
    }

    titleInput.value = ''; // Clear the input box after processing
}

// Function to populate the "Select ID" dropdowns
function populateUpdateDropdown() {
    // Get the table and dropdown elements
    const table = document.querySelector(".project-table");
    const selectIDDropdown = document.getElementById("anotherSelectID");

    // Clear existing options in the dropdown
    selectIDDropdown.innerHTML = '<option selected disabled>Select ID From Here</option>';

    // Loop through all cards and add their IDs to the dropdown
    const rowCount = table.rows.length;
    for (let i = 1; i < rowCount; i++) {
        const cardID = table.rows[i].querySelector("b").innerText;
        const option = document.createElement("option");
        option.text = cardID;
        selectIDDropdown.add(option);
    }
}

// Function to execute on page load
document.addEventListener('DOMContentLoaded', () => {
    populateUpdateDropdown();
});
