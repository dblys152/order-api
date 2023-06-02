package com.ys.order.adapter.in.model;

import com.ys.order.domain.core.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PreOrderRequest {

    @Valid @NotNull
    private Orderer orderer;
    @Valid @Size(min = 1)
    private List<OrderLineRequest> orderLines;
    @Valid @Size(min = 1)
    private List<PaymentInfoRequest> paymentInfos;
    @Valid @NotNull
    private ShippingInfoRequest shippingInfo;

    @Data
    @NoArgsConstructor
    public static class Orderer {
        private String userId;
        private String name;
        private String phone;
    }

    @Data
    @NoArgsConstructor
    public static class OrderLineRequest {
        private String productOptionId;
        private Integer price;
        private Integer quantity;
    }

    @Data
    @NoArgsConstructor
    public static class PaymentInfoRequest {
        private PaymentMethod method;
        private Integer amount;
    }

    @Data
    @NoArgsConstructor
    public static class ShippingInfoRequest {
        private String receiverName;
        private String receiverPhone;
        private String zipcode;
        private String firstLine;
        private String secondLine;
        private String message;
    }
}
