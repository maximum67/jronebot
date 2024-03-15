package com.example.jeronbot.controllers;

import com.example.jeronbot.models.TelegramUser;
import com.example.jeronbot.services.TelegramUserService;
import com.example.jeronbot.services.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/telegramUser")
@RequiredArgsConstructor
public class TelegramUserController {

    private final TelegramUserService telegramUserService;
    private final UserApplicationService userApplicationService;


    @GetMapping("/list")
    public String getTelegramUserList (Model model){
        model.addAttribute("title", "telegramUserList");
        model.addAttribute("telegramUserList", telegramUserService.list());
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
        return "telegramUserList";
    }

    @GetMapping("/{id}")
    public String deleteTelegramUser(@PathVariable("id") TelegramUser telegramUser) {
        telegramUserService.deleteTelegramUserById(telegramUser.getId());
      return "redirect:/telegramUser/list";

    }
    @GetMapping("/filtr/{id}")
    public String filtrByTelegramUserId(@PathVariable("id") TelegramUser telegramUser, Model model) {
        model.addAttribute("title", "telegramUserList");
        model.addAttribute("telegramUserList", telegramUserService.listByTelegramUserId(telegramUser));
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
        return "telegramUserList";
    }


}



