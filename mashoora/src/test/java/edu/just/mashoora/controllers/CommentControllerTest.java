package edu.just.mashoora.controllers;

import edu.just.mashoora.components.Comment;
import edu.just.mashoora.models.User;
import edu.just.mashoora.payload.request.CommentRequest;
import edu.just.mashoora.payload.response.CommentResponse;
import edu.just.mashoora.repository.UserRepository;
import edu.just.mashoora.services.CommentService;
import edu.just.mashoora.utils.StandardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testPostComment_Success() {
        Long questionId = 1L;
        CommentRequest commentRequest = new CommentRequest();
        CommentResponse commentResponse = new CommentResponse();

        when(commentService.postComment(anyLong(), any(CommentRequest.class))).thenReturn(commentResponse);

        ResponseEntity<StandardResponse<CommentResponse>> responseEntity = commentController.postComment(questionId, commentRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Comment posted successfully on Question " + questionId, responseEntity.getBody().getDetailedStatusCode());
        assertEquals(commentResponse, responseEntity.getBody().getData());
    }

    @Test
    public void testPostComment_Exception() {
        Long questionId = 1L;
        CommentRequest commentRequest = new CommentRequest();

        when(commentService.postComment(anyLong(), any(CommentRequest.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<StandardResponse<CommentResponse>> responseEntity = commentController.postComment(questionId, commentRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("error", responseEntity.getBody().getStatus());
        assertEquals("Failed to post comment on Question " + questionId, responseEntity.getBody().getDetailedStatusCode());
        assertEquals(null, responseEntity.getBody().getData());
    }

    @Test
    public void testVoteOnComment_Success() {
        Long commentId = 1L;
        boolean commentVote = true;
        User user = new User();
        Comment comment = new Comment();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("username");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(commentService.getCommentById(anyLong())).thenReturn(comment);

        ResponseEntity<StandardResponse<String>> responseEntity = commentController.voteOnComment(commentId, commentVote);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Vote updated successfully", responseEntity.getBody().getDetailedStatusCode());
        assertEquals("Vote updated successfully", responseEntity.getBody().getData());
    }

    @Test
    public void testVoteOnComment_UserNotFound() {
        Long commentId = 1L;
        boolean commentVote = true;

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("username");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        ResponseEntity<StandardResponse<String>> responseEntity = commentController.voteOnComment(commentId, commentVote);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("error", responseEntity.getBody().getStatus());
        assertEquals("User not found with username: username", responseEntity.getBody().getDetailedStatusCode());
        assertEquals(null, responseEntity.getBody().getData());
    }

    @Test
    public void testVoteOnComment_CommentNotFound() {
        Long commentId = 1L;
        boolean commentVote = true;
        User user = new User();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("username");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(commentService.getCommentById(anyLong())).thenThrow(new NoSuchElementException("Comment not found"));

        ResponseEntity<StandardResponse<String>> responseEntity = commentController.voteOnComment(commentId, commentVote);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("error", responseEntity.getBody().getStatus());
        assertEquals("Comment not found", responseEntity.getBody().getDetailedStatusCode());
        assertEquals(null, responseEntity.getBody().getData());
    }

    @Test
    public void testVoteOnComment_Exception() {
        Long commentId = 1L;
        boolean commentVote = true;
        User user = new User();
        Comment comment = new Comment();

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("username");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(commentService.getCommentById(anyLong())).thenReturn(comment);
        when(commentService.VoteOnComment(any(User.class), any(Comment.class), any(Boolean.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<StandardResponse<String>> responseEntity = commentController.voteOnComment(commentId, commentVote);

        assertEquals(HttpStatus.EXPECTATION_FAILED, responseEntity.getStatusCode());
        assertEquals("error", responseEntity.getBody().getStatus());
        assertEquals("Failed to update vote", responseEntity.getBody().getDetailedStatusCode());
        assertEquals(null, responseEntity.getBody().getData());
    }
}
