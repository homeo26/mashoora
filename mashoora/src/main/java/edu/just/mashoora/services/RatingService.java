package edu.just.mashoora.services;

import edu.just.mashoora.components.LawFieldRate;
import edu.just.mashoora.components.Rating;
import edu.just.mashoora.constants.ELawTypes;
import edu.just.mashoora.models.User;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface RatingService {

    void saveRating(Long id, ELawTypes field, int rate);

    void setLawyerStrength(Long id, ELawTypes field, boolean value);

    void increaseCasesCount(Long id, @NotNull ELawTypes field);

    Rating getRating(Long id);

    ArrayList<ELawTypes> selectedAttributes(Boolean civilLaw, Boolean commercialLaw,
                                            Boolean internationalLaw, Boolean criminalLaw,
                                            Boolean administrativeAndFinancialLaw, Boolean constitutionalLaw,
                                            Boolean privateInternationalLaw, Boolean proceduralLaw);

    List<String> getTopRatingFields(Long id);

    LawFieldRate getRatingDetails(Long id, ELawTypes field);

    List<User> getFieldStrongLawyers(ELawTypes field);

    List<LawFieldRate> getUserRatings(Long id);

    void resetLawyerStrength(Long id);

    void updateLawyerStrength(Long id, Boolean civilLaw, Boolean commercialLaw,
                              Boolean internationalLaw, Boolean criminalLaw, Boolean administrativeAndFinancialLaw,
                              Boolean constitutionalLaw, Boolean privateInternationalLaw, Boolean proceduralLaw);

    List<String> getLawyerStrength(User user);
}
