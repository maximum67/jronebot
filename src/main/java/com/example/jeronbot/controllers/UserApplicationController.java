package com.example.jeronbot.controllers;

import com.example.jeronbot.models.Role;
import com.example.jeronbot.models.UserApplication;
import com.example.jeronbot.services.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/userApplication")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UserApplicationController {

   private final UserApplicationService userApplicationService;

    @GetMapping("/list")
    public String getUserList(Model model){
        model.addAttribute("userList", userApplicationService.listUserApplication());
        model.addAttribute("title", "userList");
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
        return "userApplicationList";
    }

    @GetMapping("/edit/{id}")
    public String getUserEdit(@PathVariable ("id") Long id, Model model){
        model.addAttribute("user", userApplicationService.getUserApplicationById(id));
        model.addAttribute("roles", Role.values());
        model.addAttribute("title", "userEdit");
        model.addAttribute("isAdmin", userApplicationService.getUserByPrincipal().isAdmin());
        return "userApplicationEdit";
    }
    @PostMapping("/user/changRoles/{id}")
    public String changeUserRoles(@RequestParam("userId") UserApplication user, @RequestParam Map<String, String> form) {
        userApplicationService.changeUserRoles(user, form);
        return "redirect:/userApplication/edit/" + user.getId();
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userApplicationService.deleteUserApplication(id);
        return "redirect:/userApplication/list";
    }

    @PostMapping("/updatePassword/{id}")
    public String updatePassword(@RequestParam("userId") UserApplication user, @RequestParam("password") String password) {
        userApplicationService.updatePassword(user, password);
        return "redirect:/userApplication/edit/" + user.getId();
    }
}
