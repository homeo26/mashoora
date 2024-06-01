package edu.just.mashoora.repository;

import edu.just.mashoora.components.PaymentRequest;
import edu.just.mashoora.constants.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {

    List<PaymentRequest> findByStatus(PaymentStatus status);
}
