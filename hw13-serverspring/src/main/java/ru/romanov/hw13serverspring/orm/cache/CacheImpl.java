package ru.romanov.hw13serverspring.orm.cache;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class CacheImpl implements Cache {

    private Map<Class, Map<Long, Object>> dataMap = new WeakHashMap<>();
    private Set<CacheActionListener> listenerSet = new HashSet<>();

    @Override
    public void put(Long key, Object value) {
        listenerSet.forEach(listener -> listener.notify(key, value, CacheAction.PUT));
        Class entityClass = value.getClass();
        if (!dataMap.containsKey(entityClass)) {
            dataMap.put(entityClass, new WeakHashMap<>());
        }
        dataMap.get(entityClass).put(new Long(key), value);
    }

    @Override
    public void remove(Long key, Class entityClass) {
        listenerSet.forEach(listener -> listener.notify(key, dataMap.get(key), CacheAction.REMOVE));
        if (dataMap.containsKey(entityClass)) {
            dataMap.get(entityClass).remove(key);
        }
    }

    @Override
    public Object get(Long key, Class entityClass) {
        listenerSet.forEach(listener -> listener.notify(key, dataMap.get(key), CacheAction.GET));
        if (dataMap.containsKey(entityClass)) {
            return dataMap.get(entityClass).get(key);
        } else {
            return null;
        }
    }

    @Override
    public void addListener(CacheActionListener listener) {
        listenerSet.add(listener);
    }

    @Override
    public void removeListener(CacheActionListener listener) {
        listenerSet.remove(listener);
    }
}
