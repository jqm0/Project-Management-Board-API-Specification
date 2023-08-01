package com.example.Project.Management.Board.API.Specification.Service;

import com.example.Project.Management.Board.API.Specification.Model.Board;
import com.example.Project.Management.Board.API.Specification.Repositiry.BoardRepo;
import com.example.Project.Management.Board.API.Specification.RequestObject.BoradRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepo boardRepository;

    @Autowired
    public BoardService(BoardRepo boardRepository) {
        this.boardRepository = boardRepository;
    }
    public Board createBoard(BoradRequest boardRequest) {
        // Map the BoardRequest to a Board entity
        Board board = new Board();
        board.setTitle(boardRequest.getTitle());
        // Save the newly created board to the database
        return boardRepository.save(board);
    }
}
