package com.identify.leavescoffee.service;

import com.identify.leavescoffee.dto.request.UserCreationRequest;
import com.identify.leavescoffee.entity.User;
import com.identify.leavescoffee.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {

    UserRepository userRepository;

    public User createRequest(UserCreationRequest request){

        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPhonenumber())
                .phonenumber(request.getPhonenumber())
                .registeddate(request.getRegisteddate())
                .build();

        return userRepository.save(user);

    }

    public List<User> getAllUsers(){

        return userRepository.findAll();

    }

    public User getUserById(String id){

        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not find user"));

    }

    public User updateUserById(String id, UserCreationRequest request){

        User user = getUserById(id);

        user.setUsername(request.getUsername());
        user.setPassword(request.getPhonenumber());
        user.setPhonenumber(request.getPhonenumber());
        user.setRegisteddate(request.getRegisteddate());

        return userRepository.save(user);

    }

    public void deleteUserById(String id){

        userRepository.deleteById(id);

    }

}
