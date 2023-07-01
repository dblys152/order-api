package com.ys.infra.message;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

public class EventValidator {

    <T> void validateAndThrow(T eventMessage) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> eventMessageViolations = validator.validate(eventMessage);

        if (!eventMessageViolations.isEmpty()) {
            throw new EventValidateException(eventMessage.getClass().getName(),
                    eventMessageViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", ")));
        }
    }
}
