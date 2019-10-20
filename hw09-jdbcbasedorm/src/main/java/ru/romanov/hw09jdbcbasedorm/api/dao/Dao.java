package ru.romanov.hw09jdbcbasedorm.api.dao;

import ru.romanov.hw09jdbcbasedorm.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface Dao {

    void createTable(Class entityClass) throws DaoException;

    void dropTable(Class entityClass) throws DaoException;

    <T> long create(T entity) throws DaoException;

    <T> void update(T entity) throws DaoException;

    <T> Optional<T> loadById(long id, Class entityClass) throws DaoException;

    SessionManager getSessionManager();
}
