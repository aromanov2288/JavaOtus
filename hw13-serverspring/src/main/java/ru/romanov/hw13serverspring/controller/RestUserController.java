package ru.romanov.hw13serverspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanov.hw13serverspring.model.User;
import ru.romanov.hw13serverspring.model.UserDto;
import ru.romanov.hw13serverspring.orm.api.service.DBService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestUserController {

    private final DBService dbService;

    public RestUserController(DBService dbService) {
        this.dbService = dbService;
    }

    @PostMapping("/add_user")
    public void addUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.getName(), userDto.getAge());
        dbService.create(user);
    }

    @GetMapping("/all_users")
    public List<User> getAllUsers() {
        return dbService.loadAll(User.class);
    }
}
