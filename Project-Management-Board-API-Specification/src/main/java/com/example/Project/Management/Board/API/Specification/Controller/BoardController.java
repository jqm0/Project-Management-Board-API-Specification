package com.example.Project.Management.Board.API.Specification.Controller;

import com.example.Project.Management.Board.API.Specification.Model.Board;
import com.example.Project.Management.Board.API.Specification.RequestObject.BoradRequest;
import com.example.Project.Management.Board.API.Specification.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // Endpoint for creating a new board
    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody BoradRequest boardRequest) {
        Board createdBoard = boardService.createBoard(boardRequest);
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    
}
