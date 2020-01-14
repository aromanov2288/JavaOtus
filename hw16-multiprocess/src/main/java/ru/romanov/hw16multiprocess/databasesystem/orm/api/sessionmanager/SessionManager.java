package ru.romanov.hw16multiprocess.databasesystem.orm.api.sessionmanager;

public interface SessionManager extends AutoCloseable {

    void beginSession();

    void commitSession();

    void rollbackSession();

    void close();

    DatabaseSession getCurrentSession();
}
