package edu.just.mashoora.controllers;

import edu.just.mashoora.payload.request.QuestionRequest;
import edu.just.mashoora.payload.response.CommentResponse;
import edu.just.mashoora.payload.response.QuestionResponse;
import edu.just.mashoora.services.QuestionService;
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
    private QuestionService questionService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse<QuestionResponse>> postQuestion(@Valid @RequestBody QuestionRequest questionRequest) {
        StandardResponse<QuestionResponse> response = new StandardResponse<>();

        try {
            // Create a new question
            QuestionResponse questionResponse = questionService.postQuestion(questionRequest);

            response.setStatus("success");
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setDetailedStatusCode("Question posted successfully");
            response.setData(questionResponse);
        } catch (Exception e) {
            response.setStatus("error");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setDetailedStatusCode("Failed to post question");
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{questionId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse<QuestionResponse>> getQuestionById(@PathVariable Long questionId) {
        StandardResponse<QuestionResponse> response = new StandardResponse<>();

        try {
            // Retrieve the question by ID
            QuestionResponse questionResponse = questionService.getQuestionById(questionId);

            response.setStatus("success");
            response.setStatusCode(HttpStatus.OK.value());
            response.setDetailedStatusCode("Question retrieved successfully");
            response.setData(questionResponse);
        } catch (Exception e) {
            response.setStatus("error");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setDetailedStatusCode("Question not found");
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{questionId}/comments")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse<List<CommentResponse>>> getAllCommentsOfQuestion(@PathVariable Long questionId) {
        StandardResponse<List<CommentResponse>> response = new StandardResponse<>();

        try {
            // Retrieve all comments of the specified question
            List<CommentResponse> comments = questionService.getAllCommentsOfQuestion(questionId);

            response.setStatus("success");
            response.setStatusCode(HttpStatus.OK.value());
            response.setDetailedStatusCode("Comments retrieved successfully");
            response.setData(comments);
        } catch (Exception e) {
            response.setStatus("error");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setDetailedStatusCode("Failed to retrieve comments");
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/pages")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<StandardResponse<Page<QuestionResponse>>> getQuestionsByPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        StandardResponse<Page<QuestionResponse>> response = new StandardResponse<>();

        try {
            // Retrieve paginated questions
            Page<QuestionResponse> questions = questionService.getQuestionsByPage(page, size);

            response.setStatus("success");
            response.setStatusCode(HttpStatus.OK.value());
            response.setDetailedStatusCode("Page " + page + " questions retrieved successfully");
            response.setData(questions);
        } catch (Exception e) {
            response.setStatus("error");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setDetailedStatusCode("Failed to retrieve questions");
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
