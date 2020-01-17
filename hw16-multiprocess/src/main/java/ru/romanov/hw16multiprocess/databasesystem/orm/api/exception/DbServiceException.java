package ru.romanov.hw16multiprocess.databasesystem.orm.api.exception;

public class DbServiceException extends RuntimeException {

    public DbServiceException(Exception e) {
        super(e);
    }
}
