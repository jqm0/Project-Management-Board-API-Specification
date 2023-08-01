package com.example.Project.Management.Board.API.Specification.Controller;

import com.example.Project.Management.Board.API.Specification.Model.Board;
import com.example.Project.Management.Board.API.Specification.RequestObject.BoradRequest;
import com.example.Project.Management.Board.API.Specification.ResponseObject.BoardResponse;
import com.example.Project.Management.Board.API.Specification.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Board>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        return new ResponseEntity<>(boards, HttpStatus.OK);
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
