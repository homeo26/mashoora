package edu.just.mashoora.controllers;

import edu.just.mashoora.components.Comment;
import edu.just.mashoora.components.Question;
import edu.just.mashoora.payload.request.QuestionRequest;
import edu.just.mashoora.services.QuestionServiceImpl;
import edu.just.mashoora.utils.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionServiceImpl questionService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> postQuestion(@Valid @RequestBody QuestionRequest questionRequest) {
        StandardResponse<String> response = new StandardResponse<>();

        // Create a new question
        Question question = questionService.postQuestion(questionRequest);

        response.setStatus(HttpStatus.CREATED.getReasonPhrase());
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setDetailedStatusCode("Question posted successfully");
        response.setData(question.getTitle());

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @GetMapping("/{questionId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> getQuestionById(@PathVariable Long questionId) {
        StandardResponse<Question> response = new StandardResponse<>();

        // Retrieve the question by ID
        Optional<Question> question = questionService.getQuestionById(questionId);

        // Check if the question exists
        if (question.isPresent()) {
            response.setStatus(HttpStatus.OK.getReasonPhrase());
            response.setStatusCode(HttpStatus.OK.value());
            response.setDetailedStatusCode("Question retrieved successfully");
            response.setData(question.get());
            return ResponseEntity.status(HttpStatus.OK.value()).body(response);

        } else {
            response.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setDetailedStatusCode("No such Question with this ID" + questionId + " was found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);
        }
    }

    @GetMapping("/{questionId}/comments")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllCommentsOfQuestion(@PathVariable Long questionId) {
        StandardResponse<Set<Comment>> response = new StandardResponse<>();

        // Retrieve all comments of the specified question
        Set<Comment> comments = questionService.getAllCommentsOfQuestion(questionId);

        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setDetailedStatusCode("Comments retrieved successfully");
        response.setData(comments);

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);

    }
}