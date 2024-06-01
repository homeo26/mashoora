package edu.just.mashoora.services;

import edu.just.mashoora.constants.PaymentStatus;
import edu.just.mashoora.models.User;
import edu.just.mashoora.payload.request.PaymentRequestRequest;
import edu.just.mashoora.payload.response.PaymentRequestResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentRequestService {
    PaymentRequestResponse createPaymentRequest(PaymentRequestRequest paymentRequestRequest, String customerUsername);

    List<PaymentRequestResponse> getAllPaymentRequests();

    Optional<PaymentRequestResponse> getPaymentRequestById(Long id);

    PaymentRequestResponse updatePaymentRequestStatus(Long id, PaymentStatus status);

    List<PaymentRequestResponse> getPaymentRequestsByStatus(PaymentStatus status);

    void addUpUserBalance(User user, BigDecimal amount);

    void subtractUserBalance(User user, BigDecimal amount) throws IllegalAccessException;

    void depositBalanceToLawyer(String customerUsername, String lawyerUsername, BigDecimal amount) throws IllegalAccessException;
}
