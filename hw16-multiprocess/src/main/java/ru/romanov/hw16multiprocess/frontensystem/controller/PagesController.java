package ru.romanov.hw16multiprocess.frontensystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/")
    public String getIndexPage() {
        return "index.html";
    }

    @GetMapping("/add_user")
    public String getAddUserPage() {
        return "addUser.html";
    }

    @GetMapping("/all_users")
    public String getAllUserPage() {
        return "allUsers.html";
    }
}
