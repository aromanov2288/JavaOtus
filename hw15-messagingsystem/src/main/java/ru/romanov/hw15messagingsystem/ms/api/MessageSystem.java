package ru.romanov.hw15messagingsystem.ms.api;

import ru.romanov.hw15messagingsystem.model.domain.Message;

public interface MessageSystem {

    void addClient(MsClient msClient);

    void removeClient(String clientId);

    boolean newMessage(Message msg);

    void dispose() throws InterruptedException;
}

