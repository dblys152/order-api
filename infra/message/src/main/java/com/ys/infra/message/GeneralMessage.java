package com.ys.infra.message;

import lombok.Data;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.Map;

@Data
public class GeneralMessage<T> implements Message<T> {

    private T payload;
    private MessageHeaders headers;

    public GeneralMessage(T payload, MessageHeaders headers) {
        this.payload = payload;
        this.headers = headers;
    }

    public GeneralMessage(T payload) {
        this.payload = payload;
        this.headers = createHeaders();
    }

    private MessageHeaders createHeaders() {
        Map<String, Object> headersMap = new HashMap<>();
        headersMap.put(MessageHeaders.CONTENT_TYPE, "application/json");
        return new MessageHeaders(headersMap);
    }

    @Override
    public T getPayload() {
        return this.payload;
    }

    @Override
    public MessageHeaders getHeaders() {
        return this.headers;
    }
}
