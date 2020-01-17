package ru.romanov.hw16multiprocess.databasesystem;

import org.hibernate.SessionFactory;
import ru.romanov.hw16multiprocess.common.model.ServiceType;
import ru.romanov.hw16multiprocess.common.model.domain.User;
import ru.romanov.hw16multiprocess.common.ms.MessageType;
import ru.romanov.hw16multiprocess.common.ms.api.MsClient;
import ru.romanov.hw16multiprocess.databasesystem.ms.handler.CreateReqHandler;
import ru.romanov.hw16multiprocess.databasesystem.ms.handler.LoadAllReqHandler;
import ru.romanov.hw16multiprocess.databasesystem.ms.impl.MsClientImpl;
import ru.romanov.hw16multiprocess.databasesystem.orm.api.dao.Dao;
import ru.romanov.hw16multiprocess.databasesystem.orm.api.service.DBService;
import ru.romanov.hw16multiprocess.databasesystem.orm.api.service.DBServiceImpl;
import ru.romanov.hw16multiprocess.databasesystem.orm.hibernate.dao.DaoHibernateImpl;
import ru.romanov.hw16multiprocess.databasesystem.orm.hibernate.sessionmanager.SessionManagerHibernateImpl;
import ru.romanov.hw16multiprocess.databasesystem.socket.Client;
import ru.romanov.hw16multiprocess.databasesystem.socket.Server;

import static ru.romanov.hw16multiprocess.databasesystem.orm.hibernate.HibernateUtils.buildSessionFactory;

public class DatabaseApp {

    private static final Integer CURRENT_PORT = 8380;
    private static final ServiceType MS_CLIENT_SERVICE_TYPE = ServiceType.DATABASE;
    private static final String MS_SERVER_HOST = "localhost";
    private static final Integer MS_SERVER_PORT = 8280;
    private static final String HIBERNATE_CONFIG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        SessionFactory sessionFactory = buildSessionFactory(HIBERNATE_CONFIG_FILE, User.class);
        SessionManagerHibernateImpl sessionManager = new SessionManagerHibernateImpl(sessionFactory);
        Dao dao = new DaoHibernateImpl(sessionManager);
        DBService dbService = new DBServiceImpl(dao);

        Client socketClient = new Client(CURRENT_PORT, MS_SERVER_HOST, MS_SERVER_PORT, MS_CLIENT_SERVICE_TYPE);

        MsClient msClient = new MsClientImpl(MS_CLIENT_SERVICE_TYPE.getName(), socketClient);
        msClient.addHandler(MessageType.CREARE_REQ, new CreateReqHandler(dbService));
        msClient.addHandler(MessageType.LOAD_ALL_REQ, new LoadAllReqHandler(dbService));

        socketClient.registerService();

        Server server = new Server(CURRENT_PORT, msClient, MS_CLIENT_SERVICE_TYPE);
        server.start();
    }
}
