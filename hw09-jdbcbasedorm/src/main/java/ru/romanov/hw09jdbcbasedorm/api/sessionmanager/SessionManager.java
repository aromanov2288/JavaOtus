package ru.romanov.hw09jdbcbasedorm.api.sessionmanager;

import ru.romanov.hw09jdbcbasedorm.api.sessionmanager.DatabaseSession;

public interface SessionManager extends AutoCloseable {

    void beginSession();

    void commitSession();

    void rollbackSession();

    void close();

    DatabaseSession getCurrentSession();
}
