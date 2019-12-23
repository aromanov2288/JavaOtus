package ru.romanov.hw13serverspring.orm.hibernate.sessionmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.romanov.hw13serverspring.orm.api.sessionmanager.SessionManager;
import ru.romanov.hw13serverspring.orm.api.exception.SessionManagerException;

public class SessionManagerHibernateImpl implements SessionManager {

    private DatabaseSessionHibernateImpl databaseSession;
    private final SessionFactory sessionFactory;

    public SessionManagerHibernateImpl(SessionFactory sessionFactory) {
        if (sessionFactory == null) {
            throw new SessionManagerException("SessionFactory is null");
        }
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void beginSession() {
        try {
            databaseSession = new DatabaseSessionHibernateImpl(sessionFactory.openSession());
        } catch (Exception e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void commitSession() {
        checkSessionAndTransaction();
        try {
            databaseSession.getTransaction().commit();
            databaseSession.getHibernateSession().close();
        } catch (Exception e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void rollbackSession() {
        checkSessionAndTransaction();
        try {
            databaseSession.getTransaction().rollback();
            databaseSession.getHibernateSession().close();
        } catch (Exception e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void close() {
        if (databaseSession == null) {
            return;
        }
        Session session = databaseSession.getHibernateSession();
        if (session == null || !session.isConnected()) {
            return;
        }

        Transaction transaction = databaseSession.getTransaction();
        if (transaction == null || !transaction.isActive()) {
            return;
        }

        try {
            databaseSession.close();
            databaseSession = null;
        } catch (Exception e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public DatabaseSessionHibernateImpl getCurrentSession() {
        checkSessionAndTransaction();
        return databaseSession;
    }

    private void checkSessionAndTransaction() {
        if (databaseSession == null) {
            throw new SessionManagerException("DatabaseSession not opened");
        }
        Session session = databaseSession.getHibernateSession();
        if (session == null || !session.isConnected()) {
            throw new SessionManagerException("Session not open");
        }

        Transaction transaction = databaseSession.getTransaction();
        if (transaction == null || !transaction.isActive()) {
            throw new SessionManagerException("Transaction not opened");
        }
    }
}
