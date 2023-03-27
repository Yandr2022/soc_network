package com.itstep.yuliandr.socNtW.soc_network.annotations;



import com.itstep.yuliandr.socNtW.soc_network.validations.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.TYPE,ElementType.ANNOTATION_TYPE})//контексты, в которых применим тип аннотации
@Retention(RetentionPolicy.RUNTIME)//срок хранения аннотации с аннотированным типом
@Constraint(validatedBy = EmailValidator.class)//свой валидатор
@Documented
public @interface ValidEmail {
    String message()default "Invalid email";
    Class<?>[] groups()default {};
    Class<? extends Payload>[] payload()default{};

}
