package com.identify.leavescoffee.service;

import com.identify.leavescoffee.dto.request.AuthenticationRequest;
import com.identify.leavescoffee.dto.response.ApiResponse;
import com.identify.leavescoffee.dto.response.AuthenticationResponse;
import com.identify.leavescoffee.entity.User;
import com.identify.leavescoffee.exception.AppException;
import com.identify.leavescoffee.exception.ErrorCode;
import com.identify.leavescoffee.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.identify.leavescoffee.Common.AppBcrypt;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;

    public ApiResponse<AuthenticationResponse> isAuthenticated(AuthenticationRequest request) {
         User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

         boolean result = AppBcrypt.passwordEncoder.matches(request.getPassword(), user.getPassword());

         return ApiResponse.<AuthenticationResponse>builder()
                 .code(ErrorCode.SUCCESS.getCode())
                 .message(ErrorCode.SUCCESS.getMessage())
                 .result(
                         AuthenticationResponse.builder()
                         .authenticated(result)
                         .build())
                 .build();

    }

}
