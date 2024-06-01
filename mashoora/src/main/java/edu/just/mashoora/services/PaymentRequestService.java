package edu.just.mashoora.services;

import edu.just.mashoora.constants.PaymentStatus;
import edu.just.mashoora.payload.request.PaymentRequestRequest;
import edu.just.mashoora.payload.response.PaymentRequestResponse;

import java.util.List;
import java.util.Optional;

public interface PaymentRequestService {
    PaymentRequestResponse createPaymentRequest(PaymentRequestRequest paymentRequestRequest, String customerUsername);

    List<PaymentRequestResponse> getAllPaymentRequests();

    Optional<PaymentRequestResponse> getPaymentRequestById(Long id);

    PaymentRequestResponse updatePaymentRequestStatus(Long id, PaymentStatus status);

    List<PaymentRequestResponse> getPaymentRequestsByStatus(PaymentStatus status);

}
