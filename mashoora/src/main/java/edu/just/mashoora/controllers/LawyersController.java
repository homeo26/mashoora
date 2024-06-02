package edu.just.mashoora.controllers;

import edu.just.mashoora.components.LawFieldRate;
import edu.just.mashoora.constants.ELawTypes;
import edu.just.mashoora.models.User;
import edu.just.mashoora.payload.response.LawyerInfoResponse;
import edu.just.mashoora.payload.response.LawyerListingResponse;
import edu.just.mashoora.repository.UserRepository;
import edu.just.mashoora.services.RatingService;
import edu.just.mashoora.services.impl.RatingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/lawyers")
public class LawyersController {

    // TODO: Migrate the logic of all controllers to Services directory

    private final RatingService ratingService;
    private final UserRepository userRepository;

    @Autowired
    public LawyersController(RatingServiceImpl ratingServiceImpl, UserRepository userRepository) {
        this.ratingService = ratingServiceImpl;
        this.userRepository = userRepository;
    }

    @GetMapping("/list/{field}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<LawyerListingResponse>> fieldLawyerListing(@PathVariable("field") ELawTypes field) {
        List<User> fieldLawyers = ratingService.getFieldStrongLawyers(field);
        List<LawyerListingResponse> lawyerListingNodes = new ArrayList<>();
        for (User lawyer : fieldLawyers) {
            Long id = lawyer.getId();
            String firstName = lawyer.getFirstName();
            String lastName = lawyer.getLastName();
            String userName = lawyer.getUsername();
            List<String> lawyerFields = ratingService.getTopRatingFields(lawyer.getId());
            LawFieldRate lawFieldRate = ratingService.getRatingDetails(lawyer.getId(), field);
            LawyerListingResponse lawyerListingResponse
                    = LawyerListingResponse.builder()
                    .id(id)
                    .userName(userName)
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
    public ResponseEntity<LawyerInfoResponse> LawyerInfo(@PathVariable("id") Long id) {
        LawyerInfoResponse lawyerInfoResponse = new LawyerInfoResponse();
        User lawyer = userRepository.findById(id).get();
        lawyerInfoResponse.setLawyerId(id);
        lawyerInfoResponse.setUserName(lawyer.getUsername());
        lawyerInfoResponse.setFirstName(lawyer.getFirstName());
        lawyerInfoResponse.setLastName(lawyer.getLastName());
        lawyerInfoResponse.setEmail(lawyer.getEmail());
        lawyerInfoResponse.setLawFieldsDetails(ratingService.getUserRatings(id));

        return ResponseEntity.ok(lawyerInfoResponse);
    }


    @PostMapping("/lawyerDetails")
    @PreAuthorize("hasRole('LAWYER')")
    public ResponseEntity<String> requiredLawyerDetails(@RequestParam("file") MultipartFile file,
                                                        @RequestParam(value = "civilLaw", required = false) Boolean civilLaw,
                                                        @RequestParam(value = "commercialLaw", required = false) Boolean commercialLaw,
                                                        @RequestParam(value = "internationalLaw", required = false) Boolean internationalLaw,
                                                        @RequestParam(value = "criminalLaw", required = false) Boolean criminalLaw,
                                                        @RequestParam(value = "administrativeAndFinancialLaw", required = false) Boolean administrativeAndFinancialLaw,
                                                        @RequestParam(value = "constitutionalLaw", required = false) Boolean constitutionalLaw,
                                                        @RequestParam(value = "privateInternationalLaw", required = false) Boolean privateInternationalLaw,
                                                        @RequestParam(value = "proceduralLaw", required = false) Boolean proceduralLaw) {

        if (!file.getContentType().equals("application/pdf")) {
            return ResponseEntity.badRequest().body("Invalid file type. Please upload a PDF file.");
        }

        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username).get();
            Long id = user.getId();
            String fileName = id + ".pdf";

            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir))
                Files.createDirectories(uploadDir);

            Path filePath = Paths.get(uploadDir + "/", fileName);

            Files.write(filePath, file.getBytes());

            ratingService.updateLawyerStrength(id, civilLaw, commercialLaw, internationalLaw, criminalLaw,
                    administrativeAndFinancialLaw, constitutionalLaw, privateInternationalLaw, proceduralLaw);

            return ResponseEntity.ok("Lawyer Profile Completed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error sending request: " + e.getMessage());
        }

    }

    @PostMapping("/updateLawyerStrength")
    public ResponseEntity<String> updateLawyerStrength(@RequestParam(value = "civilLaw", required = false) Boolean civilLaw,
                                                       @RequestParam(value = "commercialLaw", required = false) Boolean commercialLaw,
                                                       @RequestParam(value = "internationalLaw", required = false) Boolean internationalLaw,
                                                       @RequestParam(value = "criminalLaw", required = false) Boolean criminalLaw,
                                                       @RequestParam(value = "administrativeAndFinancialLaw", required = false) Boolean administrativeAndFinancialLaw,
                                                       @RequestParam(value = "constitutionalLaw", required = false) Boolean constitutionalLaw,
                                                       @RequestParam(value = "privateInternationalLaw", required = false) Boolean privateInternationalLaw,
                                                       @RequestParam(value = "proceduralLaw", required = false) Boolean proceduralLaw) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).get();
        Long id = user.getId();
        ratingService.updateLawyerStrength(id, civilLaw, commercialLaw, internationalLaw, criminalLaw,
                administrativeAndFinancialLaw, constitutionalLaw, privateInternationalLaw, proceduralLaw);
        return ResponseEntity.ok("Law Fields Speciality Updated");
    }

    @GetMapping("myStrengths")
    @PreAuthorize("hasRole('LAWYER')")
    public ResponseEntity<List<String>> getLawyerStrength(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).get();
        return ResponseEntity.ok(ratingService.getLawyerStrength(user));
    }

}
