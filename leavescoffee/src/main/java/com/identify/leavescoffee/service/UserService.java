package com.identify.leavescoffee.service;

import com.identify.leavescoffee.dto.request.ApiResponse;
import com.identify.leavescoffee.dto.request.UserCreationRequest;
import com.identify.leavescoffee.entity.User;
import com.identify.leavescoffee.exception.AppException;
import com.identify.leavescoffee.exception.ErrorCode;
import com.identify.leavescoffee.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {

    UserRepository userRepository;

    public ApiResponse<User> createRequest(UserCreationRequest request){

        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPhonenumber())
                .phonenumber(request.getPhonenumber())
                .registeddate(request.getRegisteddate())
                .build();

        if(userRepository.existsByUsername(request.getUsername())){

            throw new AppException(ErrorCode.USER_EXISTS);

        }

        return ApiResponse.<User>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(userRepository.save(user))
                .build();

    }

    public ApiResponse<List<User>> getAllUsers(){

        return ApiResponse.<List<User>>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(userRepository.findAll())
                .build();

    }

    public User getUserById(String id){

        return userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

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
