package com.ys.order.application.service;

import com.ys.order.application.port.in.PreOrderCommand;
import com.ys.order.application.port.out.RecordOrderPort;
import com.ys.order.application.service.fixture.SupportedOrderFixture;
import com.ys.order.domain.core.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PreOrderCommandServiceTest extends SupportedOrderFixture {

    @InjectMocks
    private PreOrderCommandService sut;

    @Mock
    private RecordOrderPort recordOrderPort;

    @Test
    void 선_주문을_등록한다() {
        PreOrderCommand command = new PreOrderCommand(
                ORDERER, ORDER_LINES, PAYMENT_INFOS, SHIPPING_INFO);

        Order actual = sut.preOrder(command);

        assertAll(
                () -> verify(recordOrderPort).save(actual),
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getOrderer()).isEqualTo(command.getOrderer()),
                () -> assertThat(actual.getOrderLines()).isEqualTo(command.getOrderLines()),
                () -> assertThat(actual.getPaymentInfos()).isEqualTo(command.getPaymentInfos()),
                () -> assertThat(actual.getShippingInfo()).isEqualTo(command.getShippingInfo())
        );
    }
}