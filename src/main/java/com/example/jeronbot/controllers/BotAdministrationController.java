package com.example.jeronbot.controllers;

import com.example.jeronbot.models.BotSetting;
import com.example.jeronbot.repositories.BotSettingRepository;
import com.example.jeronbot.services.BotSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/bot")
public class BotAdministrationController {

    private final BotSettingService botSettingService;

    @GetMapping("/botSetting")
    public String getBotSetting(Model model, @ModelAttribute("botSetting") BotSetting botSetting){
        if(botSettingService.list().isEmpty()){
            model.addAttribute("botSetting", new BotSetting());
 //            System.out.println("New bot");
        }else {
            model.addAttribute("botSetting", botSettingService.list().get(0));
//            System.out.println("Old bot");
        }
        model.addAttribute("title", "botSetting");
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
        return "redirect:/bot/botSetting";
    }

    @PostMapping("/isActive")
    public String updateActiveBot(Model model, @ModelAttribute("botSetting") BotSetting botSetting,
                                  @ModelAttribute("activeBot") Boolean active){
        botSettingService.updateBotActive(botSetting.getId(), active);
        model.addAttribute("botSetting", botSetting);
        model.addAttribute("title", "botSetting");
        return "redirect:/bot/botSetting";
    }

}
