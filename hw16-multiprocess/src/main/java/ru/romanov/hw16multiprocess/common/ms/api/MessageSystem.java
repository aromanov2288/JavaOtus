package ru.romanov.hw16multiprocess.common.ms.api;

import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;

public interface MessageSystem {

    boolean addDatabaseClient(Integer clientPort);

    boolean removeDatabaseClient(Integer clientId);

    void newMessage(Integer clientFromPort, MsMessage msg);

    void dispose() throws InterruptedException;
}

