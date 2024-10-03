package com.identify.leavescoffee.controller;

import com.identify.leavescoffee.dto.request.UserCreationRequest;
import com.identify.leavescoffee.entity.User;
import com.identify.leavescoffee.service.UserService;
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
    User createUser(@RequestBody UserCreationRequest request) {

        return userService.createRequest(request);

    }

    @GetMapping
    List<User> getAllUsers() {

        return userService.getAllUsers();

    }

    @GetMapping("/{iduser}")
    User getUserById(@PathVariable("iduser") String iduser) {

        return userService.getUserById(iduser);

    }

    @PutMapping("/{iduser}")
    User updateUser(@PathVariable("iduser") String iduser, @RequestBody UserCreationRequest request){

        return userService.updateUserById(iduser, request);

    }

    @DeleteMapping("/{iduser}")
    void deleteUser(@PathVariable("iduser") String iduser) {

        userService.deleteUserById(iduser);

    }


}
