package ru.romanov.hw09jdbcbasedorm.jdbc.sessionmanager;

import ru.romanov.hw09jdbcbasedorm.api.sessionmanager.SessionManager;
import ru.romanov.hw09jdbcbasedorm.api.sessionmanager.SessionManagerException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SessionManagerJdbcImpl implements SessionManager {

    private static final int TIMEOUT_IN_SECONDS = 5;
    private final DataSource dataSource;
    private DatabaseSessionJdbcImpl databaseSession;

    public SessionManagerJdbcImpl(DataSource dataSource) {
        if (dataSource == null) {
            throw new SessionManagerException("Datasource is null");
        }
        this.dataSource = dataSource;
    }

    @Override
    public void beginSession() {
        try {
            Connection connection = dataSource.getConnection();
            databaseSession = new DatabaseSessionJdbcImpl(connection);
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void commitSession() {
        checkConnection();
        try {
            databaseSession.getConnection().commit();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void rollbackSession() {
        checkConnection();
        try {
            databaseSession.getConnection().rollback();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public void close() {
        checkConnection();
        try {
            databaseSession.getConnection().close();
        } catch (SQLException e) {
            throw new SessionManagerException(e);
        }
    }

    @Override
    public DatabaseSessionJdbcImpl getCurrentSession() {
        checkConnection();
        return databaseSession;
    }

    private void checkConnection() {
        try {
            Connection connection = databaseSession.getConnection();
            if (connection == null || !connection.isValid(TIMEOUT_IN_SECONDS)) {
                throw new SessionManagerException("Connection is invalid");
            }
        } catch (SQLException ex) {
            throw new SessionManagerException(ex);
        }
    }}
