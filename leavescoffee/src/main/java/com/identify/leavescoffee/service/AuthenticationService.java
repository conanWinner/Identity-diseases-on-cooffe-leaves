package com.identify.leavescoffee.service;

import com.identify.leavescoffee.dto.request.AuthenticationRequest;
import com.identify.leavescoffee.dto.request.IntrospectRequest;
import com.identify.leavescoffee.dto.response.ApiResponse;
import com.identify.leavescoffee.dto.response.AuthenticationResponse;
import com.identify.leavescoffee.dto.response.IntrospectResponse;
import com.identify.leavescoffee.entity.User;
import com.identify.leavescoffee.exception.AppException;
import com.identify.leavescoffee.exception.ErrorCode;
import com.identify.leavescoffee.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String KEY;

    public ApiResponse<IntrospectResponse> isIntrospect(IntrospectRequest request) throws JOSEException, ParseException {

        String token = request.getToken();

        JWSVerifier verifier = new MACVerifier(KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        boolean check =  verified && expiryTime.after(new Date());

        return ApiResponse.<IntrospectResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(
                        IntrospectResponse.builder()
                                .valid(check)
                                .build()
                )
                .build();

    }

    public ApiResponse<AuthenticationResponse> isAuthenticated(AuthenticationRequest request) {
         User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

         boolean result = passwordEncoder.matches(request.getPassword(), user.getPassword());

         if(!result) {
             throw new AppException(ErrorCode.UNAUTHENTICATED);
         }

         String token = generateToken(user);

         return ApiResponse.<AuthenticationResponse>builder()
                 .code(ErrorCode.SUCCESS.getCode())
                 .message(ErrorCode.SUCCESS.getMessage())
                 .result(
                         AuthenticationResponse.builder()
                                 .token(token)
                         .build())
                 .build();

    }

    private String generateToken(User user) {

        JWSHeader  header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet  claims = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("conanWinner.com")
                .issueTime(new Date())
                .expirationTime(
                        new Date(
                                Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                        )
                )
                .claim("scope", buildScope(user))
                .build();


        JWSObject  jwsObject = new JWSObject(header, claims.toPayload());

        try {
            jwsObject.sign(new MACSigner(KEY.getBytes()));

            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private String buildScope(User user){

        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!user.getRoles().isEmpty()) {
            user.getRoles().forEach(
                    stringJoiner::add
            );
        }

        return stringJoiner.toString();

    }

}
