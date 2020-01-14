package ru.romanov.hw16multiprocess.databasesystem.ms.handler;

import lombok.RequiredArgsConstructor;
import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;
import ru.romanov.hw16multiprocess.common.ms.api.MsHandler;
import ru.romanov.hw16multiprocess.common.ms.utils.Serializers;
import ru.romanov.hw16multiprocess.databasesystem.orm.api.service.DBService;

import java.util.Optional;

import static ru.romanov.hw16multiprocess.common.ms.MessageType.LOAD_ALL_RESP;

@RequiredArgsConstructor
public class LoadAllReqHandler implements MsHandler {

    private final DBService dbService;

    @Override
    public Optional<MsMessage> handle(MsMessage msg) {
        Class tClass = Serializers.deserialize(msg.getPayload(), Class.class);
        byte[] payload = Serializers.serialize(dbService.loadAll(tClass));
        return Optional.of(new MsMessage(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), LOAD_ALL_RESP.getName(), payload));
    }
}
