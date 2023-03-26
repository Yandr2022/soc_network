package com.itstep.yuliandr.socNtW.soc_network.validations;

import com.itstep.yuliandr.socNtW.soc_network.actualLoad.request.SignUpRequest;
import com.itstep.yuliandr.socNtW.soc_network.annotations.PasswordMatch;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatcher implements ConstraintValidator<PasswordMatch, Object> {

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        SignUpRequest userSignupRequest = (SignUpRequest) obj;
        return userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword());
    }
}