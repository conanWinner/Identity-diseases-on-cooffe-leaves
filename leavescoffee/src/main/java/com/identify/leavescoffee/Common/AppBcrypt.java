package com.identify.leavescoffee.Common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AppBcrypt {

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

}
