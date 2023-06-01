package com.ys.order.domain.core;

import com.ys.order.domain.fixture.SupportedOrderFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderLineTest extends SupportedOrderFixture {

    @Test
    void 주문_상품_총액을_가져온다() {
        OrderLine orderLine = OrderLine.of(
                PRODUCT_OPTION_ID, Money.of(1000), 2);
        assertThat(orderLine.getAmount().getValue()).isEqualTo(1000 * 2);
    }
}