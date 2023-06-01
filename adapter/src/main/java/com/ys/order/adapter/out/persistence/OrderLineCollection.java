package com.ys.order.adapter.out.persistence;


import com.refs.productoption.domain.ProductOptionId;
import com.ys.order.domain.core.Money;
import com.ys.order.domain.core.OrderLine;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class OrderLineCollection {

    @Column(name = "product_option_id", nullable = false)
    String productOptionId;
    @Column(name = "price", nullable = false)
    Integer price;
    @Column(name = "quantity", nullable = false)
    Integer quantity;

    public static OrderLineCollection fromDomain(OrderLine orderLine) {
        return new OrderLineCollection(
                orderLine.getProductOptionId().getId(),
                orderLine.getPrice().getValue(),
                orderLine.getQuantity()
        );
    }

    public OrderLine toDomain() {
        return OrderLine.of(
                ProductOptionId.of(this.productOptionId),
                Money.of(this.price),
                this.quantity
        );
    }
}
