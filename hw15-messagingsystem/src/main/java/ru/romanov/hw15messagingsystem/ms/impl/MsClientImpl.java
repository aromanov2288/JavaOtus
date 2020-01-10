package ru.romanov.hw15messagingsystem.ms.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.romanov.hw15messagingsystem.model.domain.Message;
import ru.romanov.hw15messagingsystem.ms.MessageType;
import ru.romanov.hw15messagingsystem.ms.api.MessageSystem;
import ru.romanov.hw15messagingsystem.ms.api.MsClient;
import ru.romanov.hw15messagingsystem.ms.api.MsHandler;
import ru.romanov.hw15messagingsystem.ms.utils.Serializers;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class MsClientImpl implements MsClient {
    private final String name;
    private final MessageSystem messageSystem;
    private final Map<String, MsHandler> handlers = new ConcurrentHashMap<>();

    @Override
    public void addHandler(MessageType type, MsHandler msHandler) {
        this.handlers.put(type.getName(), msHandler);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean sendMessage(Message msg) {
        boolean result = messageSystem.newMessage(msg);
        if (!result) {
            log.error("the last message was rejected: {}", msg);
        }
        return result;
    }

    @Override
    public void handle(Message msg) {
        log.info("new message:{}", msg);
        try {
            MsHandler msHandler = handlers.get(msg.getType());
            if (msHandler != null) {
                msHandler.handle(msg).ifPresent(this::sendMessage);
            } else {
                log.error("handler not found for the message type:{}", msg.getType());
            }
        } catch (Exception ex) {
            log.error("msg:" + msg, ex);
        }
    }

    @Override
    public <T> Message produceMessage(String to, T data, MessageType msgType) {
        return new Message(name, to, null, msgType.getName(), Serializers.serialize(data));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MsClientImpl msClient = (MsClientImpl) o;
        return Objects.equals(name, msClient.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
