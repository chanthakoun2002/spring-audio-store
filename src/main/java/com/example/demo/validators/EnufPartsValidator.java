package com.example.demo.validators;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 *
 *
 *
 */
public class EnufPartsValidator implements ConstraintValidator<ValidEnufParts, Product> {
    @Autowired
    private ApplicationContext context;
    public static  ApplicationContext myContext;
    @Override
    public void initialize(ValidEnufParts constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext constraintValidatorContext) {
        if(context==null) return true;
        if(context!=null)myContext=context;
        ProductService repo = myContext.getBean(ProductServiceImpl.class);
        if (product.getId() != 0) {
            Product myProduct = repo.findById((int) product.getId());
            for (Part p : myProduct.getParts()) {
                if (p.getInv()<(product.getInv()-myProduct.getInv()))return false;
            }

            // this handles validation for parts of product when altering product will exceed bound of related parts
            int diff = product.getInv() - myProduct.getInv();
            if (diff <= 0) return true;
            for (Part p : myProduct.getParts()) {
                //checks each part for the product
                Integer min = p.getMin();
                int minVal = (min == null ? 0 : min);
                int remaining = p.getInv() - diff;
                // throw error if expected remaining exceeds minimum
                if (remaining < minVal) {

                    constraintValidatorContext.disableDefaultConstraintViolation();
                    constraintValidatorContext
                            .buildConstraintViolationWithTemplate(String.format("Not enough (%s) to above within minimum. Remaining would be %d (min is %d).", p.getName(), remaining, minVal))
                            .addPropertyNode("inv")
                            .addConstraintViolation();

                    return false;
                }
            }


            return true;
        }
        else{
            // handling for when creating a new product, dont let user add inventory when creating a new product
            if (product.getInv() != 0) {

                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext
                        .buildConstraintViolationWithTemplate("Set inventory to 0 when creating a new product. Create empty product then update with parts and inventor.")
                        .addPropertyNode("inv")
                        .addConstraintViolation();
                return false;
            }

                return true;
            }


    }
}
