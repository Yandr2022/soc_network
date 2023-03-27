package com.yuliandr.learn.socNtW.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)//400
public class UserExistException extends RuntimeException{
    public UserExistException(String message) {
        super(message);
    }
}
