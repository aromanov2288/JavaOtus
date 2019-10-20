package ru.romanov.hw09jdbcbasedorm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.romanov.hw09jdbcbasedorm.api.dao.Dao;
import ru.romanov.hw09jdbcbasedorm.jdbc.dao.DaoJdbcImpl;
import ru.romanov.hw09jdbcbasedorm.datasourse.DataSourceH2;
import ru.romanov.hw09jdbcbasedorm.jdbc.executor.DBExecutor;
import ru.romanov.hw09jdbcbasedorm.model.Person;
import ru.romanov.hw09jdbcbasedorm.api.service.DBService;
import ru.romanov.hw09jdbcbasedorm.api.service.DBServiceImpl;
import ru.romanov.hw09jdbcbasedorm.jdbc.sessionmanager.SessionManagerJdbcImpl;

import javax.sql.DataSource;
import java.util.Optional;

public class DBServiceWithPersonTest {

    private DBService dbService;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new DataSourceH2();
        SessionManagerJdbcImpl sessionManager = new SessionManagerJdbcImpl(dataSource);
        DBExecutor dbExecutor = new DBExecutor();
        Dao dao = new DaoJdbcImpl(sessionManager, dbExecutor);
        dbService = new DBServiceImpl(dao);
        dbService.createTable(Person.class);

    }

    @Test
    void insertTest() {
        Person personForSave = new Person("Sasha", 22, true);
        long id = dbService.create(personForSave);
        Assertions.assertEquals(1, id);
    }

    @Test
    void updateTest() {
        Person personForSave = new Person("Sasha", 22, true);
        long id = dbService.create(personForSave);
        Person personForUpdate = new Person(id, "Mishf", 25, false);
        dbService.update(personForUpdate);
        Optional<Person> optionalPersonFromDb = dbService.loadById(1, Person.class);
        Assertions.assertTrue(optionalPersonFromDb.isPresent());
        Person personFromDb = optionalPersonFromDb.get();
        Assertions.assertEquals(personForUpdate, personFromDb);
    }

    @Test
    void getByIdTest() {
        Person personForSave = new Person("Sasha", 22, true);
        dbService.create(personForSave);
        Optional<Person> optionalPersonFromDb = dbService.loadById(1, Person.class);
        Assertions.assertTrue(optionalPersonFromDb.isPresent());
        Person personFromDb = optionalPersonFromDb.get();
        Assertions.assertEquals(personForSave, personFromDb);
    }

    @AfterEach
    void tearDown() {
        dbService.dropTable(Person.class);
    }
}
