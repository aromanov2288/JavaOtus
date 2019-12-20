package ru.romanov.hw13serverspring.orm.api.sessionmanager;

public interface SessionManager extends AutoCloseable {

    void beginSession();

    void commitSession();

    void rollbackSession();

    void close();

    DatabaseSession getCurrentSession();
}
