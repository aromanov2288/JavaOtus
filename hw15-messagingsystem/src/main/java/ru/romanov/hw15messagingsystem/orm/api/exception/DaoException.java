package ru.romanov.hw15messagingsystem.orm.api.exception;

public class DaoException extends Exception {
    public DaoException(Exception ex) {
        super(ex);
    }
}
