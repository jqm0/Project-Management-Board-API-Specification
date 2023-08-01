package com.example.Project.Management.Board.API.Specification.ResponseObject;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
public class BoardResponse {
    private Long boardId; // Unique identifier for the board
    private String name; // Name of the board
    private Map<Integer, String> columns; // Columns of the board

    public BoardResponse(Long boardId, String name) {
        this.boardId = boardId;
        this.name = name;
        this.columns = new HashMap<>();
        this.columns.put(1, "To do");
        this.columns.put(2, "In progress");
        this.columns.put(3, "Done");
    }
}
