package com.iti.aiplayground.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String login() {
        return "login";
    }

    @PostMapping
    public String submit(@RequestParam String email, @RequestParam String password, HttpSession session) {
        session.setAttribute("loggedIn", true);
        session.setAttribute("userEmail", email);
        return "redirect:/";
    }
}
