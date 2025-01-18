package com.postgresql.database.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the home page.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());
        }
        return "index";
    }
    @GetMapping("/index2")
    public String index2(Model model, @AuthenticationPrincipal OidcUser principal) {
        return "index2";
    }
    @GetMapping("/form")
    public String form(Model model, @AuthenticationPrincipal OidcUser principal) {
        return "form";
    }
    @GetMapping("/form2")
    public String form2(Model model, @AuthenticationPrincipal OidcUser principal) {
        return "form2";
    }
    @GetMapping("/form3")
    public String form3(Model model, @AuthenticationPrincipal OidcUser principal) {
        return "form3";
    }
    @GetMapping("/profil")
    public String profil(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());
        }
        return "profil";
    }
}