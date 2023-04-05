package com.itstep.yuliandr.socNtW.soc_network.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CommentDTO {

    private Long id;
    @NotEmpty
    private String message;
    private String username;

}
