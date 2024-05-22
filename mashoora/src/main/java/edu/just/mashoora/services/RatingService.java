package edu.just.mashoora.services;

import edu.just.mashoora.components.Rating;
import edu.just.mashoora.repository.RatingRepository;
import edu.just.mashoora.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    UserRepository userRepository;


    public void saveRating(long id, String field, int rate) {
        Rating rating = getRating(id);
        switch (field) {
            case "civilLaw":
                rating.setCivilLawRating(rating.getCivilLawRating()+rate);
                break;
            case "commercialLaw":
                rating.setCommercialLawRating(rating.getCommercialLawRating()+rate);
                break;
            case "internationalLaw":
                rating.setInternationalLawRating(rating.getInternationalLawRating()+rate);
                break;
            case "criminalLaw":
                rating.setCriminalLawRating(rating.getCriminalLawRating()+rate);
                break;
            case "administrativeAndFinancialLaw":
                rating.setAdministrativeAndFinancialLawRating(rating.getAdministrativeAndFinancialLawRating()+rate);
                break;
            case "constitutionalLaw":
                rating.setConstitutionalLawRating(rating.getConstitutionalLawRating()+rate);
                break;
            case "privateInternationalLaw":
                rating.setPrivateInternationalLawRating(rating.getPrivateInternationalLawRating()+rate);
                break;
            case "proceduralLaw":
                rating.setProceduralLawRating(rating.getProceduralLawRating()+rate);
                break;
        }
        ratingRepository.save(rating);
    }

    public void increaseCasesCount(long id, @NotNull String field) {
        Rating rating = getRating(id);
        switch (field) {
            case "civilLaw":
                rating.setCivilCasesCounter(rating.getCivilCasesCounter()+1);
                break;
            case "commercialLaw":
                rating.setCommercialCasesCounter(rating.getCommercialCasesCounter()+1);
                break;
            case "internationalLaw":
                rating.setInternationalLawRating(rating.getInternationalCasesCounter()+1);
                break;
            case "criminalLaw":
                rating.setCriminalLawRating(rating.getCriminalCasesCounter()+1);
                break;
            case "administrativeAndFinancialLaw":
                rating.setAdministrativeAndFinancialLawRating(rating.getAdministrativeAndFinancialCasesCounter()+1);
                break;
            case "constitutionalLaw":
                rating.setConstitutionalLawRating(rating.getConstitutionalCasesCounter()+1);
                break;
            case "privateInternationalLaw":
                rating.setPrivateInternationalLawRating(rating.getPrivateInternationalCasesCounter()+1);
                break;
            case "proceduralLaw":
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

    public ArrayList<String> selectedAttributes(Integer civilLaw, Integer commercialLaw, Integer internationalLaw, Integer criminalLaw, Integer administrativeAndFinancialLaw, Integer constitutionalLaw, Integer privateInternationalLaw, Integer proceduralLaw){
        ArrayList<String> fields = new ArrayList<>();
        if (civilLaw != null) fields.add("civilLaw");
        if (commercialLaw != null) fields.add("commercialLaw");
        if (internationalLaw != null) fields.add("internationalLaw");
        if (criminalLaw != null) fields.add("criminalLaw");
        if (administrativeAndFinancialLaw != null) fields.add("administrativeAndFinancialLaw");
        if (constitutionalLaw != null) fields.add("constitutionalLaw");
        if (privateInternationalLaw != null) fields.add("privateInternationalLaw");
        if (proceduralLaw != null) fields.add("proceduralLaw");
        return fields;
    }
}
