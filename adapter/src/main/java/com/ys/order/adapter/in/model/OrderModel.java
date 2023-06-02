package com.ys.order.adapter.in.model;

import com.ys.order.domain.core.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class OrderModel {

    String id;
    OrdererModel orderer;
    OrderStatus status;
    int totalAmount;
    List<OrderLineModel> orderLines;
    List<PaymentInfoModel> paymentInfos;
    ShippingInfoModel shippingInfo;

    public static OrderModel fromDomain(Order order) {
        OrderId orderId = order.getId();
        Orderer orderer = order.getOrderer();
        OrderLines orderLines = order.getOrderLines();
        PaymentInfos paymentInfos = order.getPaymentInfos();
        ShippingInfo shippingInfo = order.getShippingInfo();
        Receiver receiver = shippingInfo.getReceiver();
        Address address = shippingInfo.getAddress();
        return new OrderModel(
                orderId.getId(),
                new OrdererModel(orderer.getUserId().getId(), orderer.getName(), orderer.getPhone()),
                order.getStatus(),
                order.getTotalAmount().getValue(),
                orderLines.getItems().stream()
                        .map(ol -> new OrderLineModel(
                                ol.getProductOptionId().getId(),
                                ol.getPrice().getValue(),
                                ol.getQuantity()
                        ))
                        .collect(Collectors.toList()),
                paymentInfos.getItems().stream()
                        .map(p -> new PaymentInfoModel(p.getMethod(), p.getAmount().getValue()))
                        .collect(Collectors.toList()),
                new ShippingInfoModel(
                        receiver.getName(),
                        receiver.getPhone(),
                        address.getZipcode(),
                        address.getFirstLine(),
                        address.getSecondLine(),
                        shippingInfo.getMessage())
        );
    }

    @Data
    @AllArgsConstructor
    public static class OrdererModel {
        private String userId;
        private String name;
        private String phone;
    }

    @Data
    @AllArgsConstructor
    public static class OrderLineModel {
        private String productOptionId;
        private Integer price;
        private Integer quantity;
    }

    @Data
    @AllArgsConstructor
    public static class PaymentInfoModel {
        private PaymentMethod method;
        private Integer amount;
    }

    @Data
    @AllArgsConstructor
    public static class ShippingInfoModel {
        private String receiverName;
        private String receiverPhone;
        private String zipcode;
        private String firstLine;
        private String secondLine;
        private String message;
    }
}
