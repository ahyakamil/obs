package com.obs.app.obs_api.validation;

import com.obs.app.obs_api.dto.CreateItemDto;
import com.obs.app.obs_api.exception.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Validation {
    private final Validator validator;

    public Validation() {
        ValidatorFactory factory = jakarta.validation.Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public void validate(Object obj) {
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        if(!violations.isEmpty()) {
            String message = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new BadRequestException(message);
        }
    }
}
