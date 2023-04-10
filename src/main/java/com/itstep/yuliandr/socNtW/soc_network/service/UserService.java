package com.itstep.yuliandr.socNtW.soc_network.service;

import com.itstep.yuliandr.socNtW.soc_network.dto.UserDTO;
import com.itstep.yuliandr.socNtW.soc_network.repository.UserRepository;
import com.itstep.yuliandr.socNtW.soc_network.entity.User;
import com.itstep.yuliandr.socNtW.soc_network.entity.enums.ERole;
import com.itstep.yuliandr.socNtW.soc_network.exceptions.UserExistException;
import com.itstep.yuliandr.socNtW.soc_network.payload.request.SignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignUpRequest userIn){
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);
        try {
            LOGGER.info("Saving user {}" + userIn.getEmail());
            return repository.save(user);
        }catch (Exception e){
            LOGGER.error("Registration error\n{}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please try again");//custom exception

        }
    }

    public User updateUser(UserDTO userDTO, Principal principal){//principal -содержит данные пользователя из БД
        User user = getUserByPrincipal(principal);
        user.setName(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setBio(userDTO.getBio());
     return repository.save(user);
    }

    public User getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    public User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return repository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException
                ("User with username " + username + " not found "));
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
