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
public class LawyerListingResponse {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private List<String> topLawFields;
    private LawFieldRate lawFieldRate;
}
