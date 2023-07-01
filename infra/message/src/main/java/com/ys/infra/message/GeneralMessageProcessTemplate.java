package com.ys.infra.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

import static com.ys.infra.message.GeneralMessageProcessReturn.*;

@Slf4j
public class GeneralMessageProcessTemplate {

    private final EventValidator eventValidator;

    public GeneralMessageProcessTemplate() {
        this.eventValidator = new EventValidator();
    }

    public <T> GeneralMessageProcessReturn doProcess(Message<DomainEvent<String>> message, Class<T> aEventClass, Consumer<T> processor) {
        if (message == null || message.getPayload() == null) {
            log.error("Message or Message payload is null");
            return IGNORE;
        }
        String messageId = String.valueOf(message.getHeaders().get("id"));
        DomainEvent<String> payloadDomainEvent = message.getPayload();

        if (aEventClass == null) {
            log.error(String.format("aEventClass is null. MessageId is %s, Message Payload is %s", messageId, payloadDomainEvent));
            return RETRY;
        }

        if (processor == null) {
            log.error(String.format("processor is null. MessageId is %s, Message Payload is %s", messageId, payloadDomainEvent));
            return RETRY;
        }

        try {
            log.info(String.format("Received a message. EventType is %s, OccurredAt is %s",
                    payloadDomainEvent.getType(), payloadDomainEvent.getOccurredAt()));

            T event = DomainEvent.deserializePayload(payloadDomainEvent, aEventClass).getPayload();

            eventValidator.validateAndThrow(event);

            processor.accept(event);
            return SUCCESS;
        } catch (EventValidateException e) {
            log.error(String.format("%s has violationMessages. Messages: '%s'", e.getEventType(), e.getViolationMessages()), e);
            return IGNORE;
        } catch (Exception e) {
            log.error(String.format("Failed to process of Message. MessageId is %s, Exception Message : %s", messageId, e.getMessage()));
            e.printStackTrace();
            return RETRY;
        }
    }
}
