package com.itstep.yuliandr.socNtW.soc_network.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itstep.yuliandr.socNtW.soc_network.entity.enums.ERole;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    //уникальное, не изменяемое
    @Column(unique = true, updatable = false)
    private String username;
    //not null
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true)
    private String email;
    //не ограничен по длине
    @Column(columnDefinition = "text")
    private String bio;
    //длина, с учетом шифрования
    @Column(length = 3000)
    private String password;
    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_Id"))
    private Set<ERole> roles = new HashSet<>();

    //удаляется вместе с пользователем
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    //не обновляемое поле + шаблон формата
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;
    //загружается в другую таблицу
    @Transient
    private Collection<? extends GrantedAuthority> authorities;
//создается перед загрузкой в БД
    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
    public User() {
    }
    // для CustomerUserDetailService
    public User(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    //security
    @Override
    public String getPassword(){
        return password;
    };

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
