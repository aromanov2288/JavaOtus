package ru.romanov.hw16multiprocess.messagingsystem.socket;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.romanov.hw16multiprocess.common.model.ServiceType;
import ru.romanov.hw16multiprocess.common.model.SocketMessageType;
import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;
import ru.romanov.hw16multiprocess.common.model.dto.SocketMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static ru.romanov.hw16multiprocess.common.model.SocketMessageType.REQUEST;
import static ru.romanov.hw16multiprocess.common.model.SocketMessageType.STATUS;

@Slf4j
@RequiredArgsConstructor
public class Client {
    private static final String STATUS_OK = "OK";
    private static final Gson gson = new Gson();

    private final Integer currentPort;
    private static final String SERVICES_SERVER_HOST = "localhost";
    private final ServiceType serviceType;

    public boolean newMessage(Integer port, MsMessage msMessage) {
        SocketMessage socketMessage = new SocketMessage(REQUEST.getName(), msMessage, null, currentPort, serviceType);
        return sendMessage(port, socketMessage);
    }

    public boolean sendMessage(Integer port, SocketMessage request) {
        boolean result = false;
        String strRequest = gson.toJson(request);
        try {
            try (Socket clientSocket = new Socket(SERVICES_SERVER_HOST, port)) {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                sendRequest(out, port, strRequest);
                SocketMessage response = receiveResponse(in, port);

                if (STATUS.getName().equals(response.getType())
                        && STATUS_OK.equals(response.getStatus())) {
                    result = true;
                }

                sendRequest(out, port, getStrStopRequest());
                receiveResponse(in, port);
            }
        } catch (Exception ex) {
            log.error("error", ex);
        }
        return result;
    }

    private void sendRequest(PrintWriter out, Integer port, String request) {
        out.println(request);
        log.info("sent message to service (port={}) : {}", port, request);
    }

    private SocketMessage receiveResponse(BufferedReader in, Integer port) throws IOException {
        String strResponse = in.readLine();
        log.info("received message from service (port={}) : {}", port, strResponse);
        return gson.fromJson(strResponse, SocketMessage.class);
    }

    private String getStrStopRequest() {
        SocketMessage stopMessage = new SocketMessage(SocketMessageType.STOP.getName(), null, null, currentPort, serviceType);
        return gson.toJson(stopMessage);
    }
}
