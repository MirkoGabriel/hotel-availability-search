package com.mindata.hotelsearch.infraestructure.adapter.in.validation;

import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchRequestDto;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.LocalDate;

@Documented
@Constraint(validatedBy = ValidDateRange.Validator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {

    String message() default "checkIn must be before checkOut";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ValidDateRange, SearchRequestDto> {

        @Override
        public boolean isValid(SearchRequestDto request, ConstraintValidatorContext context) {
            if (request == null ||
                    request.checkIn() == null ||
                    request.checkOut() == null) {
                return true;
            }

            LocalDate today = LocalDate.now();

            if (request.checkIn().isBefore(today)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("checkIn must not be in the past")
                        .addPropertyNode("checkIn")
                        .addConstraintViolation();
                return false;
            }

            if (!request.checkIn().isBefore(request.checkOut())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("checkIn must be before checkOut")
                        .addPropertyNode("checkIn")
                        .addConstraintViolation();

                return false;
            }

            return true;
        }
    }
}
