package com.ys.order.domain.core;

import com.ys.order.domain.fixture.SupportedOrderFixture;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderLinesTest extends SupportedOrderFixture {

    @Test
    void 모든_주문_상품_총액을_가져온다() {
        OrderLine orderLine = OrderLine.of(
                PRODUCT_OPTION_ID, Money.of(1000), 2);
        OrderLine orderLine2 = OrderLine.of(
                PRODUCT_OPTION_ID_2, Money.of(1700), 1);
        OrderLine orderLine3 = OrderLine.of(
                PRODUCT_OPTION_ID_3, Money.of(2500), 3);
        OrderLines orderLines = OrderLines.of(new ArrayList<>(Arrays.asList(orderLine, orderLine2, orderLine3)));
        Money totalAmount = orderLines.calculateTotalAmount();
        assertThat(totalAmount.getValue()).isEqualTo(
                (1000 * 2) + (1700 * 1) + (2500 * 3));
    }
}