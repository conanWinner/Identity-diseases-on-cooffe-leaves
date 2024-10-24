package com.identify.leavescoffee.controller;

import com.identify.leavescoffee.dto.request.AuthenticationRequest;
import com.identify.leavescoffee.dto.request.IntrospectRequest;
import com.identify.leavescoffee.dto.response.ApiResponse;
import com.identify.leavescoffee.dto.response.AuthenticationResponse;
import com.identify.leavescoffee.dto.response.IntrospectResponse;
import com.identify.leavescoffee.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService auth;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return auth.isAuthenticated(request);
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return auth.isIntrospect(request);
    }

}
