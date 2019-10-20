package ru.romanov.hw09jdbcbasedorm.jdbc.executor;

public class DBExecutorHelperException extends Exception {

    public DBExecutorHelperException(Exception e) {
        super(e);
    }

    public DBExecutorHelperException(String message) {
        super(message);
    }
}
