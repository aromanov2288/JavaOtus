package ru.romanov.hw15messagingsystem.ms.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.romanov.hw15messagingsystem.front.FeService;
import ru.romanov.hw15messagingsystem.model.domain.Message;
import ru.romanov.hw15messagingsystem.model.domain.User;
import ru.romanov.hw15messagingsystem.ms.api.MsHandler;
import ru.romanov.hw15messagingsystem.ms.utils.Serializers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class LoadAllRespHandler implements MsHandler {

    private final FeService feService;

    @Override
    public Optional<Message> handle(Message msg) {
        log.info("new message:{}", msg);
        try {
            List<User> data = Serializers.deserialize(msg.getPayload(), List.class);
            UUID sourceMessageId =
                    msg.getSourceMessageId().orElseThrow(() -> new RuntimeException("Not found sourceMsg for message:" + msg.getId()));
            feService.takeConsumer(sourceMessageId, List.class).ifPresent(consumer -> consumer.accept(data));

        } catch (Exception ex) {
            log.error("msg:" + msg, ex);
        }
        return Optional.empty();
    }
}
