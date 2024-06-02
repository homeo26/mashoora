package edu.just.mashoora.controllers;

import edu.just.mashoora.constants.PaymentStatus;
import edu.just.mashoora.payload.request.PaymentDepositRequest;
import edu.just.mashoora.payload.request.PaymentRequestRequest;
import edu.just.mashoora.payload.response.PaymentRequestResponse;
import edu.just.mashoora.repository.UserRepository;
import edu.just.mashoora.services.PaymentRequestService;
import edu.just.mashoora.services.impl.UserDetailsServiceImpl;
import edu.just.mashoora.utils.StandardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentRequestControllerTest {

    @Mock
    private PaymentRequestService paymentRequestService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @InjectMocks
    private PaymentRequestController paymentRequestController;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(new SecurityContextImpl());
        when(principal.getName()).thenReturn("testUser");
    }

    @Test
    void createPaymentRequest_Success() {
        PaymentRequestRequest request = new PaymentRequestRequest();
        PaymentRequestResponse response = new PaymentRequestResponse();
        when(paymentRequestService.createPaymentRequest(eq(request), eq("testUser"))).thenReturn(response);

        StandardResponse<PaymentRequestResponse> result = paymentRequestController.createPaymentRequest(request, principal);

        assertEquals("success", result.getStatus());
        assertEquals(HttpStatus.CREATED.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).createPaymentRequest(eq(request), eq("testUser"));
    }

    @Test
    void createPaymentRequest_Failure() {
        PaymentRequestRequest request = new PaymentRequestRequest();
        when(paymentRequestService.createPaymentRequest(eq(request), eq("testUser"))).thenThrow(new RuntimeException());

        StandardResponse<PaymentRequestResponse> result = paymentRequestController.createPaymentRequest(request, principal);

        assertEquals("error", result.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).createPaymentRequest(eq(request), eq("testUser"));
    }

    @Test
    void getAllPaymentRequests_Success() {
        List<PaymentRequestResponse> responseList = Collections.singletonList(new PaymentRequestResponse());
        when(paymentRequestService.getAllPaymentRequests()).thenReturn(responseList);

        StandardResponse<List<PaymentRequestResponse>> result = paymentRequestController.getAllPaymentRequests();

        assertEquals("success", result.getStatus());
        assertEquals(HttpStatus.OK.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).getAllPaymentRequests();
    }

    @Test
    void getAllPaymentRequests_Failure() {
        when(paymentRequestService.getAllPaymentRequests()).thenThrow(new RuntimeException());

        StandardResponse<List<PaymentRequestResponse>> result = paymentRequestController.getAllPaymentRequests();

        assertEquals("error", result.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).getAllPaymentRequests();
    }

    @Test
    void getPaymentRequestById_Success() {
        PaymentRequestResponse response = new PaymentRequestResponse();
        when(paymentRequestService.getPaymentRequestById(1L)).thenReturn(Optional.of(response));

        StandardResponse<PaymentRequestResponse> result = paymentRequestController.getPaymentRequestById(1L);

        assertEquals("success", result.getStatus());
        assertEquals(HttpStatus.OK.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).getPaymentRequestById(1L);
    }

    @Test
    void getPaymentRequestById_Failure() {
        when(paymentRequestService.getPaymentRequestById(1L)).thenReturn(Optional.empty());

        StandardResponse<PaymentRequestResponse> result = paymentRequestController.getPaymentRequestById(1L);

        assertEquals("error", result.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).getPaymentRequestById(1L);
    }

    @Test
    void approvePaymentRequest_Success() {
        PaymentRequestResponse response = new PaymentRequestResponse();
        when(paymentRequestService.updatePaymentRequestStatus(1L, PaymentStatus.APPROVED)).thenReturn(response);

        StandardResponse<PaymentRequestResponse> result = paymentRequestController.approvePaymentRequest(1L);

        assertEquals("success", result.getStatus());
        assertEquals(HttpStatus.OK.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).updatePaymentRequestStatus(1L, PaymentStatus.APPROVED);
    }

    @Test
    void approvePaymentRequest_Failure() {
        when(paymentRequestService.updatePaymentRequestStatus(1L, PaymentStatus.APPROVED)).thenThrow(new RuntimeException());

        StandardResponse<PaymentRequestResponse> result = paymentRequestController.approvePaymentRequest(1L);

        assertEquals("error", result.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).updatePaymentRequestStatus(1L, PaymentStatus.APPROVED);
    }

    @Test
    void declinePaymentRequest_Success() {
        PaymentRequestResponse response = new PaymentRequestResponse();
        when(paymentRequestService.updatePaymentRequestStatus(1L, PaymentStatus.DECLINED)).thenReturn(response);

        StandardResponse<PaymentRequestResponse> result = paymentRequestController.declinePaymentRequest(1L);

        assertEquals("success", result.getStatus());
        assertEquals(HttpStatus.OK.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).updatePaymentRequestStatus(1L, PaymentStatus.DECLINED);
    }

    @Test
    void declinePaymentRequest_Failure() {
        when(paymentRequestService.updatePaymentRequestStatus(1L, PaymentStatus.DECLINED)).thenThrow(new RuntimeException());

        StandardResponse<PaymentRequestResponse> result = paymentRequestController.declinePaymentRequest(1L);

        assertEquals("error", result.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).updatePaymentRequestStatus(1L, PaymentStatus.DECLINED);
    }

    @Test
    void getPendingPaymentRequests_Success() {
        List<PaymentRequestResponse> responseList = Collections.singletonList(new PaymentRequestResponse());
        when(paymentRequestService.getPaymentRequestsByStatus(PaymentStatus.PENDING)).thenReturn(responseList);

        StandardResponse<List<PaymentRequestResponse>> result = paymentRequestController.getPendingPaymentRequests();

        assertEquals("success", result.getStatus());
        assertEquals(HttpStatus.OK.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).getPaymentRequestsByStatus(PaymentStatus.PENDING);
    }

    @Test
    void getPendingPaymentRequests_Failure() {
        when(paymentRequestService.getPaymentRequestsByStatus(PaymentStatus.PENDING)).thenThrow(new RuntimeException());

        StandardResponse<List<PaymentRequestResponse>> result = paymentRequestController.getPendingPaymentRequests();

        assertEquals("error", result.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).getPaymentRequestsByStatus(PaymentStatus.PENDING);
    }

    @Test
    void getApprovedPaymentRequests_Success() {
        List<PaymentRequestResponse> responseList = Collections.singletonList(new PaymentRequestResponse());
        when(paymentRequestService.getPaymentRequestsByStatus(PaymentStatus.APPROVED)).thenReturn(responseList);

        StandardResponse<List<PaymentRequestResponse>> result = paymentRequestController.getApprovedPaymentRequests();

        assertEquals("success", result.getStatus());
        assertEquals(HttpStatus.OK.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).getPaymentRequestsByStatus(PaymentStatus.APPROVED);
    }

    @Test
    void getApprovedPaymentRequests_Failure() {
        when(paymentRequestService.getPaymentRequestsByStatus(PaymentStatus.APPROVED)).thenThrow(new RuntimeException());

        StandardResponse<List<PaymentRequestResponse>> result = paymentRequestController.getApprovedPaymentRequests();

        assertEquals("error", result.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).getPaymentRequestsByStatus(PaymentStatus.APPROVED);
    }

    @Test
    void getDeclinedPaymentRequests_Success() {
        List<PaymentRequestResponse> responseList = Collections.singletonList(new PaymentRequestResponse());
        when(paymentRequestService.getPaymentRequestsByStatus(PaymentStatus.DECLINED)).thenReturn(responseList);

        StandardResponse<List<PaymentRequestResponse>> result = paymentRequestController.getDeclinedPaymentRequests();

        assertEquals("success", result.getStatus());
        assertEquals(HttpStatus.OK.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).getPaymentRequestsByStatus(PaymentStatus.DECLINED);
    }

    @Test
    void getDeclinedPaymentRequests_Failure() {
        when(paymentRequestService.getPaymentRequestsByStatus(PaymentStatus.DECLINED)).thenThrow(new RuntimeException());

        StandardResponse<List<PaymentRequestResponse>> result = paymentRequestController.getDeclinedPaymentRequests();

        assertEquals("error", result.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).getPaymentRequestsByStatus(PaymentStatus.DECLINED);
    }

    @Test
    void getUserBalanceByUsername_Success() {
        BigDecimal balance = BigDecimal.valueOf(100);
        when(userDetailsServiceImpl.getBalanceByUsername("testUser")).thenReturn(balance);

        StandardResponse<BigDecimal> result = paymentRequestController.getUserBalanceByUsername(principal);

        assertEquals("success", result.getStatus());
        assertEquals(HttpStatus.OK.value(), result.getStatusCode());
        verify(userDetailsServiceImpl, times(1)).getBalanceByUsername("testUser");
    }

    @Test
    void getUserBalanceByUsername_Failure() {
        when(userDetailsServiceImpl.getBalanceByUsername("testUser")).thenThrow(new RuntimeException());

        StandardResponse<BigDecimal> result = paymentRequestController.getUserBalanceByUsername(principal);

        assertEquals("error", result.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode());
        verify(userDetailsServiceImpl, times(1)).getBalanceByUsername("testUser");
    }

    @Test
    void depositBalanceToLawyer_Success() throws IllegalAccessException {
        PaymentDepositRequest request = new PaymentDepositRequest();
        request.setLawyerUsername("lawyer");
        request.setAmount(BigDecimal.valueOf(50));

        doNothing().when(paymentRequestService).depositBalanceToLawyer("testUser", "lawyer", BigDecimal.valueOf(50));

        StandardResponse<String> result = paymentRequestController.depositBalanceToLawyer(request, principal);

        assertEquals("success", result.getStatus());
        assertEquals(HttpStatus.OK.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).depositBalanceToLawyer("testUser", "lawyer", BigDecimal.valueOf(50));
    }

    @Test
    void depositBalanceToLawyer_InsufficientBalance() throws IllegalAccessException {
        PaymentDepositRequest request = new PaymentDepositRequest();
        request.setLawyerUsername("lawyer");
        request.setAmount(BigDecimal.valueOf(50));

        doThrow(new IllegalAccessException()).when(paymentRequestService).depositBalanceToLawyer("testUser", "lawyer", BigDecimal.valueOf(50));

        StandardResponse<String> result = paymentRequestController.depositBalanceToLawyer(request, principal);

        assertEquals("Insufficient Balance", result.getStatus());
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).depositBalanceToLawyer("testUser", "lawyer", BigDecimal.valueOf(50));
    }

    @Test
    void depositBalanceToLawyer_Failure() throws IllegalAccessException {
        PaymentDepositRequest request = new PaymentDepositRequest();
        request.setLawyerUsername("lawyer");
        request.setAmount(BigDecimal.valueOf(50));

        doThrow(new RuntimeException()).when(paymentRequestService).depositBalanceToLawyer("testUser", "lawyer", BigDecimal.valueOf(50));

        StandardResponse<String> result = paymentRequestController.depositBalanceToLawyer(request, principal);

        assertEquals("error", result.getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getStatusCode());
        verify(paymentRequestService, times(1)).depositBalanceToLawyer("testUser", "lawyer", BigDecimal.valueOf(50));
    }
}
