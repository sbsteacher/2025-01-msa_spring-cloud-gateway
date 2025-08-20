package com.green.scg2.configuration.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResultResponse<T> {
    private String message;
    private T result;
}
