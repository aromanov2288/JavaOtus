package ru.romanov.hw16multiprocess.common.model;

public enum ServiceType {
    FRONTEND("frontend"),
    MESSAGING_SYSTEM("messagingSystem"),
    DATABASE("database");

    private String name;

    ServiceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ServiceType valueOfName(String name) {
        for (ServiceType type : values()) {
            if (name.equals(type.getName())) {
                return type;
            }
        }
        return null;
    }
}
