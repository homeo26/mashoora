package edu.just.mashoora.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDepositRequest {

    private String body;

    @NotNull
    @PositiveOrZero
    private BigDecimal amount;

    private String lawyerUsername;
}
