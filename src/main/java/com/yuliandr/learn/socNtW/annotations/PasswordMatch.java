package com.yuliandr.learn.socNtW.annotations;

import com.yuliandr.learn.socNtW.validations.PasswordMatcher;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatcher.class)
@Documented
public @interface PasswordMatch {//сверка паролей00000000000000000000000000000000000000000000000000000
    String message() default "Password don't match";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
