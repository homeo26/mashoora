package edu.just.mashoora.payload.response;

import edu.just.mashoora.constants.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestResponse {

    private Long id;

    private String title;

    private String body;

    private BigDecimal amount;

    private String customerUsername;

    private String lawyerUsername;

    private PaymentStatus status;
}
