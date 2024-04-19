package edu.just.mashoora.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StandardResponse<T> {

    private String status;

    private int statusCode;

    private String detailedStatusCode;

    private T data;

}