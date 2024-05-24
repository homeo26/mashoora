package edu.just.mashoora.services;

import edu.just.mashoora.components.LawFieldRate;
import edu.just.mashoora.components.LawyerStrength;
import edu.just.mashoora.components.Rating;
import edu.just.mashoora.constants.Constants;
import edu.just.mashoora.models.User;
import edu.just.mashoora.repository.LawyerStrengthRepository;
import edu.just.mashoora.repository.RatingRepository;
import edu.just.mashoora.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.just.mashoora.constants.ELawTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final LawyerStrengthRepository lawyerStrengthRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, UserRepository userRepository,
                         LawyerStrengthRepository lawyerStrengthRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.lawyerStrengthRepository = lawyerStrengthRepository;
    }


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
        setLawyerStrength(id, field);
        ratingRepository.save(rating);
    }

    public void setLawyerStrength(long id, ELawTypes field) {
        Optional<LawyerStrength> lawyerStrength = lawyerStrengthRepository.findById(id);
        LawyerStrength strength = new LawyerStrength();
        strength.setId(id);
        strength.setUser(userRepository.getReferenceById(id));
        if(lawyerStrength.isPresent()) strength  = lawyerStrength.get();

        switch (field) {
            case CIVIL_LAW:
                strength.setCivilLaw(true);
                break;
            case COMMERCIAL_LAW:
                strength.setCommercialLaw(true);
                break;
            case INTERNATIONAL_LAW:
                strength.setInternationalLaw(true);
                break;
            case CRIMINAL_LAW:
                strength.setCriminalLaw(true);
                break;
            case ADMINISTRATIVE_AND_FINANCE_LAW:
                strength.setAdministrativeAndFinancialLaw(true);
                break;
            case CONSTITUTIONAL_LAW:
                strength.setConstitutionalLaw(true);
                break;
            case PRIVATE_INTERNATIONAL_LAW:
                strength.setPrivateInternationalLaw(true);
                break;
            case PROCEDURAL_LAW:
                strength.setProceduralLaw(true);
                break;
            default:
                break;
        }
        lawyerStrengthRepository.save(strength);
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
    public List<String> getTopRatingFields(Long id){
        List<String> fields = new ArrayList<>();
        for(int i=0;i< Constants.LAW_FIELDS.length;i++){
            LawFieldRate rate = getRatingDetails(id, Constants.LAW_FIELDS[i]);
            if(rate.getRate() > 2.5)
                fields.add(rate.getField());
        }
        return fields;
    }
    public LawFieldRate getRatingDetails(Long id, ELawTypes field) {
        if(!ratingRepository.existsById(id))
            return LawFieldRate.builder()
                    .field("")
                    .rate(0L)
                    .Count(0)
                    .build();

        Rating rating = ratingRepository.findById(id).get();
        rating.fixRatings();

        float rate = 0L;
        int count = 0;
        switch (field) {
            case CIVIL_LAW:
                rate = rating.getCivilLawRating();
                count = rating.getCivilCasesCounter();
                break;
            case COMMERCIAL_LAW:
                rate = rating.getCommercialLawRating();
                count = rating.getCommercialCasesCounter();
                break;
            case INTERNATIONAL_LAW:
                rate = rating.getInternationalLawRating();
                count = rating.getInternationalCasesCounter();
                break;
            case CRIMINAL_LAW:
                rate = rating.getCriminalLawRating();
                count = rating.getCriminalCasesCounter();
                break;
            case ADMINISTRATIVE_AND_FINANCE_LAW:
                rate = rating.getAdministrativeAndFinancialLawRating();
                count = rating.getAdministrativeAndFinancialCasesCounter();
            case CONSTITUTIONAL_LAW:
                rate = rating.getConstitutionalLawRating();
                count = rating.getConstitutionalCasesCounter();
                break;
            case PRIVATE_INTERNATIONAL_LAW:
                rate = rating.getPrivateInternationalLawRating();
                count = rating.getPrivateInternationalCasesCounter();
                break;
            case PROCEDURAL_LAW:
                rate = rating.getProceduralLawRating();
                count = rating.getProceduralCasesCounter();
                break;
        }

        return LawFieldRate.builder()
                .field(field.toString())
                .rate(rate)
                .Count(count)
                .build();
    }

    public List<User> getFieldStrongLawyers(ELawTypes field){
        List<LawyerStrength> lawyerStrengths = new ArrayList<>();
        switch (field) {
            case CIVIL_LAW
                    -> lawyerStrengths = lawyerStrengthRepository.findAllByCivilLawTrue();
            case COMMERCIAL_LAW
                    -> lawyerStrengths = lawyerStrengthRepository.findAllByCommercialLawTrue();
            case INTERNATIONAL_LAW
                    -> lawyerStrengths = lawyerStrengthRepository.findAllByInternationalLawTrue();
            case CRIMINAL_LAW
                    -> lawyerStrengths = lawyerStrengthRepository.findAllByCriminalLawTrue();
            case ADMINISTRATIVE_AND_FINANCE_LAW
                    -> lawyerStrengths = lawyerStrengthRepository.findAllByAdministrativeAndFinancialLawTrue();
            case CONSTITUTIONAL_LAW
                    -> lawyerStrengths = lawyerStrengthRepository.findAllByConstitutionalLawTrue();
            case PRIVATE_INTERNATIONAL_LAW
                    -> lawyerStrengths = lawyerStrengthRepository.findAllByPrivateInternationalLawTrue();
            case PROCEDURAL_LAW
                    -> lawyerStrengths = lawyerStrengthRepository.findAllByProceduralLawTrue();
            default -> {
            }
        }
        List<User> lawyers = new ArrayList<>();
        for (var lawyerStrength : lawyerStrengths) {
            lawyers.add(userRepository.getReferenceById(lawyerStrength.getId()));
        }
        return lawyers;
    }

    public List<LawFieldRate> getUserRatings(long id){
        List<LawFieldRate> rates = new ArrayList<>();
        Rating rating = ratingRepository.findById(id).get();
        rating.fixRatings();
        for(int i=0;i< Constants.LAW_FIELDS.length;i++){
            LawFieldRate rate = getRatingDetails(id, Constants.LAW_FIELDS[i]);
            rates.add(rate);
        }
        return rates;
    }

}
