package ru.romanov.hw12server.orm.api.dao;

public class DaoException extends Exception {
    public DaoException(Exception ex) {
        super(ex);
    }
}
