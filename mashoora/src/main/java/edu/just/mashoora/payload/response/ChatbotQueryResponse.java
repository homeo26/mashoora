package edu.just.mashoora.payload.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatbotQueryResponse {

    @NotBlank
    @Size(max = 10000)
    private String botResponse;

}
