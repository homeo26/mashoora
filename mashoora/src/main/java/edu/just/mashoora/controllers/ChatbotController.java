package edu.just.mashoora.controllers;

import edu.just.mashoora.chatbot.GeminiCaller;
import edu.just.mashoora.payload.request.ChatbotQueryRequest;
import edu.just.mashoora.utils.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/adel")
public class ChatbotController {

    @Autowired
    GeminiCaller geminiCaller;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse<String>> customerAccess(@RequestBody ChatbotQueryRequest request) {

        String answer = GeminiCaller.generateContent(request.getQuery());

        StandardResponse<String> response = new StandardResponse<>();
        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setDetailedStatusCode("Query answered successfully");
        response.setData(answer);

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }
}
