package com.identify.leavescoffee.service;

import com.identify.leavescoffee.dto.response.ApiResponse;
import com.identify.leavescoffee.dto.request.UserCreationRequest;
import com.identify.leavescoffee.dto.request.UserUpdateRequest;
import com.identify.leavescoffee.dto.response.UserResponse;
import com.identify.leavescoffee.entity.User;
import com.identify.leavescoffee.enums.Role;
import com.identify.leavescoffee.exception.AppException;
import com.identify.leavescoffee.exception.ErrorCode;
import com.identify.leavescoffee.mapper.UserMapper;
import com.identify.leavescoffee.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {

    UserRepository userRepository;
    UserMapper mapper;
    PasswordEncoder passwordEncoder;

    public ApiResponse<UserResponse> createRequest(UserCreationRequest request){

        HashSet<String> addRole = new HashSet<>();

        addRole.add(Role.USER.name());

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .phonenumber(request.getPhonenumber())
                .registeddate(request.getRegisteddate())
                .roles(addRole)
                .build();

        if(userRepository.existsByUsername(request.getUsername())){

            throw new AppException(ErrorCode.USER_EXISTS);

        }

        return ApiResponse.<UserResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(
                        mapper.toUserResponse(userRepository.save(user))
                )
                .build();

    }

    public ApiResponse<List<UserResponse>> getAllUsers(){

        return ApiResponse.<List<UserResponse>>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(
                        mapper.toUserResponse(
                                userRepository.findAll()
                        )
                )
                .build();

    }

    public UserResponse getUserById(String id){

        return mapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));

    }

    public UserResponse updateUserById(String id, UserUpdateRequest request){

        User user = userRepository.findById(id)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user.setPassword(request.getPhonenumber());
        user.setPhonenumber(request.getPhonenumber());

        return mapper.toUserResponse(userRepository.save(user));

    }

    public void deleteUserById(String id){

        userRepository.deleteById(id);

    }

}
