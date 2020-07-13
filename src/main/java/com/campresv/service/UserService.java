package com.campresv.service;

import com.campresv.dao.UserRepository;
import com.campresv.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDto createIfNoteExist(String email, String firstName, String lastName){
        List<UserDto> users = userRepository.findByEmailAddress( email);
        if(users != null && users.size() > 0){
            return users.get(0);
        }else{
            UserDto newUser = new UserDto(firstName, lastName, email);
            userRepository.save(newUser);
            return newUser;
        }
    }

    public UserDto findByEmail(String email){
        List<UserDto> users = userRepository.findByEmailAddress( email);
        if(users != null && users.size() > 0) {
            return users.get(0);
        }else {
            return null;
        }
    }

}
