package com.itstep.yuliandr.socNtW.soc_network.actualLoad.responce;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private  String inwUsNameResponse;
    private  String inwUsPasswResponse;

    //ответ на 401
    public InvalidLoginResponse() {
        this.inwUsNameResponse = "Invalid User Name ";
        this.inwUsPasswResponse = "Invalid User Password ";
    }
}
