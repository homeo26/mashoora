package com.just.mashoora.models;

import com.just.mashoora.components.Answer;
import com.just.mashoora.components.Complaint;
import com.just.mashoora.components.LawyerRating;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
public class Lawyer extends User {


    @NotNull
    @Column(nullable = false)
    private String licenseDocument;

    @OneToOne
    @JoinColumn(name = "lawyer_rating_id")
    private LawyerRating lawyerRating;

    private int blocksBySystemCounter;

    private int alertsBySystemCounter;

    @OneToMany(mappedBy = "lawyer")
    private List<Complaint> complaints;

    @ManyToMany(mappedBy = "upVoters")
    private Set<Answer> upVotedAnswers = new HashSet<>();

    @ManyToMany(mappedBy = "downVoters")
    private Set<Answer> downVotedAnswers = new HashSet<>();


}
