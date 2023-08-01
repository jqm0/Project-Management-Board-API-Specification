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
        boardResponse.put("columns", createColumnsMap());

        return boardResponse;
    }

    private Map<Integer, String> createColumnsMap() {
        Map<Integer, String> columns = new HashMap<>();
        columns.put(1, "To do");
        columns.put(2, "In progress");
        columns.put(3, "Done");
        return columns;
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

    // Endpoint for deleting a specific board given its ID
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boolean deleted = boardService.deleteBoard(boardId);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
