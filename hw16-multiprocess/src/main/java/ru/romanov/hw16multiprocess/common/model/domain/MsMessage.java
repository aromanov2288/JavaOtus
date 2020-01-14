package ru.romanov.hw16multiprocess.common.model.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@ToString
public class MsMessage {
    public static final MsMessage VOID_MS_MESSAGE = new MsMessage();

    private final UUID id = UUID.randomUUID();
    private final String from;
    private final String to;
    private final Optional<UUID> sourceMessageId;
    private final String type;
    private final int payloadLength;
    private final byte[] payload;

    private MsMessage() {
        this.from = null;
        this.to = null;
        this.sourceMessageId = Optional.empty();
        this.type = "voidTechnicalMessage";
        this.payload = new byte[1];
        this.payloadLength = payload.length;
    }

    public MsMessage(String from, String to, Optional<UUID> sourceMessageId, String type, byte[] payload) {
        this.from = from;
        this.to = to;
        this.sourceMessageId = sourceMessageId;
        this.type = type;
        this.payloadLength = payload.length;
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MsMessage msMessage = (MsMessage) o;
        return id == msMessage.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
