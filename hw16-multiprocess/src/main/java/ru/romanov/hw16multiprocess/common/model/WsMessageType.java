package ru.romanov.hw16multiprocess.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WsMessageType {
    JOIN_ALL_USERS_PAGE("JOIN_ALL_USERS_PAGE"),
    RESPONSE_ALL_USERS_PAGE("RESPONSE_ALL_USERS_PAGE"),
    JOIN_ADD_USER_PAGE("JOIN_ADD_USER_PAGE"),
    RESPONSE_ADD_USER_PAGE("RESPONSE_ADD_USER_PAGE");

    private String name;
}
