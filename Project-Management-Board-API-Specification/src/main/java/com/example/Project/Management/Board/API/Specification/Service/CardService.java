package com.example.Project.Management.Board.API.Specification.Service;

import com.example.Project.Management.Board.API.Specification.Repositiry.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    @Autowired
    CardRepo cardRepo ;
}
