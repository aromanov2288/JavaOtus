package ru.romanov.hw15messagingsystem.orm.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum  CacheAction {
    PUT("Элемент добавлен в кэш"),
    GET("Элемент получен из кэш"),
    REMOVE("Элемент удален из кэш");

    private String title;
}
