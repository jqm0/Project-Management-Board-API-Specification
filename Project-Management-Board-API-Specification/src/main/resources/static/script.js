
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
    const selectElement = document.getElementById('anotherSelectID');
    const selectElement2 = document.getElementById('anotherSelectID2');
    // Clear existing content in the containers and the select element
    todoContainer.innerHTML = '<h2>To Do</h2>';
    inProgressContainer.innerHTML = '<h2>In Progress</h2>';
    doneContainer.innerHTML = '<h2>Done</h2>';
    selectElement.innerHTML = '<option selected disabled>Select ID From Here</option>';
    selectElement2.innerHTML = '<option selected disabled>Select ID From Here</option>';
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
      const option2 = document.createElement('option');
      option2.value = card.id;
      option2.text = `Card ID: ${card.id}`;
      anotherSelectID.appendChild(option2);

      const option3 = document.createElement('option');
      option3.value = card.id;
      option3.text = `Card ID: ${card.id}`;
      anotherSelectID2.appendChild(option3);
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
async function handleDelete() {
  const cardIdDropdown = document.getElementById('cardIdDropdown');
  const selectedCardId = anotherSelectID2.value;

  if (selectedCardId) {
    await deleteCard(selectedCardId);
    // After deleting the card, you may want to update the UI to remove the deleted card from the display
    // You can do this by calling the displayCardsBySection function again to refresh the cards on the page.
    displayCardsBySection();
  } else {
    alert('Please select a card ID to delete.');
  }
}
async function deleteCard(cardId) {
  const boardId = 2; // Assuming the card should be deleted from the board with ID 2
  try {
    const requestOptions = {
      method: 'DELETE',
      redirect: 'follow'
    };

    const response = await fetch(`http://localhost:8080/api/boards/${boardId}/cards/${cardId}`, requestOptions);
    if (!response.ok) {
      throw new Error(`Failed to delete card. Status: ${response.status} ${response.statusText}`);
    }

    const result = await response.text();
    console.log(result); // Log the response text

    // After successfully deleting the card, you may want to update the UI to remove the deleted card from the display
    // You can do this by removing the card element from the corresponding container based on its ID

  } catch (error) {
    console.error('Error deleting card:', error);
    alert('An error occurred while deleting the card.');
  }
}


    // Call the function to fetch and display cards when the page loads
    displayCardsBySection();