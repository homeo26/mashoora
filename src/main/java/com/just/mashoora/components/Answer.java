package com.just.mashoora.components;

import com.just.mashoora.models.Customer;
import com.just.mashoora.models.Lawyer;
import com.just.mashoora.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String body;

    @NotNull
    @Column(nullable = false)
    private Timestamp datePosted;

    @ManyToMany
    @JoinTable(
            name = "answer_upvoters",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "lawyer_id")
    )
    private Set<Lawyer> upVoters = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "answer_downvoters",
            joinColumns = @JoinColumn(name = "answer_id"),
            inverseJoinColumns = @JoinColumn(name = "lawyer_id")
    )
    private Set<Lawyer> downVoters = new HashSet<>();

    private int totalVotes = 0;

}
