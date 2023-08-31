package com.example.jeronbot.controllers;


import com.example.jeronbot.models.Turbocharger;
import com.example.jeronbot.services.TurbochargerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/turbocharger")

public class TurbochargerController {

    private final TurbochargerService turbochargerService;

    @GetMapping("/list")
    public String getTurbochargerList(Model model){
        model.addAttribute("title", "turbochargerList");
        model.addAttribute("turbochargerList", turbochargerService.list());
        return "turbochargerList";
    }
    @PostMapping("/new")
    public String createTurbocharger(@ModelAttribute("turbocharger") Turbocharger turbocharger) {
       turbochargerService.updateTurbocharger(turbocharger);
       return "redirect:/turbocharger/list";
    }
    @GetMapping("/new")
    public String newTurbocharger(Model model) {
        model.addAttribute(new Turbocharger());
        return "turbochargerNew";
    }
    @GetMapping("/update")
    public String update(Model model){
        model.addAttribute("title", "turbochargerUpdate");
        return "turbochargerUpdate";
    }
    @PostMapping("/update/{id}")
    public String updateTurbocharger(@ModelAttribute("turbocharger") Turbocharger turbocharger,
                                     @PathVariable("id") Long id){
        turbochargerService.updateTurbochargerById(id, turbocharger);
        return "redirect:/turbocharger/list";
    }
    @GetMapping("/edit/{turbocharger}")
    public String getTurbochargerEdit(@PathVariable("turbocharger") Turbocharger turbocharger, Model model){
        model.addAttribute("title", "turbochargerEdit");
        model.addAttribute("turbocharger", turbocharger);
        return "turbochargerEdit";
    }
    @PostMapping("/delete/{id}")
    public String deleteTurbocharger(@PathVariable("id") Long id){
        turbochargerService.deleteTurbocharger(id);
        return "redirect:/turbocharger/list";
    }
    @PostMapping("/search")
    public String searchTurbocharger(@ModelAttribute ("turboOeNo") String turboOeNo,
                                     RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("turbochargerList",turbochargerService.getTurbochargerByOeNo(turboOeNo));
        return "redirect:/turbocharger/search";
    }
    @GetMapping("/search")
    public String search(Model model){
        model.addAttribute("title", "search");
        return "searchTurbocharger";
    }
}
