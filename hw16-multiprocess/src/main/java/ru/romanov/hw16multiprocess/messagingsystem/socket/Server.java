package ru.romanov.hw16multiprocess.messagingsystem.socket;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.romanov.hw16multiprocess.common.model.ServiceType;
import ru.romanov.hw16multiprocess.common.model.dto.SocketMessage;
import ru.romanov.hw16multiprocess.common.ms.MessageType;
import ru.romanov.hw16multiprocess.common.ms.api.MessageSystem;
import ru.romanov.hw16multiprocess.common.ms.api.MsClient;
import ru.romanov.hw16multiprocess.messagingsystem.ms.handler.SendBySocketHandler;
import ru.romanov.hw16multiprocess.messagingsystem.ms.impl.MsClientImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static ru.romanov.hw16multiprocess.common.model.ServiceType.DATABASE;
import static ru.romanov.hw16multiprocess.common.model.ServiceType.FRONTEND;
import static ru.romanov.hw16multiprocess.common.model.SocketMessageType.REGISTER_SERVICE;
import static ru.romanov.hw16multiprocess.common.model.SocketMessageType.REQUEST;
import static ru.romanov.hw16multiprocess.common.model.SocketMessageType.STATUS;
import static ru.romanov.hw16multiprocess.common.model.SocketMessageType.STOP;
import static ru.romanov.hw16multiprocess.common.model.SocketMessageType.UNREGISTER_SERVICE;

@Slf4j
@RequiredArgsConstructor
public class Server {
    private static final String STATUS_OK = "OK";
    private static final Gson gson = new Gson();

    private final Client client;
    private final Integer currentPort;
    private final MessageSystem messageSystem;
    private final ServiceType serviceType;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(currentPort)) {
            while (!Thread.currentThread().isInterrupted()) {
                log.info("waiting for client connection\n");
                try (Socket clientSocket = serverSocket.accept()) {
                    clientHandler(clientSocket);
                }
            }
        } catch (Exception ex) {
            log.error("error", ex);
        }
    }

    private void clientHandler(Socket clientSocket) {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String strRequest;
            SocketMessage request;
            while (true){
                strRequest = in.readLine();
                if (strRequest != null) {
                    log.info("received message from Client: {}", strRequest);
                    request = gson.fromJson(strRequest, SocketMessage.class);

                    SocketMessage response = new SocketMessage(STATUS.getName(), null, STATUS_OK, null, serviceType);
                    String strResponse = gson.toJson(response);
                    out.println(strResponse);
                    log.info("sent message to Client: {}", strResponse);

                    if (STOP.getName().equals(request.getType())) {
                        break;
                    }

                    if (REGISTER_SERVICE.getName().equals(request.getType())) {
                        MsClient msClient = new MsClientImpl(request.getFromServiceType(), messageSystem);
                        SendBySocketHandler handler = new SendBySocketHandler(client, request.getFromServicePort());
                        if (FRONTEND.getName().equals(request.getFromServiceType())) {
                            msClient.addHandler(MessageType.CREATE_RESP, handler);
                            msClient.addHandler(MessageType.LOAD_ALL_RESP, handler);
                        } else if (DATABASE.getName().equals(request.getFromServiceType())) {
                            msClient.addHandler(MessageType.CREARE_REQ, handler);
                            msClient.addHandler(MessageType.LOAD_ALL_REQ, handler);
                        }
                        messageSystem.addClient(msClient);
                    } else if (UNREGISTER_SERVICE.getName().equals(request.getType())) {
                        messageSystem.removeClient(request.getFromServiceType());
                    } else if (REQUEST.getName().equals(request.getType())) {
                        messageSystem.newMessage(request.getPayload());
                    }
                }
            }
        } catch (Exception ex) {
            log.error("error", ex);
        }
    }
}
