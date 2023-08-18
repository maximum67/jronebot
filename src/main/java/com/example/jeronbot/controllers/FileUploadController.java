package com.example.jeronbot.controllers;


import com.example.jeronbot.models.ParserSetting;
import com.example.jeronbot.services.ParserSettingService;
import com.example.jeronbot.services.StorageService;
import com.example.jeronbot.storage.StorageFileNotFoundException;
import lombok.RequiredArgsConstructor;
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

        @Autowired
        public FileUploadController(StorageService storageService) {
            this.storageService = storageService;
        }

        @GetMapping("/")
        public String listUploadedFiles(Model model) throws IOException {

            model.addAttribute("files", storageService.loadAll().map(
                            path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                    "serveFile", path.getFileName().toString()).build().toUri().toString())
                    .collect(Collectors.toList()));

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
