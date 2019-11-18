package ru.romanov.hw10hibernatebasedorm.api.dao;

import ru.romanov.hw10hibernatebasedorm.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface Dao {

    <T> T create(T entity) throws DaoException;

    <T> void update(T entity) throws DaoException;

    <T> Optional<T> loadById(long id, Class<T> entityClass) throws DaoException;

    SessionManager getSessionManager();
}
