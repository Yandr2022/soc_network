package com.itstep.yuliandr.socNtW.soc_network.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String msg) {
        super(msg);
    }
}
