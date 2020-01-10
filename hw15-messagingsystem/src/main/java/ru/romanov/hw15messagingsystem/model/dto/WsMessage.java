package ru.romanov.hw15messagingsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.romanov.hw15messagingsystem.model.domain.User;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class WsMessage {
    private String type;
    private UserDto dataForCreate;
    private User createdData;
    private List<User> dataCollection;
}
