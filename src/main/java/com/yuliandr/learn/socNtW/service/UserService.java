package com.yuliandr.learn.socNtW.service;

import com.yuliandr.learn.socNtW.repository.UserRepository;
import com.yuliandr.learn.socNtW.entity.User;
import com.yuliandr.learn.socNtW.entity.enums.ERole;
import com.yuliandr.learn.socNtW.exceptions.UserExistException;
import com.yuliandr.learn.socNtW.payload.request.SignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}
