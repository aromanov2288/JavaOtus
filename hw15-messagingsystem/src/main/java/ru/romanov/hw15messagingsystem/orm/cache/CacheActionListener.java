package ru.romanov.hw15messagingsystem.orm.cache;

public interface CacheActionListener<K, V> {
    void notify(K key, V value, CacheAction action);
}
