package ru.romanov.hw11cache.cache;

public interface CacheActionListener<K, V> {
    void notify(K key, V value, CacheAction action);
}
