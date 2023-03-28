package com.itstep.yuliandr.socNtW.soc_network.validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

//валидатор ошибок сервера - проверяем общие ошибки (объекты) и ошибки полей, возвращает MAP ошибок или null
@Service
public class ResponseErrorValidation {
    public ResponseEntity<Object> mapValidationService(BindingResult result) {//аргумент содержит ошибки, см аннотации LoginRequest
        if (result.hasErrors()) {//если ошибки есть
            Map<String, String> errorMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(result.getAllErrors())) {//и они не пустые
                for (ObjectError err ://складываем в мапу код-сообщение
                        result.getAllErrors()) {
                    errorMap.put(err.getCode(), err.getDefaultMessage());
                }
            }
            for (FieldError error : result.getFieldErrors()) {//смотри SignUpRequest
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
