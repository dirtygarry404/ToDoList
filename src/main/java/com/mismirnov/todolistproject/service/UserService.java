package com.mismirnov.todolistproject.service;

import com.mismirnov.todolistproject.entity.Role;
import com.mismirnov.todolistproject.entity.User;
import com.mismirnov.todolistproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException(
                "User '" + username + "' not found");
    }

    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, List<String> roles, Map<String, String> parameters) {
        user.setUsername(parameters.get("username"));
        user.setFullname(parameters.get("fullname"));
        user.setAddress(parameters.get("city"));
        user.setCity(parameters.get("city"));
        user.setZipcode(parameters.get("zipcode"));
        Set<String> predefinedRoles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();
        for (String key : roles) {
            if (predefinedRoles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }
}

