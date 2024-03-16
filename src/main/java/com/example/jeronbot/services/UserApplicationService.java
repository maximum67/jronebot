package com.example.jeronbot.services;

import com.example.jeronbot.models.Role;
import com.example.jeronbot.models.UserApplication;
import com.example.jeronbot.repositories.UserApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserApplicationRepository userApplicationRepository;
    private final PasswordEncoder passwordEncoder;


    public UserApplication getUserApplicationById(Long id){
        return userApplicationRepository.getReferenceById(id);
    }


    public boolean creatAdminApplication(UserApplication userApplication) {
        if (userApplicationRepository.findAll().isEmpty()) {
            userApplication.getRoles().add(Role.ROLE_ADMIN);
            userApplication.setPassword(passwordEncoder.encode(userApplication.getPassword()));
            userApplicationRepository.save(userApplication);
            return true;
        } else {
            return false;
        }
    }

    public boolean creatUserApplication(UserApplication userApplication) {
        String userName = userApplication.getName();
            if (userApplicationRepository.findByName(userName) != null) {
                return false;
            } else {
                userApplication.getRoles().add(Role.ROLE_USER);
                userApplication.setPassword(passwordEncoder.encode(userApplication.getPassword()));
                userApplicationRepository.save(userApplication);
                return true;
            }
    }

    public List<UserApplication> listUserApplication(){
        return userApplicationRepository.findAll();
    }

    public void deleteUserApplication(Long id) {
        if (!userApplicationRepository.getReferenceById(id).isAdmin())
        userApplicationRepository.deleteById(id);
    }

    public void updatePassword(UserApplication user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userApplicationRepository.save(user);
    }

    public void changeUserRoles(UserApplication user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
            userApplicationRepository.save(user);
        }
    }

    public UserApplication getUserByPrincipal() {
        return (UserApplication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
