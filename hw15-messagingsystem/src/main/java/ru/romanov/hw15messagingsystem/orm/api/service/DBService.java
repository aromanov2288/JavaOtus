package ru.romanov.hw15messagingsystem.orm.api.service;

import java.util.List;
import java.util.Optional;

public interface DBService {

    <T> T create(T entity);

    <T> void update(T entity);

    <T> Optional<T> loadById(long id, Class<T> entityClass);

    <T> List<T> loadAll(Class<T> entityClass);
}
