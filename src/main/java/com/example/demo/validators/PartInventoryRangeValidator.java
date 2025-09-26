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

        if (min > max) {
            // if one is incorrect then pass to both error fields
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Min must not be greater than Max").addPropertyNode("min").addConstraintViolation();
            context.buildConstraintViolationWithTemplate("Max must be greater than Min").addPropertyNode("max").addConstraintViolation();
            return false;
        }

        if (inv < min) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Inventory cannot be less than Min").addPropertyNode("min").addConstraintViolation();
            return false;

        }

        if (inv > max) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Inventory cannot exceed Max").addPropertyNode("max").addConstraintViolation();
            return false;

        }

        return inv >= min && inv <= max;

        // this is here to make sure the part test for validation is triggered correctly
//        if (min > max) return true;
//        return inv >= min && inv <= max;
    }
}