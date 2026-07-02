package com.pranati.promptlibrary.controller;

import com.pranati.promptlibrary.CustomUserDetails;
import com.pranati.promptlibrary.entity.User;
import com.pranati.promptlibrary.service.PromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private PromptService promptService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        // Logged-in user's name
        model.addAttribute("username", user.getName());

        // Dashboard Statistics
        model.addAttribute("totalPrompts",
                promptService.getTotalPrompts(user));

        model.addAttribute("favoriteCount",
                promptService.getFavoriteCount(user));

        model.addAttribute("programmingCount",
                promptService.getProgrammingCount(user));

        model.addAttribute("springBootCount",
                promptService.getSpringBootCount(user));

        model.addAttribute("libraryCount",
                promptService.getLibraryCount(user));

        model.addAttribute("databaseCount",
                promptService.getDatabaseCount(user));

        model.addAttribute("aiCount",
                promptService.getAICount(user));

        model.addAttribute("careerCount",
                promptService.getCareerCount(user));

        return "dashboard";
    }
}