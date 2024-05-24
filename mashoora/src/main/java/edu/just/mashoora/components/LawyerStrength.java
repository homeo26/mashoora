package edu.just.mashoora.components;

import edu.just.mashoora.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "LawyerStrength")
public class LawyerStrength {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId // This maps the id field in Rating to the primary key in User
    private User user;

    private boolean civilLaw;

    private boolean criminalLaw;

    private boolean commercialLaw;

    private boolean internationalLaw;

    private boolean administrativeAndFinancialLaw;

    private boolean constitutionalLaw;

    private boolean privateInternationalLaw;

    private boolean proceduralLaw;

}
