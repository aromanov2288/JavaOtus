package ru.romanov.hw15messagingsystem.front;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public interface FeService {
  <T> void getAll(Class<T> tClass, Consumer<List> dataConsumer);

  <T> void create(T entity, Consumer<T> dataConsumer);

  <T> Optional<Consumer<T>> takeConsumer(UUID sourceMessageId, Class<T> tClass);
}

