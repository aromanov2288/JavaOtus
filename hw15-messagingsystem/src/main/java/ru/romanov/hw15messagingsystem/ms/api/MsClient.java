package ru.romanov.hw15messagingsystem.ms.api;

import ru.romanov.hw15messagingsystem.model.domain.Message;
import ru.romanov.hw15messagingsystem.ms.MessageType;

public interface MsClient {

    void addHandler(MessageType type, MsHandler msHandler);

    boolean sendMessage(Message msg);

    void handle(Message msg);

    String getName();

    <T> Message produceMessage(String to, T data, MessageType msgType);

}
