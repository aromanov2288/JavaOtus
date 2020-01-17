package ru.romanov.hw16multiprocess.databasesystem.socket;

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

import static ru.romanov.hw16multiprocess.common.model.SocketMessageType.REGISTER_SERVICE;
import static ru.romanov.hw16multiprocess.common.model.SocketMessageType.REQUEST;
import static ru.romanov.hw16multiprocess.common.model.SocketMessageType.STATUS;
import static ru.romanov.hw16multiprocess.common.model.SocketMessageType.UNREGISTER_SERVICE;

@Slf4j
@RequiredArgsConstructor
public class Client {
    private static final String STATUS_OK = "OK";
    private static final Gson gson = new Gson();

    private final Integer currentPort;
    private final String msServerHost;
    private final int msServerPort;
    private final ServiceType serviceType;

    public boolean newMessage(MsMessage msMessage) {
        SocketMessage socketMessage = new SocketMessage(REQUEST.getName(), msMessage, null, currentPort, serviceType);
        return sendMessage(gson.toJson(socketMessage));
    }

    public boolean registerService() {
        SocketMessage socketMessage = new SocketMessage(REGISTER_SERVICE.getName(), null, null, currentPort, serviceType);
        return sendMessage(gson.toJson(socketMessage));
    }

    public boolean unregisterService() {
        SocketMessage socketMessage = new SocketMessage(UNREGISTER_SERVICE.getName(), null, null, currentPort, serviceType);
        return sendMessage(gson.toJson(socketMessage));
    }

    private boolean sendMessage(String strRequest) {
        boolean result = false;
        try {
            try (Socket clientSocket = new Socket(msServerHost, msServerPort)) {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                sendRequest(out, strRequest);
                SocketMessage response = receiveResponse(in);

                if (STATUS.getName().equals(response.getType()) && STATUS_OK.equals(response.getStatus())) {
                    result = true;
                }

                sendRequest(out, getStrStopRequest());
                receiveResponse(in);
            }
        } catch (Exception ex) {
            log.error("error", ex);
        }
        return result;
    }

    private void sendRequest(PrintWriter out, String request) {
        out.println(request);
        log.info("sent message to Messaging System: {}", request);
    }

    private SocketMessage receiveResponse(BufferedReader in) throws IOException {
        String strResponse = in.readLine();
        log.info("received message from Messaging System: {}", strResponse);
        return gson.fromJson(strResponse, SocketMessage.class);
    }

    private String getStrStopRequest() {
        SocketMessage stopMessage = new SocketMessage(SocketMessageType.STOP.getName(), null, null, currentPort, serviceType);
        return gson.toJson(stopMessage);
    }
}
