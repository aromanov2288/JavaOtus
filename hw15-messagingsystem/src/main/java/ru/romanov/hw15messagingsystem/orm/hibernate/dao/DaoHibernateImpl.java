package ru.romanov.hw15messagingsystem.orm.hibernate.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import ru.romanov.hw15messagingsystem.orm.api.dao.Dao;
import ru.romanov.hw15messagingsystem.orm.api.exception.DaoException;
import ru.romanov.hw15messagingsystem.orm.api.sessionmanager.SessionManager;
import ru.romanov.hw15messagingsystem.orm.hibernate.sessionmanager.DatabaseSessionHibernateImpl;
import ru.romanov.hw15messagingsystem.orm.hibernate.sessionmanager.SessionManagerHibernateImpl;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DaoHibernateImpl implements Dao {

    private final SessionManagerHibernateImpl sessionManager;

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
    public <T> List<T> loadAll(Class<T> entityClass) {
        DatabaseSessionHibernateImpl currentSession = sessionManager.getCurrentSession();
        try {
            Session session = currentSession.getHibernateSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
            Root<T> root = criteriaQuery.from(entityClass);
            TypedQuery<T> typedQuery = session.createQuery(criteriaQuery.select(root));
            return typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
