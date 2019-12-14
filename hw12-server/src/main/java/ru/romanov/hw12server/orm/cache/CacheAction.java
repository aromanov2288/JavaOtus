package ru.romanov.hw12server.orm.cache;

public enum  CacheAction {
    PUT("Элемент добавлен в кэш"),
    GET("Элемент получен из кэш"),
    REMOVE("Элемент удален из кэш");

    private String title;

    CacheAction(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
