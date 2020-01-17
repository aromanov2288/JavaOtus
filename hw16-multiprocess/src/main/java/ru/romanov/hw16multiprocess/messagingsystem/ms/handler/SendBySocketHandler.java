package ru.romanov.hw16multiprocess.messagingsystem.ms.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;
import ru.romanov.hw16multiprocess.common.ms.api.MsHandler;
import ru.romanov.hw16multiprocess.messagingsystem.socket.Client;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class SendBySocketHandler implements MsHandler {

    private final Client client;
    private final Integer sendToPort;

    @Override
    public Optional<MsMessage> handle(MsMessage msg) {
        client.newMessage(sendToPort, msg);
        return Optional.empty();
    }
}
