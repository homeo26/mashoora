package edu.just.mashoora.components;

import edu.just.mashoora.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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

    //--------------------------------------------------


    public float calculateAdministrativeAndFinancialLawRating() {
        return calculateFieldRating(administrativeAndFinancialLawRating, administrativeAndFinancialCasesCounter);
    }

    public float calculateConstitutionalLawRating() {
        return calculateFieldRating(constitutionalLawRating, constitutionalCasesCounter);
    }

    public float calculateCivilLawRating() {
        return calculateFieldRating(civilLawRating, civilCasesCounter);
    }

    public float calculateCommercialLawRating() {
        return calculateFieldRating(commercialLawRating, commercialCasesCounter);
    }

    public float calculateInternationalLawRating() {
        return calculateFieldRating(internationalLawRating, internationalCasesCounter);
    }

    public float calculateCriminalLawRating() {
        return calculateFieldRating(criminalLawRating, criminalCasesCounter);
    }

    public float calculateProceduralLawRating() {
        return calculateFieldRating(proceduralLawRating, proceduralCasesCounter);
    }

    public float calculatePrivateInternationalLawRating() {
        return calculateFieldRating(privateInternationalLawRating, privateInternationalCasesCounter);
    }

    private float calculateFieldRating(float fieldRating, int fieldCounter) {
        if (fieldCounter == 0)
            return fieldRating;
        return (fieldRating - 5) / fieldCounter;
    }

    public void fixRatings() {
        civilLawRating = calculateCivilLawRating();
        criminalLawRating = calculateCriminalLawRating();
        commercialLawRating = calculateCommercialLawRating();
        internationalLawRating = calculateInternationalLawRating();
        administrativeAndFinancialLawRating = calculateAdministrativeAndFinancialLawRating();
        constitutionalLawRating = calculateConstitutionalLawRating();
        proceduralLawRating = calculateProceduralLawRating();
        privateInternationalLawRating = calculatePrivateInternationalLawRating();
    }

}
