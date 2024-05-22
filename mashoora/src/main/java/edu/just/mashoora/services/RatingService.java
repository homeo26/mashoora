package edu.just.mashoora.services;

import edu.just.mashoora.components.Rating;
import edu.just.mashoora.repository.RatingRepository;
import edu.just.mashoora.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.just.mashoora.constants.ELawTypes;
import java.util.ArrayList;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    UserRepository userRepository;


    public void saveRating(long id, ELawTypes field, int rate) {
        Rating rating = getRating(id);
        switch (field) {
            case CIVIL_LAW:
                rating.setCivilLawRating(rating.getCivilLawRating()+rate);
                break;
            case COMMERCIAL_LAW:
                rating.setCommercialLawRating(rating.getCommercialLawRating()+rate);
                break;
            case INTERNATIONAL_LAW:
                rating.setInternationalLawRating(rating.getInternationalLawRating()+rate);
                break;
            case CRIMINAL_LAW:
                rating.setCriminalLawRating(rating.getCriminalLawRating()+rate);
                break;
            case ADMINISTRATIVE_AND_FINANCE_LAW:
                rating.setAdministrativeAndFinancialLawRating(rating.getAdministrativeAndFinancialLawRating()+rate);
                break;
            case CONSTITUTIONAL_LAW:
                rating.setConstitutionalLawRating(rating.getConstitutionalLawRating()+rate);
                break;
            case PRIVATE_INTERNATIONAL_LAW:
                rating.setPrivateInternationalLawRating(rating.getPrivateInternationalLawRating()+rate);
                break;
            case PROCEDURAL_LAW:
                rating.setProceduralLawRating(rating.getProceduralLawRating()+rate);
                break;
        }
        ratingRepository.save(rating);
    }

    public void increaseCasesCount(long id, @NotNull ELawTypes field) {
        Rating rating = getRating(id);
        switch (field) {
            case CIVIL_LAW:
                rating.setCivilCasesCounter(rating.getCivilCasesCounter()+1);
                break;
            case COMMERCIAL_LAW:
                rating.setCommercialCasesCounter(rating.getCommercialCasesCounter()+1);
                break;
            case INTERNATIONAL_LAW:
                rating.setInternationalLawRating(rating.getInternationalCasesCounter()+1);
                break;
            case CRIMINAL_LAW:
                rating.setCriminalLawRating(rating.getCriminalCasesCounter()+1);
                break;
            case ADMINISTRATIVE_AND_FINANCE_LAW:
                rating.setAdministrativeAndFinancialLawRating(rating.getAdministrativeAndFinancialCasesCounter()+1);
                break;
            case CONSTITUTIONAL_LAW:
                rating.setConstitutionalLawRating(rating.getConstitutionalCasesCounter()+1);
                break;
            case PRIVATE_INTERNATIONAL_LAW:
                rating.setPrivateInternationalLawRating(rating.getPrivateInternationalCasesCounter()+1);
                break;
            case PROCEDURAL_LAW:
                rating.setProceduralLawRating(rating.getProceduralCasesCounter()+1);
                break;
        }
        ratingRepository.save(rating);
    }

    public Rating getRating(long id) {

        if(ratingRepository.findById(id).isPresent())
            return ratingRepository.findById(id).get();
        Rating rating = new Rating();
        rating.setId(id);
        rating.setUser(userRepository.getReferenceById(id));
        return rating;
    }

    public ArrayList<ELawTypes> selectedAttributes(Integer civilLaw, Integer commercialLaw, Integer internationalLaw, Integer criminalLaw, Integer administrativeAndFinancialLaw, Integer constitutionalLaw, Integer privateInternationalLaw, Integer proceduralLaw){
        ArrayList<ELawTypes> fields = new ArrayList<>();
        if (civilLaw != null) fields.add(ELawTypes.CIVIL_LAW);
        if (commercialLaw != null) fields.add(ELawTypes.COMMERCIAL_LAW);
        if (internationalLaw != null) fields.add(ELawTypes.INTERNATIONAL_LAW);
        if (criminalLaw != null) fields.add(ELawTypes.CRIMINAL_LAW);
        if (administrativeAndFinancialLaw != null) fields.add(ELawTypes.ADMINISTRATIVE_AND_FINANCE_LAW);
        if (constitutionalLaw != null) fields.add(ELawTypes.CONSTITUTIONAL_LAW);
        if (privateInternationalLaw != null) fields.add(ELawTypes.PRIVATE_INTERNATIONAL_LAW);
        if (proceduralLaw != null) fields.add(ELawTypes.PROCEDURAL_LAW);
        return fields;
    }
}
