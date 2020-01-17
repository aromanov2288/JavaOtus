package ru.romanov.hw16multiprocess.frontensystem.front;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.romanov.hw16multiprocess.common.model.ServiceType;
import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;
import ru.romanov.hw16multiprocess.common.ms.MessageType;
import ru.romanov.hw16multiprocess.common.ms.api.MsClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class FeUserImpl implements FeService {

    private final Map<UUID, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
    private final MsClient msClient;
    private final String databaseServiceClientName = ServiceType.DATABASE.getName();

    @Override
    public <T> void getAll(Class<T> tClass, Consumer<List> dataConsumer) {
        MsMessage outMsg = msClient.produceMessage(databaseServiceClientName, tClass, MessageType.LOAD_ALL_REQ);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
    public <T> void create(T entity, Consumer<T> dataConsumer) {
        MsMessage outMsg = msClient.produceMessage(databaseServiceClientName, entity, MessageType.CREARE_REQ);
        consumerMap.put(outMsg.getId(), dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
    public <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass) {
        Consumer<T> consumer = (Consumer<T>) consumerMap.remove(sourceMessageId);
        if (consumer == null) {
            log.warn("consumer not found for:{}", sourceMessageId);
            return Optional.empty();
        }
        return Optional.of(consumer);
    }
}
