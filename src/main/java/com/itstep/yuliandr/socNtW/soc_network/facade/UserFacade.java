package com.itstep.yuliandr.socNtW.soc_network.facade;

import com.itstep.yuliandr.socNtW.soc_network.dto.UserDTO;
import com.itstep.yuliandr.socNtW.soc_network.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    public UserDTO userToUserDTO(User user){
        //представление пользователя для клиентской стороны
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setUsername(user.getUsername());
        userDTO.setBio(user.getBio());
        return userDTO;
    }
}
