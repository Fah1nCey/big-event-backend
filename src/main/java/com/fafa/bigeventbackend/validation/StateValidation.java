package com.fafa.bigeventbackend.validation;


import com.fafa.bigeventbackend.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    /**
     *
     * @param value 将来要校验的数据
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.equals("已发布") || value.equals("草稿")) {
            return true;
        }
        return false;
    }
}
