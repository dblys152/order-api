package com.ys.infra.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DomainEventTest {

    private static final String ANY_EVENT_TYPE = "com.ys.order.domain.event.OrderCompletedEvent";
    private static final String ANY_OCCURRED_AT_STRING = "2023-06-20T02:53:00.299046";

    private DomainEvent<Map<String, Object>> sut;

    @BeforeEach
    void setUp() {
        Map<String, Object> anyPayload = new HashMap<>();
        anyPayload.put("orderId", "00700");
        sut = new DomainEvent<>(ANY_EVENT_TYPE, ANY_OCCURRED_AT_STRING, anyPayload);
    }

    @Test
    void serialize_event() {
        DomainEvent actual = sut.serializePayload();

        assertThat(actual).isNotNull();
        assertThat(actual.getPayload()).isInstanceOf(String.class);
    }

    @Test
    public void serialize_and_deserialize_event() {
        DomainEvent eventOfSerializedPayload = sut.serializePayload();

        DomainEvent<Map<String, Object>> actual = DomainEvent.deserializePayload(eventOfSerializedPayload, Map.class);
        String orderId = actual.getPayload().get("orderId").toString();

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(ANY_EVENT_TYPE),
                () -> assertThat(actual.getOccurredAt()).isEqualTo(ANY_OCCURRED_AT_STRING),
                () -> assertThat(actual.getPayload()).isNotNull()
        );
    }
}