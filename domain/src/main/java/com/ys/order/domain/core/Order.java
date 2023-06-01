package com.ys.order.domain.core;

import com.fasterxml.uuid.Generators;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

import static com.ys.order.domain.core.OrderStatus.*;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Order implements OrderService {

    @NotNull
    private OrderId id;
    @NotNull
    private Orderer orderer;
    @NotNull
    private OrderStatus status;
    @NotNull
    private Money totalAmount;
    @NotNull
    private OrderLines orderLines;
    @NotNull
    private PaymentInfos paymentInfos;
    @NotNull
    private ShippingInfo shippingInfo;

    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    private Long version;

    private Order(
            OrderId id,
            Orderer orderer,
            OrderStatus status,
            Money totalAmount,
            OrderLines orderLines,
            PaymentInfos paymentInfos,
            ShippingInfo shippingInfo
    ) {
        this.id = id;
        this.orderer = orderer;
        this.status = status;
        this.totalAmount = totalAmount;
        this.orderLines = orderLines;
        this.paymentInfos = paymentInfos;
        this.shippingInfo = shippingInfo;
    }

    public static Order preOrder(Orderer orderer, OrderLines orderLines, PaymentInfos paymentInfos, ShippingInfo shippingInfo) {
        OrderId id = OrderId.of(Generators.timeBasedEpochGenerator().generate().toString());
        Money totalAmount = orderLines.calculateTotalAmount();
        return new Order(
                id, orderer, PAYMENT_WAITING, totalAmount, orderLines, paymentInfos, shippingInfo);
    }

    public static Order of(
            OrderId id,
            Orderer orderer,
            OrderStatus status,
            Money totalAmount,
            OrderLines orderLines,
            PaymentInfos paymentInfos,
            ShippingInfo shippingInfo,
            LocalDateTime created_at,
            LocalDateTime modified_at,
            Long version
    ) {
        return new Order(
                id, orderer, status, totalAmount, orderLines, paymentInfos, shippingInfo, created_at, modified_at, version);
    }

    @Override
    public void complete() {
        if (this.status != PAYMENT_WAITING) {
            throw new IllegalStateException("결제 대기중 상태에서만 완료 처리 가능합니다.");
        }
        this.status = COMPLETED;
    }

    @Override
    public void cancel() {
        if (!isStateOfCancellation()) {
            throw new IllegalStateException("현재 취소가 가능한 상태가 아닙니다.");
        }
        this.status = CANCELED;
    }

    private boolean isStateOfCancellation() {
        return this.status == PAYMENT_WAITING
                || this.status == COMPLETED
                || this.status == PREPARING;
    }

    @Override
    public void changeShippingInfo(Receiver receiver, Address address, String message) {
        if (!isStateOfModifiableShippingInfo()) {
            throw new IllegalStateException("현재 배송정보 수정이 가능한 상태가 아닙니다.");
        }
        this.shippingInfo = ShippingInfo.of(receiver, address, message);
    }

    private boolean isStateOfModifiableShippingInfo() {
        return this.status == COMPLETED || this.status == PREPARING;
    }
}
