package ru.romanov.hw16multiprocess.common.ms.api;

import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;
import ru.romanov.hw16multiprocess.common.ms.MessageType;

public interface MsClient {

    void addHandler(MessageType type, MsHandler msHandler);

    boolean sendMessage(MsMessage msg);

    void handle(MsMessage msg);

    String getName();

    <T> MsMessage produceMessage(String to, T data, MessageType msgType);

}
