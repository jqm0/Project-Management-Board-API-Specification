package com.example.Project.Management.Board.API.Specification.Repositiry;
import com.example.Project.Management.Board.API.Specification.Model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepo  extends JpaRepository<Card, Long> {
}
