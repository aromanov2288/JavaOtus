package ru.romanov.hw09jdbcbasedorm.jdbc.sessionmanager;

import ru.romanov.hw09jdbcbasedorm.api.sessionmanager.DatabaseSession;

import java.sql.Connection;

public class DatabaseSessionJdbcImpl implements DatabaseSession {

    private final Connection connection;

    DatabaseSessionJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
