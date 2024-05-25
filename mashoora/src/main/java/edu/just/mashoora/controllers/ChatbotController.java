package edu.just.mashoora.controllers;

import edu.just.mashoora.chatbot.ChatbotMessage;
import edu.just.mashoora.payload.request.ChatbotQueryRequest;
import edu.just.mashoora.payload.response.ChatbotQueryResponse;
import edu.just.mashoora.services.ChatbotServiceImpl;
import edu.just.mashoora.services.UserDetailsImpl;
import edu.just.mashoora.utils.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/adel")
public class ChatbotController {

    @Autowired
    private ChatbotServiceImpl chatbotService;

    @PostMapping("/query")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse<?>> sendQuery(@Valid @RequestBody ChatbotQueryRequest request) {
        StandardResponse<ChatbotQueryResponse> response = new StandardResponse<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long senderId = userDetails.getId();

        ChatbotQueryResponse botResponse = chatbotService.sendQuery(senderId, request);
        response.setStatus(HttpStatus.CREATED.getReasonPhrase());
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setDetailedStatusCode("Query Sent successfully");
        response.setData(botResponse);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);

    }

    @GetMapping("/history")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> getChatHistory() {
        StandardResponse<List<ChatbotMessage>> response = new StandardResponse<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long senderId = userDetails.getId();

        List<ChatbotMessage> history = chatbotService.getQueriesBySenderId(senderId);
        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setDetailedStatusCode("History retrieved successfully");
        response.setData(history);

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @DeleteMapping("/history")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteChatHistory() {

        StandardResponse<String> response = new StandardResponse<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long senderId = userDetails.getId();

        chatbotService.deleteBySenderId(senderId);

        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setDetailedStatusCode("History deleted successfully");
        response.setData("Chat history with adel was deleted successfully");

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);

    }
}
