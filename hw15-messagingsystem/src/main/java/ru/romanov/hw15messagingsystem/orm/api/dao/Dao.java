package ru.romanov.hw15messagingsystem.orm.api.dao;

import ru.romanov.hw15messagingsystem.orm.api.exception.DaoException;
import ru.romanov.hw15messagingsystem.orm.api.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface Dao {

    <T> T create(T entity) throws DaoException;

    <T> void update(T entity) throws DaoException;

    <T> Optional<T> loadById(long id, Class<T> entityClass);

    <T> List<T> loadAll(Class<T> entityClass);

    SessionManager getSessionManager();
}
