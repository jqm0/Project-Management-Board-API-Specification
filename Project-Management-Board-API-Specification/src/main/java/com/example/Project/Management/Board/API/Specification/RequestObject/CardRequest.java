package com.example.Project.Management.Board.API.Specification.RequestObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequest {
    private String title;
    private String description;
    private String section;
}
