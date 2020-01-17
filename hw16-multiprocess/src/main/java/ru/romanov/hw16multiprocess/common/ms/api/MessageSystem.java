package ru.romanov.hw16multiprocess.common.ms.api;

import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;

public interface MessageSystem {

    void addClient(MsClient msClient);

    void removeClient(String clientId);

    boolean newMessage(MsMessage msg);

    void dispose() throws InterruptedException;
}

