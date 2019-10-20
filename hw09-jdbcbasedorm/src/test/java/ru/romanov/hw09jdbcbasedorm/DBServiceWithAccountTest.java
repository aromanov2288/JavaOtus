package ru.romanov.hw09jdbcbasedorm;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.romanov.hw09jdbcbasedorm.api.dao.Dao;
import ru.romanov.hw09jdbcbasedorm.jdbc.dao.DaoJdbcImpl;
import ru.romanov.hw09jdbcbasedorm.datasourse.DataSourceH2;
import ru.romanov.hw09jdbcbasedorm.jdbc.executor.DBExecutor;
import ru.romanov.hw09jdbcbasedorm.model.Account;
import ru.romanov.hw09jdbcbasedorm.api.service.DBService;
import ru.romanov.hw09jdbcbasedorm.api.service.DBServiceImpl;
import ru.romanov.hw09jdbcbasedorm.jdbc.sessionmanager.SessionManagerJdbcImpl;

import javax.sql.DataSource;
import java.util.Optional;

public class DBServiceWithAccountTest {

    private DBService dbService;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new DataSourceH2();
        SessionManagerJdbcImpl sessionManager = new SessionManagerJdbcImpl(dataSource);
        DBExecutor dbExecutor = new DBExecutor();
        Dao dao = new DaoJdbcImpl(sessionManager, dbExecutor);
        dbService = new DBServiceImpl(dao);
        dbService.createTable(Account.class);

    }

    @Test
    void insertTest() {
        Account accountForSave = new Account("USER", 10.5f,true);
        long id = dbService.create(accountForSave);
        Assertions.assertEquals(1, id);
    }

    @Test
    void updateTest() {
        Account accountForSave = new Account("USER", 10.5f,true);
        long id = dbService.create(accountForSave);
        Account accountForUpdate = new Account(id, "ADMIN", 33.5f,false);
        dbService.update(accountForUpdate);
        Optional<Account> optionalAccountFromDb = dbService.loadById(1, Account.class);
        Assertions.assertTrue(optionalAccountFromDb.isPresent());
        Account accountFromDb = optionalAccountFromDb.get();
        Assertions.assertEquals(accountForUpdate, accountFromDb);
    }

    @Test
    void getByIdTest() {
        Account accountForSave = new Account("USER", 10.5f,true);
        dbService.create(accountForSave);
        Optional<Account> optionalAccountFromDb = dbService.loadById(1, Account.class);
        Assertions.assertTrue(optionalAccountFromDb.isPresent());
        Account accountFromDb = optionalAccountFromDb.get();
        Assertions.assertEquals(accountForSave, accountFromDb);
    }

    @AfterEach
    void tearDown() {
        dbService.dropTable(Account.class);
    }
}
