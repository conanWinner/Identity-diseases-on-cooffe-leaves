package com.identify.leavescoffee.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    String id;
    String username;
    String phonenumber;
    LocalDate registeddate;
    Set<String> roles;

}
