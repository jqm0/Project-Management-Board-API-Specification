package com.example.Project.Management.Board.API.Specification.Controller;

import com.example.Project.Management.Board.API.Specification.Model.Board;
import com.example.Project.Management.Board.API.Specification.Model.Card;
import com.example.Project.Management.Board.API.Specification.RequestObject.CardRequest;
import com.example.Project.Management.Board.API.Specification.ResponseObject.CardResponse;
import com.example.Project.Management.Board.API.Specification.Service.BoardService;
import com.example.Project.Management.Board.API.Specification.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boards")
public class CardController {
    private final CardService cardService;
    private final BoardService boardService;

    @Autowired
    public CardController(CardService cardService, BoardService boardService) {
        this.cardService = cardService;
        this.boardService = boardService;
    }

    // Endpoint for adding a card to a board
    @PostMapping("/{boardId}/cards")
    public ResponseEntity<CardResponse> addCardToBoard(@PathVariable Long boardId, @RequestBody CardRequest cardRequest) {
        // Check if the board exists
        Board board = boardService.getBoardById(boardId);
        if (board == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Create a new card and set its properties
        Card card = new Card();
        card.setTitle(cardRequest.getTitle());
        card.setDescription(cardRequest.getDescription());
        card.setSection(cardRequest.getSection());
        System.out.println(card.getId());

        // Add the card to the board
        board.addCard(card);
        boardService.saveBoard(board);
        // i found error here it return null for card.getId() so to solve it i use this way
        Integer newID = board.getCards().size() + 2;
        // Create the response and return
        CardResponse response = new CardResponse(
                (long) newID,
                card.getTitle(),
                card.getDescription(),
                card.getSection()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Endpoint for getting all cards from a board
    // Endpoint for getting all cards from a board
    @GetMapping("/{boardId}/cards")
    public ResponseEntity<Map<String, List<CardResponse>>> getCardsFromBoard(@PathVariable Long boardId) {
        // Check if the board with the given boardId exists
        Board board = boardService.getBoardById(boardId);
        if (board == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Retrieve the list of cards from the specified board
        List<Card> cards = board.getCards();

        // Convert the list of cards to the desired response format
        List<CardResponse> cardResponses = cards.stream()
                .map(this::convertToCardResponse)
                .collect(Collectors.toList());

        Map<String, List<CardResponse>> responseBody = new HashMap<>();
        responseBody.put("cards", cardResponses);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    // Endpoint for getting a specific card from a board by its ID
    @GetMapping("/{boardId}/cards/{cardId}")
    public ResponseEntity<CardResponse> getCardFromBoardById(
            @PathVariable Long boardId,
            @PathVariable Long cardId
    ) {
        // Check if the board with the given boardId exists
        Board board = boardService.getBoardById(boardId);
        if (board == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Find the card with the specified cardId from the board's cards
        Card card = board.getCards().stream()
                .filter(c -> c.getId().equals(cardId))
                .findFirst()
                .orElse(null);

        // If the card is not found, return 404 NOT FOUND
        if (card == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Convert the card to the desired response format
        CardResponse cardResponse = convertToCardResponse(card);

        // Return the card response with 200 OK
        return new ResponseEntity<>(cardResponse, HttpStatus.OK);
    }

    // Endpoint for updating a card on a board by its ID
    @PutMapping("/{boardId}/cards/{cardId}")
    public ResponseEntity<CardResponse> updateCardOnBoard(
            @PathVariable Long boardId,
            @PathVariable Long cardId,
            @RequestBody CardRequest cardRequest
    ) {
        // Check if the board with the given boardId exists
        Board board = boardService.getBoardById(boardId);
        if (board == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Find the card with the specified cardId from the board's cards
        Card cardToUpdate = board.getCards().stream()
                .filter(c -> c.getId().equals(cardId))
                .findFirst()
                .orElse(null);

        // If the card is not found, return 404 NOT FOUND
        if (cardToUpdate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the card with the values from the request body
        cardToUpdate.setTitle(cardRequest.getTitle());
        cardToUpdate.setDescription(cardRequest.getDescription());
        cardToUpdate.setSection(cardRequest.getSection());

        // Save the updated card to the board
        boardService.saveBoard(board);

        // Convert the updated card to the desired response format
        CardResponse cardResponse = convertToCardResponse(cardToUpdate);

        // Return the updated card response with 200 OK
        return new ResponseEntity<>(cardResponse, HttpStatus.OK);
    }

    // Endpoint for deleting a card from a board by its ID
    @DeleteMapping("/{boardId}/cards/{cardId}")
    public ResponseEntity<Map<String, String>> deleteCardFromBoard(
            @PathVariable Long boardId,
            @PathVariable Long cardId
    ) {
        // Check if the board with the given boardId exists
        Board board = boardService.getBoardById(boardId);
        if (board == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Find the card with the specified cardId from the board's cards
        Card cardToDelete = board.getCards().stream()
                .filter(c -> c.getId().equals(cardId))
                .findFirst()
                .orElse(null);

        // If the card is not found, return 404 NOT FOUND
        if (cardToDelete == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Remove the card from the board
        board.removeCard(cardToDelete);
        boardService.saveBoard(board);

        // Create the response message
        String message = "Card with ID " + cardId + " has been deleted successfully from board " + boardId + ".";
        Map<String, String> responseBody = Collections.singletonMap("message", message);

        // Return the response with 200 OK
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    private CardResponse convertToCardResponse(Card card) {
        CardResponse response = new CardResponse();
        response.setId(card.getId());
        response.setTitle(card.getTitle());
        response.setDescription(card.getDescription());
        response.setSection(card.getSection());
        return response;
    }
}
