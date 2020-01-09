package com.mismirnov.todolistproject.config;

import com.mismirnov.todolistproject.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String city;
    private String address;
    private String zipcode;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username, passwordEncoder.encode(password),
                fullname, city, address, zipcode);
    }
}