package edu.just.mashoora.services.impl;

import edu.just.mashoora.components.LawyerStrength;
import edu.just.mashoora.components.Rating;
import edu.just.mashoora.constants.Constants;
import edu.just.mashoora.constants.ELawTypes;
import edu.just.mashoora.models.User;
import edu.just.mashoora.repository.LawyerStrengthRepository;
import edu.just.mashoora.repository.RatingRepository;
import edu.just.mashoora.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LawyerStrengthRepository lawyerStrengthRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveRating() {
        Long id = 1L;
        ELawTypes field = ELawTypes.CIVIL_LAW;
        int rate = 5;
        Rating rating = new Rating();
        rating.setId(id);
        rating.setCivilLawRating(0);

        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));
        when(userRepository.getReferenceById(id)).thenReturn(new User());

        ratingService.saveRating(id, field, rate);

        ArgumentCaptor<Rating> ratingCaptor = ArgumentCaptor.forClass(Rating.class);
        verify(ratingRepository).save(ratingCaptor.capture());
        Rating savedRating = ratingCaptor.getValue();

        assertEquals(5, savedRating.getCivilLawRating());
    }

    @Test
    public void testSetLawyerStrength() {
        Long id = 1L;
        ELawTypes field = ELawTypes.CIVIL_LAW;
        boolean value = true;
        LawyerStrength lawyerStrength = new LawyerStrength();
        lawyerStrength.setId(id);
        lawyerStrength.setCivilLaw(false);

        when(lawyerStrengthRepository.findById(id)).thenReturn(Optional.of(lawyerStrength));
        when(userRepository.getReferenceById(id)).thenReturn(new User());

        ratingService.setLawyerStrength(id, field, value);

        ArgumentCaptor<LawyerStrength> strengthCaptor = ArgumentCaptor.forClass(LawyerStrength.class);
        verify(lawyerStrengthRepository).save(strengthCaptor.capture());
        LawyerStrength savedStrength = strengthCaptor.getValue();

        assertTrue(savedStrength.isCivilLaw());
    }

    @Test
    public void testIncreaseCasesCount() {
        Long id = 1L;
        ELawTypes field = ELawTypes.CIVIL_LAW;
        Rating rating = new Rating();
        rating.setId(id);
        rating.setCivilCasesCounter(0);

        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));
        when(userRepository.getReferenceById(id)).thenReturn(new User());

        ratingService.increaseCasesCount(id, field);

        ArgumentCaptor<Rating> ratingCaptor = ArgumentCaptor.forClass(Rating.class);
        verify(ratingRepository).save(ratingCaptor.capture());
        Rating savedRating = ratingCaptor.getValue();

        assertEquals(1, savedRating.getCivilCasesCounter());
    }

    @Test
    public void testGetRating() {
        Long id = 1L;
        Rating rating = new Rating();
        rating.setId(id);

        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));
        when(userRepository.getReferenceById(id)).thenReturn(new User());

        Rating result = ratingService.getRating(id);

        assertEquals(rating, result);
    }

    @Test
    public void testGetRating_NewRating() {
        Long id = 1L;

        when(ratingRepository.findById(id)).thenReturn(Optional.empty());
        when(userRepository.getReferenceById(id)).thenReturn(new User());

        Rating result = ratingService.getRating(id);

        assertEquals(id, result.getId());
        verify(ratingRepository, never()).save(any(Rating.class));
    }

    @Test
    public void testSelectedAttributes() {
        Boolean civilLaw = true;
        Boolean commercialLaw = null;
        Boolean internationalLaw = true;
        Boolean criminalLaw = false;
        Boolean administrativeAndFinancialLaw = true;
        Boolean constitutionalLaw = null;
        Boolean privateInternationalLaw = false;
        Boolean proceduralLaw = true;

        var result = ratingService.selectedAttributes(
                civilLaw, commercialLaw, internationalLaw, criminalLaw,
                administrativeAndFinancialLaw, constitutionalLaw, privateInternationalLaw, proceduralLaw);

        assertEquals(6, result.size());
        assertTrue(result.contains(ELawTypes.CIVIL_LAW));
        assertTrue(result.contains(ELawTypes.INTERNATIONAL_LAW));
        assertTrue(result.contains(ELawTypes.ADMINISTRATIVE_AND_FINANCE_LAW));
        assertTrue(result.contains(ELawTypes.PROCEDURAL_LAW));
    }


    @Test
    public void testGetFieldStrongLawyers() {
        ELawTypes field = ELawTypes.CIVIL_LAW;
        LawyerStrength lawyerStrength = new LawyerStrength();
        lawyerStrength.setId(1L);
        List<LawyerStrength> lawyerStrengths = List.of(lawyerStrength);
        User user = new User();
        user.setId(1L);

        when(lawyerStrengthRepository.findAllByCivilLawTrue()).thenReturn(lawyerStrengths);
        when(userRepository.getReferenceById(1L)).thenReturn(user);

        List<User> result = ratingService.getFieldStrongLawyers(field);

        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    @Test
    public void testGetUserRatings() {
        Long id = 1L;
        Rating rating = new Rating();
        rating.setId(id);
        rating.setCivilLawRating(3.0f);
        rating.setCivilCasesCounter(1);

        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));
        when(ratingRepository.existsById(id)).thenReturn(true);

        var result = ratingService.getUserRatings(id);

        assertEquals(Constants.LAW_FIELDS.length, result.size());
    }

    @Test
    public void testResetLawyerStrength() {
        Long id = 1L;
        ratingService.resetLawyerStrength(id);

        for (ELawTypes field : Constants.LAW_FIELDS) {
            verify(lawyerStrengthRepository, atLeastOnce()).save(any(LawyerStrength.class));
        }
    }

    @Test
    public void testUpdateLawyerStrength() {
        Long id = 1L;
        Boolean civilLaw = true;
        Boolean commercialLaw = false;
        Boolean internationalLaw = true;
        Boolean criminalLaw = false;
        Boolean administrativeAndFinancialLaw = false;
        Boolean constitutionalLaw = true;
        Boolean privateInternationalLaw = false;
        Boolean proceduralLaw = true;

        ratingService.updateLawyerStrength(
                id, civilLaw, commercialLaw, internationalLaw, criminalLaw,
                administrativeAndFinancialLaw, constitutionalLaw, privateInternationalLaw, proceduralLaw);

        for (ELawTypes field : Constants.LAW_FIELDS) {
            verify(lawyerStrengthRepository, atLeastOnce()).save(any(LawyerStrength.class));
        }
    }


}
