package com.ys.infra.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DomainEventTest {

    private static final String ANY_EVENT_TYPE = "com.ys.test.AnyEvent";
    private static final String ANY_OCCURRED_AT_STRING = "2023-06-20T02:53:00.299046";

    private DomainEvent<AnyEvent> sut;

    @BeforeEach
    void setUp() {
        sut = new DomainEvent<>(
                ANY_EVENT_TYPE, ANY_OCCURRED_AT_STRING, new AnyEvent("ANY_VALUE"));
    }

    @Test
    void 도메인_이벤트_직렬화() {
        String actual = sut.serialize();

        assertThat(actual).isEqualTo("{\"type\":\"" + ANY_EVENT_TYPE
                + "\",\"occurredAt\":\"" + ANY_OCCURRED_AT_STRING
                + "\",\"payload\":\"{\\\"value\\\":\\\"ANY_VALUE\\\"}\"}");
    }

    @Test
    void 도메인_이벤트_페이로드_직렬화() {
        DomainEvent actual = sut.serializePayload();

        assertThat(actual).isNotNull();
        assertThat(actual.getPayload()).isEqualTo("{\"value\":\"ANY_VALUE\"}");
    }

    @Test
    void 도메인_이벤트_역직렬화() {
        String serializedEvent = "{\"type\":\"" + ANY_EVENT_TYPE
                + "\",\"occurredAt\":\"" + ANY_OCCURRED_AT_STRING
                + "\",\"payload\":\"{\\\"value\\\":\\\"ANY_VALUE\\\"}\"}";

        DomainEvent<AnyEvent> actual = DomainEvent.deserialize(serializedEvent, AnyEvent.class);
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(ANY_EVENT_TYPE),
                () -> assertThat(actual.getOccurredAt()).isEqualTo(ANY_OCCURRED_AT_STRING),
                () -> assertThat(actual.getPayload()).isInstanceOf(AnyEvent.class)
        );
    }

    @Test
    void 도메인_이벤트_역직렬화_payload_string() {
        String serializedEvent = "{\"type\":\"" + ANY_EVENT_TYPE
                + "\",\"occurredAt\":\"" + ANY_OCCURRED_AT_STRING
                + "\",\"payload\":\"{\\\"value\\\":\\\"ANY_VALUE\\\"}\"}";

        DomainEvent<String> actual = DomainEvent.deserialize(serializedEvent, String.class);
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(ANY_EVENT_TYPE),
                () -> assertThat(actual.getOccurredAt()).isEqualTo(ANY_OCCURRED_AT_STRING),
                () -> assertThat(actual.getPayload()).isInstanceOf(String.class)
        );
    }

    @Test
    void 도메인_이벤트_페이로드_역직렬화() {
        DomainEvent eventOfSerializedPayload = sut.serializePayload();

        DomainEvent<AnyEvent> actual = DomainEvent.deserializePayload(eventOfSerializedPayload, AnyEvent.class);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(ANY_EVENT_TYPE),
                () -> assertThat(actual.getOccurredAt()).isEqualTo(ANY_OCCURRED_AT_STRING),
                () -> assertThat(actual.getPayload()).isInstanceOf(AnyEvent.class)
        );
    }

    private static class AnyEvent {
        private String value;

        @JsonCreator
        public AnyEvent(@JsonProperty("value") String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}