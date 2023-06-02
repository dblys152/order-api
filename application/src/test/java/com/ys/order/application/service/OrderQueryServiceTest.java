package com.ys.order.application.service;

import com.ys.order.application.port.out.LoadOrderPort;
import com.ys.order.application.service.exception.OrderNotFoundException;
import com.ys.order.application.service.fixture.SupportedOrderFixture;
import com.ys.order.domain.core.Order;
import com.ys.order.domain.core.OrderId;
import com.ys.order.domain.core.Orders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderQueryServiceTest extends SupportedOrderFixture {

    @InjectMocks
    private OrderQueryService sut;

    @Mock
    private LoadOrderPort loadOrderPort;

    @Test
    void 단건_주문을_조회한다() {
        given(loadOrderPort.findById(ORDER_ID)).willReturn(COMPLETED_ORDER);

        Order actual = loadOrderPort.findById(ORDER_ID);

        assertThat(actual).isNotNull();
    }

    @Test
    void 주문건이_없으면_에러를_반환한다() {
        OrderId NO_ORDER_ID = OrderId.of("NO_ORDER_ID");
        given(loadOrderPort.findById(NO_ORDER_ID)).willThrow(OrderNotFoundException.class);

        assertThatThrownBy(() -> loadOrderPort.findById(NO_ORDER_ID)).isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void 유저의_모든_주문_내역을_조회한다() {
        Orders orders = Orders.of(new ArrayList<>(Arrays.asList(
            COMPLETED_ORDER, SHIPPED_ORDER)));
        given(loadOrderPort.findAllByOrdererUserId(USER_ID)).willReturn(orders);

        Orders actual = sut.getOrders(USER_ID);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getItems().size()).isEqualTo(2)
        );
    }
}