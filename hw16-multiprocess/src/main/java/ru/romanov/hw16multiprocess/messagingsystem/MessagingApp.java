package ru.romanov.hw16multiprocess.messagingsystem;

import ru.romanov.hw16multiprocess.common.model.ServiceType;
import ru.romanov.hw16multiprocess.common.ms.api.MessageSystem;
import ru.romanov.hw16multiprocess.messagingsystem.socket.Client;
import ru.romanov.hw16multiprocess.messagingsystem.socket.Server;
import ru.romanov.hw16multiprocess.messagingsystem.ms.impl.MessageSystemImpl;

public class MessagingApp {

    private static final Integer currentPort = 8280;
    private static final ServiceType SERVICE_TYPE = ServiceType.MESSAGING_SYSTEM;

    public static void main(String[] args) {
        Client client = new Client(currentPort, SERVICE_TYPE);
        MessageSystem ms = new MessageSystemImpl(client);
        Server server = new Server(currentPort, ms, SERVICE_TYPE);
        server.start();
    }
}
