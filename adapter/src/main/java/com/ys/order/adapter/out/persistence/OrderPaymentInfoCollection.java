package com.ys.order.adapter.out.persistence;

import com.ys.order.domain.core.Money;
import com.ys.order.domain.core.PaymentInfo;
import com.ys.order.domain.core.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class OrderPaymentInfoCollection {

    @Column(name = "method", nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentMethod method;
    @Column(name = "amount", nullable = false)
    Integer amount;

    public static OrderPaymentInfoCollection fromDomain(PaymentInfo paymentInfo) {
        return new OrderPaymentInfoCollection(
                paymentInfo.getMethod(),
                paymentInfo.getAmount().getValue()
        );
    }

    public PaymentInfo toDomain() {
        return PaymentInfo.of(this.getMethod(), Money.of(this.amount));
    }
}
