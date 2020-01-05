package ru.romanov.hw15messagingsystem.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.romanov.hw15messagingsystem.front.FeService;
import ru.romanov.hw15messagingsystem.front.FeUserImpl;
import ru.romanov.hw15messagingsystem.model.domain.User;
import ru.romanov.hw15messagingsystem.ms.MessageType;
import ru.romanov.hw15messagingsystem.ms.api.MessageSystem;
import ru.romanov.hw15messagingsystem.ms.api.MsClient;
import ru.romanov.hw15messagingsystem.ms.handler.CreateReqHandler;
import ru.romanov.hw15messagingsystem.ms.handler.CreateRespHandler;
import ru.romanov.hw15messagingsystem.ms.handler.LoadAllReqHandler;
import ru.romanov.hw15messagingsystem.ms.handler.LoadAllRespHandler;
import ru.romanov.hw15messagingsystem.ms.impl.MessageSystemImpl;
import ru.romanov.hw15messagingsystem.ms.impl.MsClientImpl;
import ru.romanov.hw15messagingsystem.orm.api.dao.Dao;
import ru.romanov.hw15messagingsystem.orm.api.service.DBService;
import ru.romanov.hw15messagingsystem.orm.api.service.DBServiceImpl;
import ru.romanov.hw15messagingsystem.orm.hibernate.dao.DaoHibernateImpl;
import ru.romanov.hw15messagingsystem.orm.hibernate.sessionmanager.SessionManagerHibernateImpl;

import static ru.romanov.hw15messagingsystem.orm.hibernate.HibernateUtils.buildSessionFactory;

@Configuration
public class AppConfig {

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "feService";
    private static final String DATABASE_SERVICE_CLIENT_NAME = "dbService";

    @Bean
    public MessageSystem messageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public FeService feService(MessageSystem ms) {
        MsClient feMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, ms);
        ms.addClient(feMsClient);

        FeService feService = new FeUserImpl(feMsClient, DATABASE_SERVICE_CLIENT_NAME);
        feMsClient.addHandler(MessageType.LOAD_ALL_RESP, new LoadAllRespHandler(feService));
        feMsClient.addHandler(MessageType.CREATE_RESP, new CreateRespHandler(feService));
        return feService;
    }

    @Bean
    public DBService dbService(MessageSystem ms) {
        MsClient dbMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, ms);
        ms.addClient(dbMsClient);

        SessionFactory sessionFactory = buildSessionFactory("hibernate.cfg.xml", User.class);
        SessionManagerHibernateImpl sessionManager = new SessionManagerHibernateImpl(sessionFactory);
        Dao dao = new DaoHibernateImpl(sessionManager);
        DBService dbService = new DBServiceImpl(dao);
        dbMsClient.addHandler(MessageType.LOAD_ALL_REQ, new LoadAllReqHandler(dbService));
        dbMsClient.addHandler(MessageType.CREARE_REQ, new CreateReqHandler(dbService));
        return dbService;
    }
}
