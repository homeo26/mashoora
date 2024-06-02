package edu.just.mashoora.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingLawersResponse {
    private String firstName;
    private String lastName;
    private Long id;
    private String username;

}
