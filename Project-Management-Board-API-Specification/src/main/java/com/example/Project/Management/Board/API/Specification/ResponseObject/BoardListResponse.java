package com.example.Project.Management.Board.API.Specification.ResponseObject;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BoardListResponse {
    private List<BoardResponse> boards;
}