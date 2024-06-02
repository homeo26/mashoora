package edu.just.mashoora.controllers;

import edu.just.mashoora.chatbot.ChatbotMessage;
import edu.just.mashoora.payload.request.ChatbotQueryRequest;
import edu.just.mashoora.payload.response.ChatbotQueryResponse;
import edu.just.mashoora.services.ChatbotService;
import edu.just.mashoora.services.impl.UserDetailsImpl;
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
    private ChatbotService chatbotService;

    @PostMapping("/query")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse<ChatbotQueryResponse>> sendQuery(@Valid @RequestBody ChatbotQueryRequest request) {
        StandardResponse<ChatbotQueryResponse> response = new StandardResponse<>();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long senderId = userDetails.getId();

            ChatbotQueryResponse botResponse = chatbotService.sendQuery(senderId, request);
            response.setStatus("success");
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setDetailedStatusCode("Query Sent successfully");
            response.setData(botResponse);
        } catch (Exception e) {
            response.setStatus("error");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setDetailedStatusCode("Query Failed");
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/history")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse<List<ChatbotMessage>>> getChatHistory() {
        StandardResponse<List<ChatbotMessage>> response = new StandardResponse<>();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long senderId = userDetails.getId();

            List<ChatbotMessage> history = chatbotService.getQueriesBySenderId(senderId);
            response.setStatus("success");
            response.setStatusCode(HttpStatus.OK.value());
            response.setDetailedStatusCode("History retrieved successfully");
            response.setData(history);
        } catch (Exception e) {
            response.setStatus("error");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setDetailedStatusCode("Failed to retrieve history");
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/history")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse<String>> deleteChatHistory() {
        StandardResponse<String> response = new StandardResponse<>();

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long senderId = userDetails.getId();

            chatbotService.deleteBySenderId(senderId);

            response.setStatus("success");
            response.setStatusCode(HttpStatus.OK.value());
            response.setDetailedStatusCode("History deleted successfully");
            response.setData("Chat history with Adel was deleted successfully");
        } catch (Exception e) {
            response.setStatus("error");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setDetailedStatusCode("Failed to delete history");
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
