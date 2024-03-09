package com.just.mashoora.components;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity
public class Question {

    @Id
    private Long id;

    private String customerId;

    private String title;

    private String body;


    private Timestamp dateCreated;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

}
