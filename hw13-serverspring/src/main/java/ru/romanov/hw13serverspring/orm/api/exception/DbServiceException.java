package ru.romanov.hw13serverspring.orm.api.exception;

public class DbServiceException extends RuntimeException {

    public DbServiceException(Exception e) {
        super(e);
    }
}
