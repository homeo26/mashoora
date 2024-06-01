package edu.just.mashoora.payload.request;


import jakarta.validation.constraints.NotBlank;
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
public class PaymentRequestRequest {

    private String title;

    private String body;

    @NotNull
    @PositiveOrZero
    private BigDecimal amount;

    @NotBlank
    @NotNull
    private String lawyerUsername;
}
