package ru.romanov.hw15messagingsystem.ms.api;


import ru.romanov.hw15messagingsystem.model.domain.Message;

import java.util.Optional;

public interface MsHandler {
    Optional<Message> handle(Message msg);
}
