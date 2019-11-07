package ru.romanov.hw09jdbcbasedorm.jdbc.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DBExecutor {

    public void createOrDropTable(Connection connection, String sql) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public long insertRecord(Connection connection, String sql, List<String> params) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.size(); i++) {
                pst.setString(i + 1, params.get(i));
            }
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            connection.rollback(savePoint);
            throw e;
        }
    }

    public void updateRecord(Connection connection, String sql, List<String> params) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("savePointName");
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                pst.setString(i + 1, params.get(i));
            }
            pst.executeUpdate();
        } catch (SQLException ex) {
            connection.rollback(savePoint);
            throw ex;
        }
    }

    public <T> Optional<T> selectRecord(Connection connection, String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }
    }
}
