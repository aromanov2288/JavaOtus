package ru.romanov.hw15messagingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  WsMessageType {
    JOIN_ALL_USERS_PAGE("JOIN_ALL_USERS_PAGE"),
    RESPONSE_ALL_USERS_PAGE("RESPONSE_ALL_USERS_PAGE"),
    JOIN_ADD_USER_PAGE("JOIN_ADD_USER_PAGE"),
    RESPONSE_ADD_USER_PAGE("RESPONSE_ADD_USER_PAGE");

    private String name;
}
