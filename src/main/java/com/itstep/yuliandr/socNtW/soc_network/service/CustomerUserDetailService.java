package com.itstep.yuliandr.socNtW.soc_network.service;

import com.itstep.yuliandr.socNtW.soc_network.repository.UserRepository;
import com.itstep.yuliandr.socNtW.soc_network.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public CustomerUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found"));
        return build(user);
    }
    public User loadUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }
    //создание копии пользователя с данными аутентификации для загрузки со сверкой по email см. loadUserByUsername(String username)
    public static User build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        return new User(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
    }
}
