package ru.romanov.hw09jdbcbasedorm.api.service;

import ru.romanov.hw09jdbcbasedorm.api.dao.Dao;
import ru.romanov.hw09jdbcbasedorm.api.sessionmanager.SessionManager;

import java.util.Optional;

public class DBServiceImpl implements DBService {

    private Dao dao;

    public DBServiceImpl(Dao dao) {
        this.dao = dao;
    }

    @Override
    public void createTable(Class entityClass) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.createTable(entityClass);
                sessionManager.commitSession();
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void dropTable(Class entityClass) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                dao.dropTable(entityClass);
                sessionManager.commitSession();
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public <T> long create(T entity) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long id = dao.create(entity);
                sessionManager.commitSession();
                return id;
            } catch (Exception e) {
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
    public <T> Optional<T> loadById(long id, Class entityClass) {
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
