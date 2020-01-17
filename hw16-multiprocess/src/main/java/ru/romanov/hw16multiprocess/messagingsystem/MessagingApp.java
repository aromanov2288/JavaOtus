package ru.romanov.hw16multiprocess.messagingsystem;

import ru.romanov.hw16multiprocess.common.model.ServiceType;
import ru.romanov.hw16multiprocess.common.ms.api.MessageSystem;
import ru.romanov.hw16multiprocess.messagingsystem.socket.Client;
import ru.romanov.hw16multiprocess.messagingsystem.socket.Server;
import ru.romanov.hw16multiprocess.messagingsystem.ms.impl.MessageSystemImpl;

public class MessagingApp {

    private static final Integer CURRENT_PORT = 8280;
    private static final ServiceType SERVICE_TYPE = ServiceType.MESSAGING_SYSTEM;

    public static void main(String[] args) {
        Client client = new Client(CURRENT_PORT, SERVICE_TYPE);
        MessageSystem ms = new MessageSystemImpl();
        Server server = new Server(client, CURRENT_PORT, ms, SERVICE_TYPE);
        server.start();
    }
}
