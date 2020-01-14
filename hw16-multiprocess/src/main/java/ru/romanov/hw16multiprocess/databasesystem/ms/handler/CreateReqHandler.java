package ru.romanov.hw16multiprocess.databasesystem.ms.handler;

import lombok.RequiredArgsConstructor;
import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;
import ru.romanov.hw16multiprocess.common.model.domain.User;
import ru.romanov.hw16multiprocess.common.ms.api.MsHandler;
import ru.romanov.hw16multiprocess.common.ms.utils.Serializers;
import ru.romanov.hw16multiprocess.databasesystem.orm.api.service.DBService;

import java.util.Optional;

import static ru.romanov.hw16multiprocess.common.ms.MessageType.CREATE_RESP;

@RequiredArgsConstructor
public class CreateReqHandler implements MsHandler {

    private final DBService dbService;

    @Override
    public Optional<MsMessage> handle(MsMessage msg) {
        User user = Serializers.deserialize(msg.getPayload(), User.class);
        byte[] payload = Serializers.serialize(dbService.create(user));
        return Optional.of(new MsMessage(msg.getTo(), msg.getFrom(), Optional.of(msg.getId()), CREATE_RESP.getName(), payload));
    }
}
