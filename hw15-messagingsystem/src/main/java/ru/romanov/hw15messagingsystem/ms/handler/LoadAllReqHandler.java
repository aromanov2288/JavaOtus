package ru.romanov.hw15messagingsystem.ms.handler;

import lombok.RequiredArgsConstructor;
import ru.romanov.hw15messagingsystem.model.domain.Message;
import ru.romanov.hw15messagingsystem.ms.api.MsHandler;
import ru.romanov.hw15messagingsystem.ms.utils.Serializers;
import ru.romanov.hw15messagingsystem.orm.api.service.DBService;

import java.util.Optional;

import static ru.romanov.hw15messagingsystem.ms.MessageType.LOAD_ALL_RESP;

@RequiredArgsConstructor
public class LoadAllReqHandler implements MsHandler {

    private final DBService dbService;

    @Override
    public Optional<Message> handle(Message msg) {
        Class tClass = Serializers.deserialize(msg.getPayload(), Class.class);
        byte[] payload = Serializers.serialize(dbService.loadAll(tClass));
        return Optional.of(new Message(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), LOAD_ALL_RESP.getName(), payload));
    }
}
