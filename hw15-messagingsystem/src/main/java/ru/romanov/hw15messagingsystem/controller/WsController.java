package ru.romanov.hw15messagingsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.romanov.hw15messagingsystem.front.FeService;
import ru.romanov.hw15messagingsystem.model.WsMessageType;
import ru.romanov.hw15messagingsystem.model.domain.User;
import ru.romanov.hw15messagingsystem.model.dto.UserDto;
import ru.romanov.hw15messagingsystem.model.dto.WsMessage;

import static ru.romanov.hw15messagingsystem.model.WsMessageType.RESPONSE_ADD_USER_PAGE;
import static ru.romanov.hw15messagingsystem.model.WsMessageType.RESPONSE_ALL_USERS_PAGE;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WsController {

    private final String DST = "/topic/answer";
    private final FeService feService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/server")
    public void receiveMessage(@Payload WsMessage message) {
        if (message.getType().equals(WsMessageType.JOIN_ALL_USERS_PAGE.toString())) {
            feService.getAll(User.class, usersList ->
                    simpMessagingTemplate.convertAndSend(DST, new WsMessage(RESPONSE_ALL_USERS_PAGE.getName(), null, null, usersList))
            );
        } else if (message.getType().equals(WsMessageType.JOIN_ADD_USER_PAGE.toString())) {
            UserDto userDto = message.getDataForCreate();
            feService.create(new User(userDto.getName(), userDto.getAge()), user ->
                    simpMessagingTemplate.convertAndSend(DST, new WsMessage(RESPONSE_ADD_USER_PAGE.getName(), null, user, null))
            );
        }
    }
}
