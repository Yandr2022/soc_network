package com.yuliandr.learn.socNtW.validations;

import com.yuliandr.learn.socNtW.annotations.PasswordMatch;
import com.yuliandr.learn.socNtW.payload.request.SignUpRequest;

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