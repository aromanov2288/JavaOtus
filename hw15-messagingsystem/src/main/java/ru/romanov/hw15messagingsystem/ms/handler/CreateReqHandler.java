package ru.romanov.hw15messagingsystem.ms.handler;

import lombok.RequiredArgsConstructor;
import ru.romanov.hw15messagingsystem.model.domain.Message;
import ru.romanov.hw15messagingsystem.model.domain.User;
import ru.romanov.hw15messagingsystem.ms.api.MsHandler;
import ru.romanov.hw15messagingsystem.ms.utils.Serializers;
import ru.romanov.hw15messagingsystem.orm.api.service.DBService;

import java.util.Optional;

import static ru.romanov.hw15messagingsystem.ms.MessageType.CREATE_RESP;

@RequiredArgsConstructor
public class CreateReqHandler implements MsHandler {

    private final DBService dbService;

    @Override
    public Optional<Message> handle(Message msg) {
        User user = Serializers.deserialize(msg.getPayload(), User.class);
        byte[] payload = Serializers.serialize(dbService.create(user));
        return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), CREATE_RESP.getName(), payload));
    }
}
