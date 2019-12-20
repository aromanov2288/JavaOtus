package ru.romanov.hw13serverspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping({"/", "/index"})
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/add_user")
    public String getAddUserPage() {
        return "addUser";
    }

    @GetMapping("/all_users")
    public String getAllUserPage() {
        return "allUsers";
    }
}
