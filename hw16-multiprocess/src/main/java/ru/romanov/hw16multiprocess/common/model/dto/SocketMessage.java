package ru.romanov.hw16multiprocess.common.model.dto;

import lombok.Getter;
import lombok.Setter;
import ru.romanov.hw16multiprocess.common.model.ServiceType;
import ru.romanov.hw16multiprocess.common.model.domain.MsMessage;

@Getter
@Setter
public class SocketMessage {
    private String type;
    private MsMessage payload;
    private String status;
    private Integer fromServicePort;
    private String fromServiceType;

    public SocketMessage(String type, MsMessage payload, String status, Integer fromServicePort, ServiceType fromServiceType) {
        this.type = type;
        this.payload = payload;
        this.status = status;
        this.fromServicePort = fromServicePort;
        this.fromServiceType = fromServiceType.getName();
    }
}
