package ru.romanov.hw16multiprocess.databasesystem.orm.api.exception;

public class DaoException extends Exception {
    public DaoException(Exception ex) {
        super(ex);
    }
}
