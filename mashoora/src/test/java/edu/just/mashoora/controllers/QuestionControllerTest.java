package edu.just.mashoora.controllers;

import edu.just.mashoora.payload.request.QuestionRequest;
import edu.just.mashoora.payload.response.CommentResponse;
import edu.just.mashoora.payload.response.QuestionResponse;
import edu.just.mashoora.services.QuestionService;
import edu.just.mashoora.utils.StandardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class QuestionControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostQuestion_Success() {
        QuestionRequest questionRequest = new QuestionRequest();
        QuestionResponse questionResponse = new QuestionResponse();

        when(questionService.postQuestion(any(QuestionRequest.class))).thenReturn(questionResponse);

        ResponseEntity<StandardResponse<QuestionResponse>> responseEntity = questionController.postQuestion(questionRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Question posted successfully", responseEntity.getBody().getDetailedStatusCode());
        assertEquals(questionResponse, responseEntity.getBody().getData());
    }

    @Test
    public void testPostQuestion_Exception() {
        QuestionRequest questionRequest = new QuestionRequest();

        when(questionService.postQuestion(any(QuestionRequest.class))).thenThrow(new RuntimeException("Error"));

        ResponseEntity<StandardResponse<QuestionResponse>> responseEntity = questionController.postQuestion(questionRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("error", responseEntity.getBody().getStatus());
        assertEquals("Failed to post question", responseEntity.getBody().getDetailedStatusCode());
        assertNull(responseEntity.getBody().getData());
    }

    @Test
    public void testGetQuestionById_Success() {
        Long questionId = 1L;
        QuestionResponse questionResponse = new QuestionResponse();

        when(questionService.getQuestionById(anyLong())).thenReturn(questionResponse);

        ResponseEntity<StandardResponse<QuestionResponse>> responseEntity = questionController.getQuestionById(questionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Question retrieved successfully", responseEntity.getBody().getDetailedStatusCode());
        assertEquals(questionResponse, responseEntity.getBody().getData());
    }

    @Test
    public void testGetQuestionById_Exception() {
        Long questionId = 1L;

        when(questionService.getQuestionById(anyLong())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<StandardResponse<QuestionResponse>> responseEntity = questionController.getQuestionById(questionId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("error", responseEntity.getBody().getStatus());
        assertEquals("Question not found", responseEntity.getBody().getDetailedStatusCode());
        assertNull(responseEntity.getBody().getData());
    }

    @Test
    public void testGetAllCommentsOfQuestion_Success() {
        Long questionId = 1L;
        List<CommentResponse> comments = new ArrayList<>();

        when(questionService.getAllCommentsOfQuestion(anyLong())).thenReturn(comments);

        ResponseEntity<StandardResponse<List<CommentResponse>>> responseEntity = questionController.getAllCommentsOfQuestion(questionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Comments retrieved successfully", responseEntity.getBody().getDetailedStatusCode());
        assertEquals(comments, responseEntity.getBody().getData());
    }

    @Test
    public void testGetAllCommentsOfQuestion_Exception() {
        Long questionId = 1L;

        when(questionService.getAllCommentsOfQuestion(anyLong())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<StandardResponse<List<CommentResponse>>> responseEntity = questionController.getAllCommentsOfQuestion(questionId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("error", responseEntity.getBody().getStatus());
        assertEquals("Failed to retrieve comments", responseEntity.getBody().getDetailedStatusCode());
        assertNull(responseEntity.getBody().getData());
    }

    @Test
    public void testGetQuestionsByPage_Success() {
        Page<QuestionResponse> questions = Page.empty();

        when(questionService.getQuestionsByPage(anyInt(), anyInt())).thenReturn(questions);

        ResponseEntity<StandardResponse<Page<QuestionResponse>>> responseEntity = questionController.getQuestionsByPage(1, 10);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Page 1 questions retrieved successfully", responseEntity.getBody().getDetailedStatusCode());
        assertEquals(questions, responseEntity.getBody().getData());
    }

    @Test
    public void testGetQuestionsByPage_Exception() {
        when(questionService.getQuestionsByPage(anyInt(), anyInt())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<StandardResponse<Page<QuestionResponse>>> responseEntity = questionController.getQuestionsByPage(1, 10);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("error", responseEntity.getBody().getStatus());
        assertEquals("Failed to retrieve questions", responseEntity.getBody().getDetailedStatusCode());
        assertEquals(null, responseEntity.getBody().getData());
    }
}
