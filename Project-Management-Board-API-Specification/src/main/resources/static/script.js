
async function displayCardsBySection() {
  const boardId = 2; // Assuming the card should be added to the board with ID 2
  try {
    // Fetch the list of cards for the board using the provided fetch code
    const requestOptions = {
      method: 'GET',
      redirect: 'follow',
    };

    const response = await fetch(`http://localhost:8080/api/boards/${boardId}/cards`, requestOptions);
    if (!response.ok) {
      throw new Error(`Failed to fetch cards. Status: ${response.status} ${response.statusText}`);
    }

    // Parse the response body as JSON
    const data = await response.json();
    console.log("cards:", data); // Add this line to inspect the value of `cards`
    const cards = data.cards; // Extract the cards array from the response

    // Get the containers for each section
    const todoContainer = document.getElementById('todo');
    const inProgressContainer = document.getElementById('inprogress');
    const doneContainer = document.getElementById('done');

    // Get the select element for the card IDs
    const selectElement = document.getElementById('anotherSelectID2');

    // Clear existing content in the containers and the select element
    todoContainer.innerHTML = '<h2>To Do</h2>';
    inProgressContainer.innerHTML = '<h2>In Progress</h2>';
    doneContainer.innerHTML = '<h2>Done</h2>';
    selectElement.innerHTML = '<option selected disabled>Select ID From Here</option>';

    // Loop through the cards and create HTML elements to display them
    cards.forEach((card, index) => {
      const cardElement = document.createElement('div');
      cardElement.classList.add('card'); // Add the 'card' class for styling
      cardElement.innerHTML = `
        <h3>Card ID: ${index + 1}</h3>
        <p>Title: ${card.title}</p>
        <p>Description: ${card.description}</p>
      `;

      // Add the card to the corresponding container based on the section
      if (card.section === '1' | card.section === "TODO") {
              todoContainer.appendChild(cardElement);
            } else if (card.section === '2' || card.section === "In Progress") {
              inProgressContainer.appendChild(cardElement);
            } else if (card.section === '3'  || card.section === "Done") {
              doneContainer.appendChild(cardElement);
       }

      // Populate the select element with card IDs
      const optionElement = document.createElement('option');
      optionElement.value = card.id; // Assuming 'id' is the property that holds the card ID
      optionElement.textContent = card.id;
      selectElement.appendChild(optionElement);
    });
  } catch (error) {
    console.error('Error fetching cards:', error);
    alert('An error occurred while fetching cards.');
  }
}

async function createNewCard() {
  const boardId = 2; // Assuming the card should be added to the board with ID 2
  const titleInput = document.getElementById('createCardTitleInput');
  const descInput = document.getElementById('createCardDescInput');
  const sectionSelect = document.getElementById('createCategorySelect');

  // Get the values from the input fields
  const cardData = {
    title: titleInput.value,
    description: descInput.value,
    section: sectionSelect.value,
  };

  try {
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(cardData),
      redirect: 'follow',
    };

    const response = await fetch(`http://localhost:8080/api/boards/${boardId}/cards`, requestOptions);
    if (!response.ok) {
      throw new Error(`Failed to create card. Status: ${response.status} ${response.statusText}`);
    }

    const createdCard = await response.json();
    console.log('Created Card:', createdCard);
    alert('New card created successfully!');
    // You can now call the displayCardsBySection() function to refresh the displayed cards.
    displayCardsBySection();
  } catch (error) {
    console.error('Error creating card:', error);
    alert('An error occurred while creating the card.');
  }
}

    // Call the function to fetch and display cards when the page loads
    displayCardsBySection();