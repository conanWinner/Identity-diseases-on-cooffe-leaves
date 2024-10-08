package com.identify.leavescoffee.controller;

import com.identify.leavescoffee.dto.request.AuthenticationRequest;
import com.identify.leavescoffee.dto.response.ApiResponse;
import com.identify.leavescoffee.dto.response.AuthenticationResponse;
import com.identify.leavescoffee.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService auth;

    @PostMapping("login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return auth.isAuthenticated(request);
    }

}
