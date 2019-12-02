package ru.romanov.hw10hibernatebasedorm.api.service;

public class DbServiceException extends RuntimeException {

    public DbServiceException(Exception e) {
        super(e);
    }
}
