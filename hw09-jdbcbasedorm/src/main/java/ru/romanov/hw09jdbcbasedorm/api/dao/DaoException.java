package ru.romanov.hw09jdbcbasedorm.api.dao;

public class DaoException extends Exception {
    public DaoException(Exception ex) {
        super(ex);
    }
}
