package com.itstep.yuliandr.socNtW.soc_network.payload.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTTokenSuccessResponse {
    private String token;
    private boolean success;

}
