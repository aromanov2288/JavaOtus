package ru.romanov.hw10hibernatebasedorm;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.romanov.hw10hibernatebasedorm.api.dao.Dao;
import ru.romanov.hw10hibernatebasedorm.api.service.DBService;
import ru.romanov.hw10hibernatebasedorm.api.service.DBServiceImpl;
import ru.romanov.hw10hibernatebasedorm.hibernate.HibernateUtils;
import ru.romanov.hw10hibernatebasedorm.hibernate.dao.DaoHibernateImpl;
import ru.romanov.hw10hibernatebasedorm.hibernate.sessionmanager.SessionManagerHibernateImpl;
import ru.romanov.hw10hibernatebasedorm.model.Address;
import ru.romanov.hw10hibernatebasedorm.model.Person;
import ru.romanov.hw10hibernatebasedorm.model.Phone;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DBServiceTest {

    private SessionFactory sessionFactory;
    private DBService dbService;

    @BeforeEach
    void setUp() {
        sessionFactory =
                HibernateUtils.buildSessionFactory("hibernate-test.cfg.xml", Address.class, Phone.class, Person.class);
        SessionManagerHibernateImpl sessionManager = new SessionManagerHibernateImpl(sessionFactory);
        Dao dao = new DaoHibernateImpl(sessionManager);
        dbService = new DBServiceImpl(dao);

    }

    @Test
    void insertTest() {
        Person personForSave = createPerson("Street One", "8(495)123-45-67");
        Person personFromDb = dbService.create(personForSave);
        Assertions.assertEquals(personForSave, personFromDb);
        Assertions.assertTrue(personFromDb.getId() > 0);
    }

    @Test
    void updateTest() {
        Person personForSave = createPerson("Street One", "8(495)123-45-67");
        long id = dbService.create(personForSave).getId();
        Person personForUpdate = createPerson(id, "Street two", "8(495)888-88-88");
        dbService.update(personForUpdate);
        Optional<Person> optionalPersonFromDb = dbService.loadById(1, Person.class);
        Assertions.assertTrue(optionalPersonFromDb.isPresent());
        Person personFromDb = optionalPersonFromDb.get();
        Assertions.assertEquals(personForUpdate, personFromDb);
    }

    @Test
    void getByIdTest() {
        Person personForSave = createPerson("Tverskaya str", "8(495)123-45-67");
        long id = dbService.create(personForSave).getId();
        Optional<Person> optionalPersonFromDb = dbService.loadById(id, Person.class);
        Assertions.assertTrue(optionalPersonFromDb.isPresent());
        Person personFromDb = optionalPersonFromDb.get();
        Assertions.assertEquals(personForSave, personFromDb);
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }

    private Person createPerson(long id, String street, String number) {
        Person person = createPerson(street, number);
        person.setId(id);
        return person;
    }

    private Person createPerson(String street, String number) {
        Address address = new Address(street);
        Person person = new Person();
        Phone phone = new Phone(number);
        Set<Phone> phoneSet = new HashSet<>();
        phoneSet.add(phone);
        person.setAddress(address);
        person.setPhoneSet(phoneSet);
        return person;
    }
}
