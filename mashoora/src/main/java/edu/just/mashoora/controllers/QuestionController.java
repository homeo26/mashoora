package edu.just.mashoora.controllers;

import edu.just.mashoora.payload.request.QuestionRequest;
import edu.just.mashoora.payload.response.CommentResponse;
import edu.just.mashoora.payload.response.QuestionResponse;
import edu.just.mashoora.services.QuestionServiceImpl;
import edu.just.mashoora.utils.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionServiceImpl questionService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> postQuestion(@Valid @RequestBody QuestionRequest questionRequest) {
        StandardResponse<QuestionResponse> response = new StandardResponse<>();

        // Create a new question
        QuestionResponse questionResponse = questionService.postQuestion(questionRequest);

        response.setStatus(HttpStatus.CREATED.getReasonPhrase());
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setDetailedStatusCode("Question posted successfully");
        response.setData(questionResponse);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @GetMapping("/{questionId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> getQuestionById(@PathVariable Long questionId) {
        StandardResponse<QuestionResponse> response = new StandardResponse<>();

        // Retrieve the question by ID
        QuestionResponse questionResponse = questionService.getQuestionById(questionId);

        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setDetailedStatusCode("Question retrieved successfully");
        response.setData(questionResponse);
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);

    }

    @GetMapping("/{questionId}/comments")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllCommentsOfQuestion(@PathVariable Long questionId) {
        StandardResponse<List<CommentResponse>> response = new StandardResponse<>();

        // Retrieve all comments of the specified question
        List<CommentResponse> comments = questionService.getAllCommentsOfQuestion(questionId);

        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setDetailedStatusCode("Comments retrieved successfully");
        response.setData(comments);

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);

    }

    @GetMapping("pages")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> getQuestionsByPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        StandardResponse<Page<QuestionResponse>> response = new StandardResponse<>();

        // Retrieve all comments of the specified question
        Page<QuestionResponse> questions = questionService.getQuestionsByPage(page, size);

        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setDetailedStatusCode("Page "+ page + " questions retrieved successfully");
        response.setData(questions);

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }
}