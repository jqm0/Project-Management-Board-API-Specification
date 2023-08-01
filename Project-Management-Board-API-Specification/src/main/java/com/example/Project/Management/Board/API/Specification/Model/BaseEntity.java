package com.example.Project.Management.Board.API.Specification.Model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
@MappedSuperclass
@Getter
@Setter
@Data
public class BaseEntity {
    Date createdDate;
    Date updatedDate;
    Boolean isActive;
}

