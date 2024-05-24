package edu.just.mashoora.controllers;

import edu.just.mashoora.components.LawFieldRate;
import edu.just.mashoora.constants.ELawTypes;
import edu.just.mashoora.models.User;
import edu.just.mashoora.payload.response.LawyerInfoResponse;
import edu.just.mashoora.payload.response.LawyerListingResponse;
import edu.just.mashoora.repository.UserRepository;
import edu.just.mashoora.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/lawyers")
public class LawyersController {

    private final RatingService ratingService;
    private final UserRepository userRepository;

    @Autowired
    public LawyersController(RatingService ratingService, UserRepository userRepository) {
        this.ratingService = ratingService;
        this.userRepository = userRepository;
    }

    @GetMapping("/list/{field}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<LawyerListingResponse>> fieldLawyerListing(@PathVariable("field") ELawTypes field){
        List<User> fieldLawyers = ratingService.getFieldStrongLawyers(field);
        List<LawyerListingResponse> lawyerListingNodes = new ArrayList<>();
        for(User lawyer : fieldLawyers) {
            long id = lawyer.getId();
            String firstName = lawyer.getFirstName();
            String lastName = lawyer.getLastName();
            List<String> lawyerFields = ratingService.getTopRatingFields(lawyer.getId());
            LawFieldRate lawFieldRate = ratingService.getRatingDetails(lawyer.getId(), field);
            LawyerListingResponse lawyerListingResponse
                    = LawyerListingResponse.builder()
                    .id(id)
                    .firstName(firstName)
                    .lastName(lastName)
                    .topLawFields(lawyerFields)
                    .lawFieldRate(lawFieldRate)
                    .build();
            lawyerListingNodes.add(lawyerListingResponse);
        }

        return ResponseEntity.ok(lawyerListingNodes);
    }

    @GetMapping("/LawyerInfo/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<LawyerInfoResponse> LawyerInfo(@PathVariable ("id") long id){
        LawyerInfoResponse  lawyerInfoResponse = new LawyerInfoResponse();
        // ToDo: prepare the response
        User lawyer = userRepository.findById(id).get();
        lawyerInfoResponse.setLawyerId(id);
        lawyerInfoResponse.setUserName(lawyer.getUsername());
        lawyerInfoResponse.setFirstName(lawyer.getFirstName());
        lawyerInfoResponse.setLastName(lawyer.getLastName());
        lawyerInfoResponse.setEmail(lawyer.getEmail());
        lawyerInfoResponse.setLawFieldsDetails(ratingService.getUserRatings(id));

        return ResponseEntity.ok(lawyerInfoResponse);
    }
}
