package com.example.jronebot.controllers;


import com.example.jronebot.services.StorageService;
import com.example.jronebot.services.UserApplicationService;
import com.example.jronebot.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/file")
    public class FileUploadController {

        private final StorageService storageService;
        private final UserApplicationService userApplicationService;

        @Autowired
        public FileUploadController(StorageService storageService, UserApplicationService userApplicationService) {
            this.storageService = storageService;
            this.userApplicationService = userApplicationService;
        }

        @GetMapping("/")
        public String listUploadedFiles(Model model) throws IOException {

            model.addAttribute("files", storageService.loadAll().map(
                            path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                    "serveFile", path.getFileName().toString()).build().toUri().toString())
                    .collect(Collectors.toList()));
            model.addAttribute("title", "uploadForm");
            model.addAttribute("isAdmin",  userApplicationService.getUserByPrincipal().isAdmin());
            return "uploadForm";
        }

        @GetMapping("/files/{filename:.+}")
        @ResponseBody
        public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

            Resource file = storageService.loadAsResource(filename);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        }

        @PostMapping("/upload")
        public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                       RedirectAttributes redirectAttributes) {
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message",
                        "File siEmpty" + "!");
            }else{
                storageService.deleteAll();
                storageService.init();
                storageService.store(file);
                redirectAttributes.addFlashAttribute("fileName", file.getOriginalFilename());
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded " + file.getOriginalFilename() + "!");
            }
            return "redirect:/parserSetting/edit";
        }

        @ExceptionHandler(StorageFileNotFoundException.class)
        public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
            return ResponseEntity.notFound().build();
        }

    }
