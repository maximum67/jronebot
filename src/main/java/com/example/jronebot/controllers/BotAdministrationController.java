package com.example.jronebot.controllers;

import com.example.jronebot.models.BotSetting;
import com.example.jronebot.services.BotSettingService;
import com.example.jronebot.services.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/bot")
public class BotAdministrationController {

    private final BotSettingService botSettingService;
    private final UserApplicationService userApplicationService;

    @GetMapping("/botSetting")
    public String getBotSetting(Model model, @ModelAttribute("botSetting") BotSetting botSetting){
        if(botSettingService.list().isEmpty()){
            model.addAttribute("botSetting", new BotSetting());
        }else {
            model.addAttribute("botSetting", botSettingService.list().get(0));
        }
        model.addAttribute("title", "botSetting");
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
        return "botSetting";
    }
    @PostMapping("/updateBotSetting")
    public String updateBotSetting(Model model, @ModelAttribute("botSetting") BotSetting botSetting){
        if (botSettingService.getBotSettingById(botSetting.getId())==null){
            botSettingService.updateBotSetting(botSetting);
        }else {
            botSettingService.updateBotSettingById(botSetting, botSetting.getId());
        }
        model.addAttribute("botSetting", botSetting);
        model.addAttribute("title", "botSetting");
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
        return "redirect:/bot/botSetting";
    }

    @PostMapping("/isActive")
    public String updateActiveBot(Model model, @ModelAttribute("botSetting") BotSetting botSetting,
                                  @ModelAttribute("activeBot") Boolean active){
        botSettingService.updateBotActive(botSetting.getId(), active);
        model.addAttribute("botSetting", botSetting);
        model.addAttribute("title", "botSetting");
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
        return "redirect:/bot/botSetting";
    }

}
