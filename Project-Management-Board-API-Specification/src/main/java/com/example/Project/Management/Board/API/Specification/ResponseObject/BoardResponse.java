package com.example.Project.Management.Board.API.Specification.ResponseObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {
    private Long boardId; // Unique identifier for the board
    private String title; // Name of the board
    private Map<Integer, String> columns; // Columns of the board

    public BoardResponse(Long boardId, String title) {
        this.boardId = boardId;
        this.title = title;
        this.columns = new HashMap<>();
        this.columns.put(1, "To do");
        this.columns.put(2, "In progress");
        this.columns.put(3, "Done");
    }


}
