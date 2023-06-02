package com.ys.order.adapter.in.model;

import lombok.Data;

@Data
public class ApiErrorResponse {

    String status = "error";
    String message;

    public ApiErrorResponse(String message) {
        this.message = message;
    }
}
