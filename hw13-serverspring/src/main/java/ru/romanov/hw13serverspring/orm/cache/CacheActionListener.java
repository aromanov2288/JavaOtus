package ru.romanov.hw13serverspring.orm.cache;

public interface CacheActionListener<K, V> {
    void notify(K key, V value, CacheAction action);
}
