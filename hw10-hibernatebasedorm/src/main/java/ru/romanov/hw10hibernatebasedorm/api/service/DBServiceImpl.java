package ru.romanov.hw10hibernatebasedorm.api.service;


import ru.romanov.hw10hibernatebasedorm.api.dao.Dao;
import ru.romanov.hw10hibernatebasedorm.api.sessionmanager.SessionManager;

import java.util.Optional;

public class DBServiceImpl implements DBService {

    private Dao dao;

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
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return dao.loadById(id, entityClass);
            } catch (Exception e) {
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
