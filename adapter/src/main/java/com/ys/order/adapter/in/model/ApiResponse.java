package com.ys.order.adapter.in.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {

    String status = "success";
    Object data;

    public ApiResponse(Object data) {
        this.data = data;
    }
}
