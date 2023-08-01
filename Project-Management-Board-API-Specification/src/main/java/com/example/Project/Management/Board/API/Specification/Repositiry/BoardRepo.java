package com.example.Project.Management.Board.API.Specification.Repositiry;

import com.example.Project.Management.Board.API.Specification.Model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepo extends JpaRepository<Board, Long> {
}
