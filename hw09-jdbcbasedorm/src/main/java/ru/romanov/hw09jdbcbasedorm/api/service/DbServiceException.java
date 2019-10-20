package ru.romanov.hw09jdbcbasedorm.api.service;

public class DbServiceException extends RuntimeException {

    public DbServiceException(Exception e) {
        super(e);
    }
}
