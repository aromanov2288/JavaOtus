package ru.romanov.hw13serverspring;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romanov.hw13serverspring.model.User;
import ru.romanov.hw13serverspring.orm.api.dao.Dao;
import ru.romanov.hw13serverspring.orm.api.service.DBService;
import ru.romanov.hw13serverspring.orm.api.service.DBServiceImpl;
import ru.romanov.hw13serverspring.orm.hibernate.dao.DaoHibernateImpl;
import ru.romanov.hw13serverspring.orm.hibernate.sessionmanager.SessionManagerHibernateImpl;

import static ru.romanov.hw13serverspring.orm.hibernate.HibernateUtils.buildSessionFactory;

@Configuration
public class AppConfig {

    @Bean
    public DBService dbService() {
        SessionFactory sessionFactory = buildSessionFactory("hibernate.cfg.xml", User.class);
        SessionManagerHibernateImpl sessionManager = new SessionManagerHibernateImpl(sessionFactory);
        Dao dao = new DaoHibernateImpl(sessionManager);
        return new DBServiceImpl(dao);
    }
}
