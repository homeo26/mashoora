package edu.just.mashoora.components;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LawFieldRate {
    private String field;
    private float rate;
    private int Count;
}
