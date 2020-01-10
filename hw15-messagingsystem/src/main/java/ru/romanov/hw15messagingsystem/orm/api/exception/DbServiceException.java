package ru.romanov.hw15messagingsystem.orm.api.exception;

public class DbServiceException extends RuntimeException {

    public DbServiceException(Exception e) {
        super(e);
    }
}
