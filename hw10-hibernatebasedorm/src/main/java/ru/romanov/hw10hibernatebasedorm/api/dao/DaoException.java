package ru.romanov.hw10hibernatebasedorm.api.dao;

public class DaoException extends Exception {
    public DaoException(Exception ex) {
        super(ex);
    }
}
