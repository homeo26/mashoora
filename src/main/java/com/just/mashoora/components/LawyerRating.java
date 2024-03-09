package com.just.mashoora.components;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class LawyerRating {

    @NotNull
    @Id
    private Long id;

    private float civilLawRating;

    private float criminalLawRating;

    private float commercialLawRating;

    private float internationalLawRating;

    private float administrativeAndFinancialLawRating;

    private float constitutionalLawRating;

    private float proceduralLawRating;

    private int civilCasesCounter;

    private int criminalCasesCounter;

    private int commercialCasesCounter;

    private int internationalCasesCounter;

    private int administrativeAndFinancialCasesCounter;

    private int constitutionalCasesCounter;

    private int proceduralCasesCounter;


}
