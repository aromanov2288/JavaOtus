package ru.romanov.hw16multiprocess.common.ms.api;

import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;

import java.util.Optional;

public interface MsHandler {
    Optional<MsMessage> handle(MsMessage msg);
}
