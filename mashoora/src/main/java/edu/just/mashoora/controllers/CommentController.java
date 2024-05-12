package edu.just.mashoora.controllers;

import edu.just.mashoora.payload.request.CommentRequest;
import edu.just.mashoora.payload.response.CommentResponse;
import edu.just.mashoora.services.CommentServiceImpl;
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

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("/{questionId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> postComment(@PathVariable Long questionId, @Valid @RequestBody CommentRequest commentRequest) {
        StandardResponse<CommentResponse> response = new StandardResponse<>();

        // Create a new comment on the specified question
        CommentResponse commentResponse = commentService.postComment(questionId, commentRequest);

        response.setStatus(HttpStatus.CREATED.getReasonPhrase());
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setDetailedStatusCode("Comment posted successfully on Question " + questionId);
        response.setData(commentResponse);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @GetMapping("/{commentId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCommentById(@PathVariable Long commentId) {
        StandardResponse<CommentResponse> response = new StandardResponse<>();

        // Retrieve the comment by ID
        CommentResponse commentResponse = commentService.getCommentById(commentId);

        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setDetailedStatusCode("Comment retrieved successfully");
        response.setData(commentResponse);
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }
}