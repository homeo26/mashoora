package edu.just.mashoora.controllers;


import edu.just.mashoora.constants.PaymentStatus;
import edu.just.mashoora.payload.request.PaymentDepositRequest;
import edu.just.mashoora.payload.request.PaymentRequestRequest;
import edu.just.mashoora.payload.response.PaymentRequestResponse;
import edu.just.mashoora.repository.UserRepository;
import edu.just.mashoora.services.PaymentRequestService;
import edu.just.mashoora.services.impl.UserDetailsServiceImpl;
import edu.just.mashoora.utils.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentRequestController {

    @Autowired
    private PaymentRequestService paymentRequestService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public StandardResponse<PaymentRequestResponse> createPaymentRequest(@RequestBody PaymentRequestRequest paymentRequestRequest, Principal principal) {
        try {
            PaymentRequestResponse createdRequest = paymentRequestService.createPaymentRequest(paymentRequestRequest, principal.getName());
            return StandardResponse.<PaymentRequestResponse>builder()
                    .status("success")
                    .statusCode(HttpStatus.CREATED.value())
                    .detailedStatusCode("Payment request created successfully")
                    .data(createdRequest)
                    .build();
        } catch (Exception e) {
            return StandardResponse.<PaymentRequestResponse>builder()
                    .status("error")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .detailedStatusCode("Failed to create payment request")
                    .data(null)
                    .build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<List<PaymentRequestResponse>> getAllPaymentRequests() {
        try {
            List<PaymentRequestResponse> paymentRequests = paymentRequestService.getAllPaymentRequests();
            return StandardResponse.<List<PaymentRequestResponse>>builder()
                    .status("success")
                    .statusCode(HttpStatus.OK.value())
                    .detailedStatusCode("Payment requests fetched successfully")
                    .data(paymentRequests)
                    .build();
        } catch (Exception e) {
            return StandardResponse.<List<PaymentRequestResponse>>builder()
                    .status("error")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .detailedStatusCode("Failed to fetch payment requests")
                    .data(null)
                    .build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<PaymentRequestResponse> getPaymentRequestById(@PathVariable Long id) {
        try {
            PaymentRequestResponse paymentRequest = paymentRequestService.getPaymentRequestById(id).orElseThrow(() -> new RuntimeException("Payment request not found"));
            return StandardResponse.<PaymentRequestResponse>builder()
                    .status("success")
                    .statusCode(HttpStatus.OK.value())
                    .detailedStatusCode("Payment request fetched successfully")
                    .data(paymentRequest)
                    .build();
        } catch (Exception e) {
            return StandardResponse.<PaymentRequestResponse>builder()
                    .status("error")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .detailedStatusCode("Failed to fetch payment request")
                    .data(null)
                    .build();
        }
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<PaymentRequestResponse> approvePaymentRequest(@PathVariable Long id) {
        try {
            PaymentRequestResponse approvedRequest = paymentRequestService.updatePaymentRequestStatus(id, PaymentStatus.APPROVED);
            return StandardResponse.<PaymentRequestResponse>builder()
                    .status("success")
                    .statusCode(HttpStatus.OK.value())
                    .detailedStatusCode("Payment request approved successfully")
                    .data(approvedRequest)
                    .build();
        } catch (Exception e) {
            return StandardResponse.<PaymentRequestResponse>builder()
                    .status("error")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .detailedStatusCode("Failed to approve payment request")
                    .data(null)
                    .build();
        }
    }

    @PutMapping("/{id}/decline")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<PaymentRequestResponse> declinePaymentRequest(@PathVariable Long id) {
        try {
            PaymentRequestResponse declinedRequest = paymentRequestService.updatePaymentRequestStatus(id, PaymentStatus.DECLINED);
            return StandardResponse.<PaymentRequestResponse>builder()
                    .status("success")
                    .statusCode(HttpStatus.OK.value())
                    .detailedStatusCode("Payment request declined successfully")
                    .data(declinedRequest)
                    .build();
        } catch (Exception e) {
            return StandardResponse.<PaymentRequestResponse>builder()
                    .status("error")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .detailedStatusCode("Failed to decline payment request")
                    .data(null)
                    .build();
        }
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<List<PaymentRequestResponse>> getPendingPaymentRequests() {
        try {
            List<PaymentRequestResponse> pendingRequests = paymentRequestService.getPaymentRequestsByStatus(PaymentStatus.PENDING);
            return StandardResponse.<List<PaymentRequestResponse>>builder()
                    .status("success")
                    .statusCode(HttpStatus.OK.value())
                    .detailedStatusCode("Pending payment requests fetched successfully")
                    .data(pendingRequests)
                    .build();
        } catch (Exception e) {
            return StandardResponse.<List<PaymentRequestResponse>>builder()
                    .status("error")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .detailedStatusCode("Failed to fetch pending payment requests")
                    .data(null)
                    .build();
        }
    }

    @GetMapping("/approved")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<List<PaymentRequestResponse>> getApprovedPaymentRequests() {
        try {
            List<PaymentRequestResponse> approvedRequests = paymentRequestService.getPaymentRequestsByStatus(PaymentStatus.APPROVED);
            return StandardResponse.<List<PaymentRequestResponse>>builder()
                    .status("success")
                    .statusCode(HttpStatus.OK.value())
                    .detailedStatusCode("Approved payment requests fetched successfully")
                    .data(approvedRequests)
                    .build();
        } catch (Exception e) {
            return StandardResponse.<List<PaymentRequestResponse>>builder()
                    .status("error")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .detailedStatusCode("Failed to fetch approved payment requests")
                    .data(null)
                    .build();
        }
    }

    @GetMapping("/declined")
    @PreAuthorize("hasRole('ADMIN')")
    public StandardResponse<List<PaymentRequestResponse>> getDeclinedPaymentRequests() {
        try {
            List<PaymentRequestResponse> declinedRequests = paymentRequestService.getPaymentRequestsByStatus(PaymentStatus.DECLINED);
            return StandardResponse.<List<PaymentRequestResponse>>builder()
                    .status("success")
                    .statusCode(HttpStatus.OK.value())
                    .detailedStatusCode("Declined payment requests fetched successfully")
                    .data(declinedRequests)
                    .build();
        } catch (Exception e) {
            return StandardResponse.<List<PaymentRequestResponse>>builder()
                    .status("error")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .detailedStatusCode("Failed to fetch declined payment requests")
                    .data(null)
                    .build();
        }
    }

    @GetMapping("/balance")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('LAWYER')")
    public StandardResponse<BigDecimal> getUserBalanceByUsername(Principal principal) {
        try {
            BigDecimal userBalance = userDetailsServiceImpl.getBalanceByUsername(principal.getName());
            return StandardResponse.<BigDecimal>builder()
                    .status("success")
                    .statusCode(HttpStatus.OK.value())
                    .detailedStatusCode("balance fetched successfully")
                    .data(userBalance)
                    .build();
        } catch (Exception e) {
            return StandardResponse.<BigDecimal>builder()
                    .status("error")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .detailedStatusCode("Failed to fetch user balance")
                    .data(null)
                    .build();
        }
    }

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('CUSTOMER')")
    public StandardResponse<String> depositBalanceToLawyer(@RequestBody PaymentDepositRequest paymentDepositRequest, Principal principal) {
        try {
            paymentRequestService.depositBalanceToLawyer(principal.getName(), paymentDepositRequest.getLawyerUsername(), paymentDepositRequest.getAmount());
            return StandardResponse.<String>builder()
                    .status("success")
                    .statusCode(HttpStatus.OK.value())
                    .detailedStatusCode("Deposited amount " + paymentDepositRequest.getAmount() + " successfully to " + paymentDepositRequest.getLawyerUsername())
                    .data(null)
                    .build();
        } catch (IllegalAccessException e) {
            return StandardResponse.<String>builder()
                    .status("Insufficient Balance")
                    .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
                    .detailedStatusCode("Failed to deposit payment request due to insufficient balance")
                    .data(null)
                    .build();
        } catch (Exception e) {
            return StandardResponse.<String>builder()
                    .status("error")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .detailedStatusCode("Failed to deposit amount to lawyer for unknown reason")
                    .data(null)
                    .build();
        }
    }
}
