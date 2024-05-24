package edu.just.mashoora.controllers;

import edu.just.mashoora.constants.ELawTypes;
import edu.just.mashoora.models.User;
import edu.just.mashoora.payload.response.LawyerListingResponse;
import edu.just.mashoora.repository.UserRepository;
import edu.just.mashoora.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("list/{field}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<User>> fieldLawyerListing(@PathVariable("field") ELawTypes field){
        return ResponseEntity.ok(ratingService.getFieldStrongLawyers(field));
    }
}
