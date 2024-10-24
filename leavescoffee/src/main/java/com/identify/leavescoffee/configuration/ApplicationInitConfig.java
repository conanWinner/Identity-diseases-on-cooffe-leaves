package com.identify.leavescoffee.configuration;

import com.identify.leavescoffee.entity.User;
import com.identify.leavescoffee.enums.Role;
import com.identify.leavescoffee.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner  applicationRunner(UserRepository userRepository) {
        return args -> {

            HashSet<String> role = new HashSet<String>();
            role.add(Role.ADMIN.name());

            if (userRepository.findByUsername("admin").isEmpty()) {

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(role)
                        .build();

                userRepository.save(user);

                log.warn("Account admin has been created with default password: admin, please change it");

            }

        };
    }

}
