package ru.romanov.hw10hibernatebasedorm.api.service;

import java.util.Optional;

public interface DBService {

    <T> T create(T entity);

    <T> void update(T entity);

    <T> Optional<T> loadById(long id, Class<T> entityClass);
}
