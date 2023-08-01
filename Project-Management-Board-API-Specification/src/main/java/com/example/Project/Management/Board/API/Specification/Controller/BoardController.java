package com.example.Project.Management.Board.API.Specification.Controller;

import com.example.Project.Management.Board.API.Specification.Model.Board;
import com.example.Project.Management.Board.API.Specification.RequestObject.BoradRequest;
import com.example.Project.Management.Board.API.Specification.ResponseObject.BoardResponse;
import com.example.Project.Management.Board.API.Specification.Service.BoardService;
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
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // Endpoint for creating a new board
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@RequestBody BoradRequest boardRequest) {
        Board createdBoard = boardService.createBoard(boardRequest);
        BoardResponse response = new BoardResponse(createdBoard.getId(), createdBoard.getTitle());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Endpoint for getting all boards
    @GetMapping
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        List<Map<String, Object>> boardResponses = boards.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        Map<String, List<Map<String, Object>>> responseBody = new HashMap<>();
        responseBody.put("boards", boardResponses);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    private Map<String, Object> convertToResponse(Board board) {
        Map<String, Object> boardResponse = new HashMap<>();
        boardResponse.put("board_id", board.getId());
        boardResponse.put("name", board.getTitle());
        boardResponse.put("columns", board.getColumns());
        return boardResponse;
    }

    // Endpoint for retrieving a single board by its ID
    @GetMapping("/{boardId}")
    public ResponseEntity<Board> getBoard(@PathVariable Long boardId) {
        Board board = boardService.getBoardById(boardId);
        if (board == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(board, HttpStatus.OK);
    }
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long boardId, @RequestBody BoradRequest boardRequest) {
        Board updatedBoard = boardService.updateBoard(boardId, boardRequest);
        if (updatedBoard == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BoardResponse response = convertToResponse2(updatedBoard);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ... Other methods ...

    private BoardResponse convertToResponse2(Board board) {
        // Create a new BoardResponse object and set its properties
        BoardResponse response = new BoardResponse();
        response.setBoardId(board.getId());
        response.setTitle(board.getTitle());
        response.setColumns(board.getColumns());
        return response;
    }

    // Endpoint for deleting a specific board given its ID
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> deleteBoard(@PathVariable Long boardId) {
        boolean deleted = boardService.deleteBoard(boardId);
        if (!deleted) {
            // Return 404 Not Found if the board with the given ID does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("successful", true);
        responseBody.put("message", "Board with ID " + boardId + " has been deleted successfully.");

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
