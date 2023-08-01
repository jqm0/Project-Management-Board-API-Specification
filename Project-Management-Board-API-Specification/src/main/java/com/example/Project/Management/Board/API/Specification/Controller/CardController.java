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
                (long)newID,
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

    // ... other code ...

    private CardResponse convertToCardResponse(Card card) {
        CardResponse response = new CardResponse();
        response.setId(card.getId());
        response.setTitle(card.getTitle());
        response.setDescription(card.getDescription());
        response.setSection(card.getSection());
        return response;
    }
}
