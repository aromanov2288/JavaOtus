package ru.romanov.hw13serverspring.orm.api.service;

import ru.romanov.hw13serverspring.orm.api.dao.Dao;
import ru.romanov.hw13serverspring.orm.api.exception.DbServiceException;
import ru.romanov.hw13serverspring.orm.api.sessionmanager.SessionManager;
import ru.romanov.hw13serverspring.orm.cache.CacheImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBServiceImpl implements DBService {

    private Dao dao;
    private CacheImpl cache = new CacheImpl();

    public DBServiceImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public <T> T create(T entity) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                T t = dao.create(entity);
                sessionManager.commitSession();
                return t;
            } catch (Exception e) {
                e.printStackTrace();
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public <T> void update(T entity) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.update(entity);
                sessionManager.commitSession();
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public <T> Optional<T> loadById(long id, Class<T> entityClass) {
        if (cache.get(id, entityClass) != null) {
            return Optional.ofNullable((T) cache.get(id, entityClass));
        }
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<T> optional = dao.loadById(id, entityClass);
                optional.ifPresent(element -> cache.put(id, element));
                return optional;
            } catch (Exception e) {
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public <T> List<T> loadAll(Class<T> entityClass) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return dao.loadAll(entityClass);
            } catch (Exception e) {
                sessionManager.rollbackSession();
            }
            return new ArrayList<>();
        }
    }
}
