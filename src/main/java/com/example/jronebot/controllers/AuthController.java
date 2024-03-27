package com.example.jronebot.controllers;

import com.example.jronebot.models.UserApplication;
import com.example.jronebot.services.UserApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class AuthController {

    private final UserApplicationService userApplicationService;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("title","Авторизация");
        return "login";
    }
    @GetMapping("/index")
    public String getSuccessPage(Model model){
        model.addAttribute("title","index");
        return "index";
    }
    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("title", "home");
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
        return "home";
    }

    @GetMapping("/auth/registration")
    public String registration(Model model) {
        model.addAttribute("title", "registration");
        model.addAttribute("errorMessage", true);
        return "registration";
    }

    @PostMapping("/auth/registration")
    public String createAdmin(UserApplication user, Model model) {
        if (!userApplicationService.creatAdminApplication(user)) {
            model.addAttribute("errorMessage", false);
            model.addAttribute("title", "registration");
            return "registration";
        }else {
            userApplicationService.creatAdminApplication(user);
            return "redirect:/login";
        }
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/auth/registrationUser")
    public String registrationUser(Model model) {
        model.addAttribute("title", "registrationUser");
        model.addAttribute("errorMessage", true);
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
        return "userApplicationNew";
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/auth/registrationUser")
    public String createUser(UserApplication user, Model model) {
        if (!userApplicationService.creatUserApplication(user)) {
            model.addAttribute("errorMessage", false);
            model.addAttribute("title", "registrationUser");
            model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
            return "userApplicationNew";
        } else {
            userApplicationService.creatUserApplication(user);
            return "redirect:/userApplication/list";

        }
    }

 }


