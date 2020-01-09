package com.mismirnov.todolistproject.config;

import com.mismirnov.todolistproject.entity.Role;
import com.mismirnov.todolistproject.entity.User;
import com.mismirnov.todolistproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User admin = new User("admin", "admin", Role.ADMIN);
        userRepository.save(admin);
    }
}

