package ru.romanov.hw09jdbcbasedorm.api.service;

import java.util.Optional;

public interface DBService {

    void createTable(Class entityClass);

    void dropTable(Class entityClass);

    <T> long create(T entity);

    <T> void update(T entity);

    <T> Optional<T> loadById(long id, Class entityClass);
}
