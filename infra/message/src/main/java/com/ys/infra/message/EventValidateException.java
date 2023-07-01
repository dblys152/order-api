package com.ys.infra.message;

import lombok.Getter;

@Getter
public class EventValidateException extends IllegalArgumentException {

    private final String eventType;
    private final String violationMessages;

    public EventValidateException(String eventType, String violationMessages) {
        this.eventType = eventType;
        this.violationMessages = violationMessages;
    }
}
