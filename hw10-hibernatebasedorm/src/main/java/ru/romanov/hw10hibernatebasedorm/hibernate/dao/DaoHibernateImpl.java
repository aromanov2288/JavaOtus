package ru.romanov.hw10hibernatebasedorm.hibernate.dao;

import org.hibernate.Session;
import ru.romanov.hw10hibernatebasedorm.api.dao.Dao;
import ru.romanov.hw10hibernatebasedorm.api.dao.DaoException;
import ru.romanov.hw10hibernatebasedorm.api.sessionmanager.SessionManager;
import ru.romanov.hw10hibernatebasedorm.hibernate.sessionmanager.DatabaseSessionHibernateImpl;
import ru.romanov.hw10hibernatebasedorm.hibernate.sessionmanager.SessionManagerHibernateImpl;

import java.util.Optional;

public class DaoHibernateImpl implements Dao {

    private final SessionManagerHibernateImpl sessionManager;

    public DaoHibernateImpl(SessionManagerHibernateImpl sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public <T> T create(T entity) throws DaoException {
        DatabaseSessionHibernateImpl currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.persist(entity);
            return entity;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public <T> void update(T entity) throws DaoException {
        DatabaseSessionHibernateImpl currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(entity);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public <T> Optional<T> loadById(long id, Class<T> entityClass) {
        DatabaseSessionHibernateImpl currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(entityClass, id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
