package com.example.Project.Management.Board.API.Specification.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@Data
@Entity
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ElementCollection
    private List<Card> cards;
    public void addCard(Card card) {
        cards.add(card);
    }
    public void removeCard(Card card) {
        cards.remove(card);
    }
}
