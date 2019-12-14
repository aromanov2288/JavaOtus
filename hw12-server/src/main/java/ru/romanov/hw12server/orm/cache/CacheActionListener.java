package ru.romanov.hw12server.orm.cache;

public interface CacheActionListener<K, V> {
    void notify(K key, V value, CacheAction action);
}
