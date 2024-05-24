package edu.just.mashoora.repository;

import edu.just.mashoora.components.LawyerStrength;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LawyerStrengthRepository extends JpaRepository<LawyerStrength, Long>{
    List<LawyerStrength> findAllByCivilLawTrue();
    List<LawyerStrength> findAllByCriminalLawTrue();
    List<LawyerStrength> findAllByCommercialLawTrue();
    List<LawyerStrength> findAllByInternationalLawTrue();
    List<LawyerStrength> findAllByPrivateInternationalLawTrue();
    List<LawyerStrength> findAllByProceduralLawTrue();
    List<LawyerStrength> findAllByConstitutionalLawTrue();

    @Query("SELECT ls FROM LawyerStrength ls WHERE ls.administrativeAndFinancialLaw = true")
    List<LawyerStrength> findAllByAdministrativeAndFinancialLawTrue();
}
