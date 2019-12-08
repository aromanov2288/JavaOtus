package ru.romanov.hw11cache;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.romanov.hw11cache.api.dao.Dao;
import ru.romanov.hw11cache.api.dao.DaoException;
import ru.romanov.hw11cache.api.service.DBService;
import ru.romanov.hw11cache.api.service.DBServiceImpl;
import ru.romanov.hw11cache.api.sessionmanager.SessionManager;
import ru.romanov.hw11cache.hibernate.HibernateUtils;
import ru.romanov.hw11cache.hibernate.dao.DaoHibernateImpl;
import ru.romanov.hw11cache.hibernate.sessionmanager.SessionManagerHibernateImpl;
import ru.romanov.hw11cache.model.Address;
import ru.romanov.hw11cache.model.Person;
import ru.romanov.hw11cache.model.Phone;

import java.time.LocalDateTime;
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
        Dao daoDecorator = new DaoDecorator(dao);
        dbService = new DBServiceImpl(daoDecorator);

    }

    @Test
    void getByIdTest() {
        Person personForSave = createPerson("Tverskaya str", "8(495)123-45-67");
        long id = dbService.create(personForSave).getId();
        System.out.println(LocalDateTime.now() + ", запись сохранена в БД");

        System.out.println(LocalDateTime.now() + ", отправлен запрос на получение записи из БД");
        Optional<Person> optionalPersonFromDb1 = dbService.loadById(id, Person.class);
        System.out.println(LocalDateTime.now() + ", получена запись из БД");

        System.out.println(LocalDateTime.now() + ", отправлен запрос на получение записи из БД");
        Optional<Person> optionalPersonFromDb2 = dbService.loadById(id, Person.class);
        System.out.println(LocalDateTime.now() + ", получена запись из БД");

        Assertions.assertTrue(optionalPersonFromDb1.isPresent());
        Person personFromDb1 = optionalPersonFromDb1.get();
        Assertions.assertEquals(personForSave, personFromDb1);

        Assertions.assertTrue(optionalPersonFromDb2.isPresent());
        Person personFromDb2 = optionalPersonFromDb2.get();
        Assertions.assertEquals(personForSave, personFromDb2);

        System.gc();
        System.out.println(LocalDateTime.now() + ", отправлен запрос на получение записи из БД");
        Optional<Person> optionalPersonFromDb3 = dbService.loadById(id, Person.class);
        System.out.println(LocalDateTime.now() + ", получена запись из БД");

        Assertions.assertTrue(optionalPersonFromDb3.isPresent());
        Person personFromDb3 = optionalPersonFromDb2.get();
        Assertions.assertEquals(personForSave, personFromDb3);
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

    class DaoDecorator implements Dao {

        private Dao dao;

        DaoDecorator (Dao dao) {
            this.dao = dao;
        }

        @Override
        public <T> T create(T entity) throws DaoException {
            return dao.create(entity);
        }

        @Override
        public <T> void update(T entity) throws DaoException {
            dao.update(entity);
        }

        @Override
        public <T> Optional<T> loadById(long id, Class<T> entityClass) throws DaoException {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return dao.loadById(id, entityClass);
        }

        @Override
        public SessionManager getSessionManager() {
            return dao.getSessionManager();
        }
    }
}
