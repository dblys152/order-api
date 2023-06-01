package com.ys.order.domain.core;

import com.ys.order.domain.fixture.SupportedOrderFixture;
import org.junit.jupiter.api.Test;

import static com.ys.order.domain.core.OrderStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest extends SupportedOrderFixture {

    @Test
    void 선_주문을_생성한다() {
        Order preOrder = Order.preOrder(ORDERER, ORDER_LINES, PAYMENT_INFOS, SHIPPING_INFO);

        assertAll(
                () -> assertThat(preOrder.getId()).isNotNull(),
                () -> assertThat(preOrder.getStatus()).isEqualTo(PAYMENT_WAITING),
                () -> assertThat(preOrder.getTotalAmount()).isNotNull(),
                () -> assertThat(preOrder.getOrderLines()).isEqualTo(ORDER_LINES),
                () -> assertThat(preOrder.getPaymentInfos()).isEqualTo(PAYMENT_INFOS),
                () -> assertThat(preOrder.getShippingInfo()).isEqualTo(SHIPPING_INFO)
        );
    }

    @Test
    void 주문을_완료한다() {
        Order preOrder = PRE_ORDER;

        preOrder.complete();

        assertThat(preOrder.getStatus()).isEqualTo(COMPLETED);
    }

    @Test
    void 주문_완료는_결제_대기중_상태에서만_가능하다() {
        Order order = COMPLETED_ORDER;

        assertThatThrownBy(() -> order.complete()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 주문을_취소한다() {
        Order preOrder = PRE_ORDER;
        Order completedOrder = COMPLETED_ORDER;
        Order preparingOrder = PREPARING_ORDER;

        preOrder.cancel();
        completedOrder.cancel();
        preparingOrder.cancel();

        assertThat(preOrder.getStatus()).isEqualTo(CANCELED);
        assertThat(completedOrder.getStatus()).isEqualTo(CANCELED);
        assertThat(preparingOrder.getStatus()).isEqualTo(CANCELED);
    }

    @Test
    void 주문_취소는_출고가_안_된_상태에서_가능하다() {
        Order shippedOrder = SHIPPED_ORDER;
        Order deliveringOrder = DELIVERING_ORDER;

        assertThatThrownBy(() -> shippedOrder.cancel()).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> deliveringOrder.cancel()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 배송지_정보를_수정한다() {
        Receiver receiver = Receiver.of("테스터", "010-4444-5555");
        Address address = Address.of("00700", "서울 성동구", "502호");
        String message = "문앞에 부탁드립니다!";
        Order order = COMPLETED_ORDER;

        order.changeShippingInfo(receiver, address, message);
        ShippingInfo changedShippingInfo = order.getShippingInfo();

        assertAll(
                () -> assertThat(changedShippingInfo.getReceiver()).isEqualTo(receiver),
                () -> assertThat(changedShippingInfo.getAddress()).isEqualTo(address),
                () -> assertThat(changedShippingInfo.getMessage()).isEqualTo(message)
        );
    }

    @Test
    void 배송지_정보는_주문이_완료되고_출고가_안_된_상태에서_가능하다() {
        Order preOrder = PRE_ORDER;
        Order shippedOrder = SHIPPED_ORDER;

        assertThatThrownBy(() -> preOrder.changeShippingInfo(RECEIVER, ADDRESS, MESSAGE))
                .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> shippedOrder.changeShippingInfo(RECEIVER, ADDRESS, MESSAGE))
                .isInstanceOf(IllegalStateException.class);
    }
}