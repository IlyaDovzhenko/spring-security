package com.spring.security.controller;

import com.spring.security.entity.User;
import com.spring.security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userFrom,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userFrom.getPassword().equals(userFrom.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Password do not match!");
            return "registration";
        }
        if (!userService.saveUser(userFrom)) {
            model.addAttribute("usernameError", "A user with the same name already exist!");
            return "registration";
        }
        return "redirect:/";
    }
}