package edu.just.mashoora.services.impl;

import edu.just.mashoora.components.PaymentRequest;
import edu.just.mashoora.constants.PaymentStatus;
import edu.just.mashoora.models.User;
import edu.just.mashoora.payload.request.PaymentRequestRequest;
import edu.just.mashoora.payload.response.PaymentRequestResponse;
import edu.just.mashoora.repository.PaymentRequestRepository;
import edu.just.mashoora.repository.UserRepository;
import edu.just.mashoora.services.PaymentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentRequestServiceImpl implements PaymentRequestService {

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public PaymentRequestResponse createPaymentRequest(PaymentRequestRequest paymentRequestRequest, String customerUsername) {
        User customer = userRepository.findByUsername(customerUsername).orElseThrow(() -> new RuntimeException("Customer not found"));
        User lawyer = userRepository.findByUsername(paymentRequestRequest.getLawyerUsername()).orElseThrow(() -> new RuntimeException("Lawyer not found"));

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .title(paymentRequestRequest.getTitle())
                .body(paymentRequestRequest.getBody())
                .amount(paymentRequestRequest.getAmount())
                .customer(customer)
                .lawyer(lawyer)
                .status(PaymentStatus.PENDING)
                .build();

        PaymentRequest savedRequest = paymentRequestRepository.save(paymentRequest);
        return toResponsePayload(savedRequest);
    }

    public List<PaymentRequestResponse> getAllPaymentRequests() {
        return paymentRequestRepository.findAll().stream()
                .map(this::toResponsePayload)
                .collect(Collectors.toList());
    }

    public Optional<PaymentRequestResponse> getPaymentRequestById(Long id) {
        return paymentRequestRepository.findById(id)
                .map(this::toResponsePayload);
    }

    public PaymentRequestResponse updatePaymentRequestStatus(Long id, PaymentStatus status) {
        PaymentRequest paymentRequest = paymentRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment request not found"));
        paymentRequest.setStatus(status);
        PaymentRequest updatedRequest = paymentRequestRepository.save(paymentRequest);

        if (status == PaymentStatus.APPROVED) {
            User lawyer = userRepository.findByUsername(updatedRequest.getLawyer().getUsername()).orElseThrow(() -> new RuntimeException("Lawyer not found"));
            updateLawyerBalance(lawyer, updatedRequest.getAmount());
        }

        return toResponsePayload(updatedRequest);
    }

    public List<PaymentRequestResponse> getPaymentRequestsByStatus(PaymentStatus status) {
        return paymentRequestRepository.findByStatus(status).stream()
                .map(this::toResponsePayload)
                .collect(Collectors.toList());
    }

    private PaymentRequestResponse toResponsePayload(PaymentRequest paymentRequest) {
        return PaymentRequestResponse.builder()
                .id(paymentRequest.getId())
                .title(paymentRequest.getTitle())
                .body(paymentRequest.getBody())
                .amount(paymentRequest.getAmount())
                .customerUsername(paymentRequest.getCustomer().getUsername())
                .lawyerUsername(paymentRequest.getLawyer().getUsername())
                .status(paymentRequest.getStatus())
                .build();
    }

    private void updateLawyerBalance(User lawyer, BigDecimal amount) {
        lawyer.setBalance(lawyer.getBalance().add(amount));
        userRepository.save(lawyer);
    }
}
