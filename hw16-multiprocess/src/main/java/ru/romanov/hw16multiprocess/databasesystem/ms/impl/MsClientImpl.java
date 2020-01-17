package ru.romanov.hw16multiprocess.databasesystem.ms.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;
import ru.romanov.hw16multiprocess.common.ms.MessageType;
import ru.romanov.hw16multiprocess.common.ms.api.MsClient;
import ru.romanov.hw16multiprocess.common.ms.api.MsHandler;
import ru.romanov.hw16multiprocess.common.ms.utils.Serializers;
import ru.romanov.hw16multiprocess.databasesystem.socket.Client;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class MsClientImpl implements MsClient {
    private final String name;
    private final Client socketClient;
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
    public boolean sendMessage(MsMessage msg) {
        boolean result = socketClient.newMessage(msg);
        if (!result) {
            log.error("the last message was rejected: {}", msg);
        }
        return result;
    }

    @Override
    public void handle(MsMessage msg) {
        log.info("message processed: {}", msg);
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
    public <T> MsMessage produceMessage(String to, T data, MessageType msgType) {
        return new MsMessage(name, to, null, msgType.getName(), Serializers.serialize(data));
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
