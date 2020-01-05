package ru.romanov.hw15messagingsystem.front;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.romanov.hw15messagingsystem.model.domain.Message;
import ru.romanov.hw15messagingsystem.ms.api.MsClient;
import ru.romanov.hw15messagingsystem.ms.MessageType;

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
  private final String databaseServiceClientName;

  @Override
  public <T> void getAll(Class<T> tClass, Consumer<List> dataConsumer) {
    Message outMsg = msClient.produceMessage(databaseServiceClientName, tClass, MessageType.LOAD_ALL_REQ);
    consumerMap.put(outMsg.getId(), dataConsumer);
    msClient.sendMessage(outMsg);
  }

  @Override
  public <T> void create(T entity, Consumer<T> dataConsumer) {
    Message outMsg = msClient.produceMessage(databaseServiceClientName, entity, MessageType.CREARE_REQ);
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
