package ru.romanov.hw15messagingsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.romanov.hw15messagingsystem.model.domain.User;
import ru.romanov.hw15messagingsystem.model.dto.UserDto;
import ru.romanov.hw15messagingsystem.orm.api.service.DBService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestsController {

    private final DBService dbService;

    @PostMapping("/add_user")
    public void addUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.getName(), userDto.getAge());
        dbService.create(user);
    }
}
