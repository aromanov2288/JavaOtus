package ru.romanov.hw16multiprocess.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.romanov.hw16multiprocess.common.model.domain.User;

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
