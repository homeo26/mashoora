package edu.just.mashoora.controllers;

import edu.just.mashoora.utils.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse<String>> customerAccess() {

        StandardResponse<String> response = new StandardResponse<>();
        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setDetailedStatusCode("customer content returned successfully");
        response.setData("Customer Content");

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @GetMapping("/lawyer")
    @PreAuthorize("hasRole('LAWYER')")
    public ResponseEntity<StandardResponse<String>> lawyerAccess() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        StandardResponse<String> response = new StandardResponse<>();
        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setDetailedStatusCode("lawyer content returned successfully");
        response.setData("Lawyer Board");

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StandardResponse<String>> adminAccess() {

        StandardResponse<String> response = new StandardResponse<>();
        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setDetailedStatusCode("Admin content returned successfully");
        response.setData("Admin Board");

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @GetMapping("/testAuth")
    public String testAuth() {
        return "Test Auth";
    }
}
