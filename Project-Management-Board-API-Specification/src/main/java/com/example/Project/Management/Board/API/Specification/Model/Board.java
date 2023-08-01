package com.example.Project.Management.Board.API.Specification.Model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Card> cards = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "board_columns", joinColumns = @JoinColumn(name = "board_id"))
    @MapKeyColumn(name = "column_id")
    @Column(name = "column_name")
    private Map<Integer, String> columns;
    public void addCard(Card card) {
        cards.add(card);
    }
    public void removeCard(Card card) {
        cards.remove(card);
    }
}
