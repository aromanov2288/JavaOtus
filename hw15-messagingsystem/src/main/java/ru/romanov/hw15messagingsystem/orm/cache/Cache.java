package ru.romanov.hw15messagingsystem.orm.cache;

public interface Cache {

    void put(Long key, Object value);

    void remove(Long key, Class entityClass);

    Object get(Long key, Class entityClass);

    void addListener(CacheActionListener listener);

    void removeListener(CacheActionListener listener);
}
