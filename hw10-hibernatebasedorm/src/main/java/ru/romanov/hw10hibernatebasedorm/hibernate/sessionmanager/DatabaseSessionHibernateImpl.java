package ru.romanov.hw10hibernatebasedorm.hibernate.sessionmanager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.romanov.hw10hibernatebasedorm.api.sessionmanager.DatabaseSession;

public class DatabaseSessionHibernateImpl implements DatabaseSession {

    private final Session session;
    private final Transaction transaction;

    public DatabaseSessionHibernateImpl(Session session) {
        this.session = session;
        this.transaction = session.beginTransaction();
    }

    public Session getHibernateSession() {
        return session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void close() {
        if (transaction.isActive()) {
            transaction.commit();
        }
        session.close();
    }
}
