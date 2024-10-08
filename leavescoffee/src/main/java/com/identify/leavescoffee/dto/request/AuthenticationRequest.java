package com.identify.leavescoffee.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    String username;
    String password;

}
