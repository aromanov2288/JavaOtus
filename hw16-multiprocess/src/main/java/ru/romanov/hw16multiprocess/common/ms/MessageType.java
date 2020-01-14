package ru.romanov.hw16multiprocess.common.ms;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageType {
    LOAD_ALL_REQ("loadAllReq"),
    LOAD_ALL_RESP("loadAllResp"),
    CREARE_REQ("createReq"),
    CREATE_RESP("createResp");

    private final String name;
}
