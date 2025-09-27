package com.example.demo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PartInventoryRangeValidator.class)
public @interface ValidInventoryRange {
    String message() default "Inventory must be between set min and max.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}