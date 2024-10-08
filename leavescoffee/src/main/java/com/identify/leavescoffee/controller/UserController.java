package com.identify.leavescoffee.controller;

import com.identify.leavescoffee.dto.response.ApiResponse;
import com.identify.leavescoffee.dto.request.UserCreationRequest;
import com.identify.leavescoffee.dto.request.UserUpdateRequest;
import com.identify.leavescoffee.dto.response.UserResponse;
import com.identify.leavescoffee.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {

        return userService.createRequest(request);

    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers() {

        return userService.getAllUsers();

    }

    @GetMapping("/{iduser}")
    UserResponse getUserById(@PathVariable("iduser") String iduser) {

        return userService.getUserById(iduser);

    }

    @PutMapping("/{iduser}")
    UserResponse updateUser(@PathVariable("iduser") String iduser, @RequestBody UserUpdateRequest request){

        return userService.updateUserById(iduser, request);

    }

    @DeleteMapping("/{iduser}")
    void deleteUser(@PathVariable("iduser") String iduser) {

        userService.deleteUserById(iduser);

    }


}
