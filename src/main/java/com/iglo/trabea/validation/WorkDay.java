package com.iglo.trabea.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = WorkingDayValidator.class)
public @interface WorkDay {
    String message() default "Requested Work Schedule must be between Monday and Friday";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
