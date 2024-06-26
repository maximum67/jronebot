package com.example.jronebot.controllers;


import com.example.jronebot.models.Turbocharger;
import com.example.jronebot.services.TurbochargerService;
import com.example.jronebot.services.UserApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/turbocharger")

public class TurbochargerController {

    private final TurbochargerService turbochargerService;
    private final UserApplicationService userApplicationService;

    @GetMapping("/list")
    public String getTurbochargerList(Model model){
        model.addAttribute("title", "turbochargerList");
        model.addAttribute("turbochargerList", turbochargerService.list());
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
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
        model.addAttribute("title", "turbochargerNew");
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
        return "turbochargerNew";
    }
    @GetMapping("/update")
    public String update(Model model){
        model.addAttribute("title", "turbochargerUpdate");
        model.addAttribute("title", "turbochargerUpdate");
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
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
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
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
        model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
        return "searchTurbocharger";
    }
}
