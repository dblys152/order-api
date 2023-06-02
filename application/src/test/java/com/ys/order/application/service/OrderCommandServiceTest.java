package com.ys.order.application.service;

import com.ys.order.application.port.in.CompleteOrderCommand;
import com.ys.order.application.port.out.LoadOrderPort;
import com.ys.order.application.port.out.RecordOrderPort;
import com.ys.order.application.service.exception.OrderNotFoundException;
import com.ys.order.application.service.fixture.SupportedOrderFixture;
import com.ys.order.domain.core.Order;
import com.ys.order.domain.core.OrderId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.ys.order.domain.core.OrderStatus.COMPLETED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class OrderCommandServiceTest extends SupportedOrderFixture {

    @InjectMocks
    private OrderCommandService sut;

    @Mock
    private LoadOrderPort loadOrderPort;
    @Mock
    private RecordOrderPort recordOrderPort;

    @Test
    void 주문을_완료한다() {
        CompleteOrderCommand command = new CompleteOrderCommand(ORDER_ID);
        given(loadOrderPort.findById(command.getOrderId())).willReturn(PRE_ORDER);
        given(recordOrderPort.save(PRE_ORDER)).willReturn(COMPLETED_ORDER);

        Order actual = sut.complete(command);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getStatus()).isEqualTo(COMPLETED),
                () -> verify(loadOrderPort).findById(ORDER_ID),
                () -> verify(recordOrderPort).save(PRE_ORDER)
        );
    }

    @Test
    void 선_주문건이_없으면_주문을_완료_할_수_없다() {
        OrderId NO_ORDER_ID = OrderId.of("NO_ORDER_ID");
        CompleteOrderCommand command = new CompleteOrderCommand(NO_ORDER_ID);
        given(loadOrderPort.findById(command.getOrderId())).willThrow(OrderNotFoundException.class);

        assertAll(
                () -> assertThatThrownBy(() -> sut.complete(command)).isInstanceOf(OrderNotFoundException.class),
                () -> verify(loadOrderPort).findById(NO_ORDER_ID),
                () -> verifyNoInteractions(recordOrderPort)
        );
    }
}