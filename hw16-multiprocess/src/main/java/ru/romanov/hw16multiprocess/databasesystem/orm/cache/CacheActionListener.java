package ru.romanov.hw16multiprocess.databasesystem.orm.cache;

public interface CacheActionListener<K, V> {
    void notify(K key, V value, CacheAction action);
}
