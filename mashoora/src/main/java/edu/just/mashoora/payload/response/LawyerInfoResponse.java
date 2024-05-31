package edu.just.mashoora.payload.response;

import edu.just.mashoora.components.LawFieldRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LawyerInfoResponse {
    private Long lawyerId;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    List<LawFieldRate> lawFieldsDetails;
}
