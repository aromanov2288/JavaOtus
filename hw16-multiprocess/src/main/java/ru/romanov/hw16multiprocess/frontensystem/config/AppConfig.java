package ru.romanov.hw16multiprocess.frontensystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romanov.hw16multiprocess.common.model.ServiceType;
import ru.romanov.hw16multiprocess.common.ms.MessageType;
import ru.romanov.hw16multiprocess.common.ms.api.MsClient;
import ru.romanov.hw16multiprocess.frontensystem.front.FeService;
import ru.romanov.hw16multiprocess.frontensystem.front.FeUserImpl;
import ru.romanov.hw16multiprocess.frontensystem.ms.handler.CreateRespHandler;
import ru.romanov.hw16multiprocess.frontensystem.ms.handler.LoadAllRespHandler;
import ru.romanov.hw16multiprocess.frontensystem.ms.impl.MsClientImpl;
import ru.romanov.hw16multiprocess.frontensystem.socket.Client;
import ru.romanov.hw16multiprocess.frontensystem.socket.Server;

@Configuration
public class AppConfig {

    private static Integer CURRENT_PORT = 8180;
    private static final String MS_SERVER_HOST = "localhost";
    private static final Integer MS_SERVER_PORT = 8280;
    private static ServiceType SERVICE_TYPE = ServiceType.FRONTEND;

    @Bean
    public Client client() {
        return new Client(CURRENT_PORT, MS_SERVER_HOST, MS_SERVER_PORT, SERVICE_TYPE);
    }

    @Bean
    public MsClient msClient(Client socketClient) {
        return new MsClientImpl(SERVICE_TYPE.getName(), socketClient);
    }

    @Bean
    public Server server(MsClient msClient) {
        return new Server(CURRENT_PORT, msClient, SERVICE_TYPE);
    }

    @Bean
    public FeService feService(MsClient msClient) {
        FeService feService = new FeUserImpl(msClient);
        msClient.addHandler(MessageType.LOAD_ALL_RESP, new LoadAllRespHandler(feService));
        msClient.addHandler(MessageType.CREATE_RESP, new CreateRespHandler(feService));
        return feService;
    }

}
