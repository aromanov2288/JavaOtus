package ru.romanov.hw13serverspring.orm.api.exception;

public class DaoException extends Exception {
    public DaoException(Exception ex) {
        super(ex);
    }
}
