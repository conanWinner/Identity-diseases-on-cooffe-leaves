package com.identify.leavescoffee.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {

    SUCCESS(1000, "Success"),
    USER_EXISTS(2000, "User already exists"),
    USER_NOT_FOUND(3000, "User not found"),
    USERNAME_INVALID(4000, "Username is invalid"),
    PASSWORD_INVALID(5000, "Password is invalid"),


    UNCATEGORIZED_ERROR(9999, "Uncategorized error"),
    KEY_ERROR(9998, "Uncategorized error"),

    ;

    int code;

    String message;

}
