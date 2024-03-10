package com.just.mashoora.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponse<T> {

    private String status;

    private int statusCode;

    private String detailedStatusCode;

    private T data;

}
