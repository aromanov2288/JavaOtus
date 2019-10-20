package ru.romanov.hw09jdbcbasedorm.jdbc.dao;

import javafx.util.Pair;
import ru.romanov.hw09jdbcbasedorm.api.dao.Dao;
import ru.romanov.hw09jdbcbasedorm.api.dao.DaoException;
import ru.romanov.hw09jdbcbasedorm.jdbc.executor.DBExecutor;
import ru.romanov.hw09jdbcbasedorm.jdbc.executor.DBExecutorHelper;
import ru.romanov.hw09jdbcbasedorm.api.sessionmanager.SessionManager;
import ru.romanov.hw09jdbcbasedorm.jdbc.sessionmanager.SessionManagerJdbcImpl;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class DaoJdbcImpl implements Dao {

    private final SessionManagerJdbcImpl sessionManager;
    private final DBExecutor dbExecutor;
    private final DBExecutorHelper helper = new DBExecutorHelper();

    public DaoJdbcImpl(SessionManagerJdbcImpl sessionManager, DBExecutor dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public void createTable(Class entityClass) throws DaoException {
        try {
            String createTableSqlData = helper.createTableSqlStr(entityClass);
            dbExecutor.createOrDropTable(getConnection(), createTableSqlData);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable(Class entityClass) throws DaoException {
        try {
            String dropTableSqlData = helper.dropTableSqlStr(entityClass);
            dbExecutor.createOrDropTable(getConnection(), dropTableSqlData);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public <T> long create(T entity) throws DaoException {
        try {
            Pair<String, List<String>> insertSqlData = helper.insertSqlData(entity);
            return dbExecutor.insertRecord(getConnection(), insertSqlData.getKey(), insertSqlData.getValue());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public <T> void update(T entity) throws DaoException {
        try {
            Pair<String, List<String>> updateSqlData = helper.updateSqlData(entity);
            dbExecutor.updateRecord(getConnection(), updateSqlData.getKey(), updateSqlData.getValue());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public <T> Optional<T> loadById(long id, Class entityClass) {
        try {
            String loadByIdData = helper.loadByIdSqlData(entityClass);
            return dbExecutor.selectRecord(getConnection(), loadByIdData, id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return (T) helper.createInstanceAndSetData(entityClass, resultSet);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
