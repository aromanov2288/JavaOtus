package ru.romanov.hw16multiprocess.common.model;

public enum SocketMessageType {
    REGISTER_SERVICE("REGISTER_SERVICE"),
    UNREGISTER_SERVICE("UNREGISTER_SERVICE"),
    REQUEST("REQUEST"),
    STATUS("STATUS"),
    STOP("STOP");

    private String name;

    SocketMessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
