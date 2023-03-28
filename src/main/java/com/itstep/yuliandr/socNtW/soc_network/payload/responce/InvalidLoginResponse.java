package com.itstep.yuliandr.socNtW.soc_network.payload.responce;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private  String invUsNameResponse;
    private  String invUsPasswResponse;

    //ответ на 401
    public InvalidLoginResponse() {
        this.invUsNameResponse = "Invalid User Name ";
        this.invUsPasswResponse = "Invalid User Password ";
    }
}
