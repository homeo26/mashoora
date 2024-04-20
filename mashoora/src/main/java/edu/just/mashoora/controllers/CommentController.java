package edu.just.mashoora.controllers;

import edu.just.mashoora.components.Comment;
import edu.just.mashoora.payload.request.CommentRequest;
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

import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("/{questionId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> postComment(@PathVariable Long questionId, @Valid @RequestBody CommentRequest commentRequest) {
        StandardResponse<String> response = new StandardResponse<>();

        // Create a new comment on the specified question
        Comment comment = commentService.postComment(questionId, commentRequest);

        response.setStatus(HttpStatus.CREATED.getReasonPhrase());
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setDetailedStatusCode("Comment posted successfully on Question " + questionId);
        response.setData(comment.getContent());

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @GetMapping("/{commentId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> getCommentById(@PathVariable Long commentId) {
        StandardResponse<String> response = new StandardResponse<>();

        // Retrieve the comment by ID
        Optional<Comment> comment = commentService.getCommentById(commentId);

        // Check if the comment exists
        if (comment.isPresent()) {
            response.setStatus(HttpStatus.OK.getReasonPhrase());
            response.setStatusCode(HttpStatus.OK.value());
            response.setDetailedStatusCode("Comment retrieved successfully");
            response.setData(comment.get().getContent());
            return ResponseEntity.status(HttpStatus.OK.value()).body(response);

        } else {
            response.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setDetailedStatusCode("No such comment with this ID" + commentId + " was found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response);

        }
    }
}