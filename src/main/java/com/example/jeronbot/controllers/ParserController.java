package com.example.jeronbot.controllers;

import com.example.jeronbot.models.ParserSetting;
import com.example.jeronbot.services.ParserSettingService;

import com.example.jeronbot.services.StorageService;
import com.example.jeronbot.services.TurbochargerService;
import com.example.jeronbot.services.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/parserSetting")
public class ParserController {

    private final ParserSettingService parserSettingService;
    private final StorageService storageService;
    private  final TurbochargerService turbochargerService;
    private final UserApplicationService userApplicationService;

    @GetMapping("/edit")
    public String getParerSetting(Model model){
       model.addAttribute("parserSetting", parserSettingService.getSetting());
       model.addAttribute("files", storageService.loadAll().map(
                            path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                    "serveFile", path.getFileName().toString()).build().toUri().toString())
                    .collect(Collectors.toList()));
       model.addAttribute("fileName", parserSettingService.getFile().getName());
        model.addAttribute("title", "parserSetting");
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
            return "parserSettingEdit";
    }

    @PostMapping("/update/{id}")
    public String updateParserSetting(@ModelAttribute("parserSetting")ParserSetting parserSetting,
                                     @PathVariable("id") Long id) {
        parserSettingService.updateParserSetting(parserSetting, id);
        return "redirect:/parserSetting/edit";
    }

    @PostMapping("/delete/{id}")
    public String deleteParserSetting(@PathVariable("id") Long id){
        storageService.deleteAll();
        storageService.init();
        parserSettingService.deleteParserSetting(id);
        return "redirect:/parserSetting/edit";
    }

    @PostMapping("/parsing/{id}")
    public String parsing(@PathVariable("id") Long id) throws IOException {
        turbochargerService.updateTurbochargerList(parserSettingService.parsing(parserSettingService.getSettingById(id)));
        return "redirect:/parserSetting/edit";
    }
}
