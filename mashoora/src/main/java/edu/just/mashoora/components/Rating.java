package edu.just.mashoora.components;

import edu.just.mashoora.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rating {

    @Id
    private Long id;

    @OneToOne
    @MapsId // This maps the id field in Rating to the primary key in User
    private User user;

    private float civilLawRating;

    private float criminalLawRating;

    private float commercialLawRating;

    private float internationalLawRating;

    private float administrativeAndFinancialLawRating;

    private float constitutionalLawRating;

    private float privateInternationalLawRating;

    private float proceduralLawRating;

    //---------------------------------------------------

    private int civilCasesCounter;

    private int criminalCasesCounter;

    private int commercialCasesCounter;

    private int internationalCasesCounter;

    private int administrativeAndFinancialCasesCounter;

    private int constitutionalCasesCounter;

    private int proceduralCasesCounter;

    private int privateInternationalCasesCounter;

}
