package com.itstep.yuliandr.socNtW.soc_network.entity;

import com.itstep.yuliandr.socNtW.soc_network.entity.enums.ERole;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private Long id;
    private String name;
    private String username;
    private String lastname;
    private String email;
    private String bio;
    private String password;
    private Set<ERole> roles = new HashSet<>();
    private List<Post> posts = new ArrayList<>();
    private LocalDateTime createdDate;
    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
