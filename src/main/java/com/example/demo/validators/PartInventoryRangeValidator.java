package com.example.demo.validators;


import com.example.demo.domain.Part;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PartInventoryRangeValidator implements ConstraintValidator<ValidInventoryRange, Part> {

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext context) {
        // Check if value is within constraints of min and max
        if (part == null) return true;

        Integer min = part.getMin();
        Integer max = part.getMax();
        Integer inv = part.getInv();

        if (min == null || max == null) return true;

        if (min > max) return false;
        return inv >= min && inv <= max;
    }
}