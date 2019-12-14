package ru.romanov.hw12server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.SessionFactory;
import ru.romanov.hw12server.orm.api.dao.Dao;
import ru.romanov.hw12server.orm.api.service.DBService;
import ru.romanov.hw12server.orm.api.service.DBServiceImpl;
import ru.romanov.hw12server.server.auth.filter.ApiFilter;
import ru.romanov.hw12server.server.auth.filter.PagesFilter;
import ru.romanov.hw12server.orm.hibernate.HibernateUtils;
import ru.romanov.hw12server.orm.hibernate.dao.DaoHibernateImpl;
import ru.romanov.hw12server.orm.hibernate.sessionmanager.SessionManagerHibernateImpl;
import ru.romanov.hw12server.model.Admin;
import ru.romanov.hw12server.model.User;
import ru.romanov.hw12server.server.auth.service.AuthService;
import ru.romanov.hw12server.server.servlet.rest.LoginServlet;
import ru.romanov.hw12server.server.servlet.pages.PageServlet;
import ru.romanov.hw12server.server.servlet.rest.UserServlet;

public class App {

    private static final int PORT = 8080;
    private final static String PAGE_LOGIN = "/login";
    private final static String PAGE_MAIN = "/main_page";
    private final static String PAGE_ADD_USER = "/add_user";
    private final static String PAGE_ALL_USERS = "/all_users";
    private final static String URL_API_USERS = "/api/users";
    private final static String URL_API_LOGIN = "/api/login";

    private final DBService dbService;
    private final AuthService authService;

    public App() {
        dbService = getDbService();
        authService = getAuthService();
    }

    public static void main(String[] args) throws Exception {
        new App().start();
    }

    private void start() throws Exception {
        dbService.create(new Admin("admin", "pass"));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new PageServlet(PAGE_LOGIN)), PAGE_LOGIN);
        context.addServlet(new ServletHolder(new PageServlet(PAGE_MAIN)), PAGE_MAIN);
        context.addServlet(new ServletHolder(new PageServlet(PAGE_ADD_USER)), PAGE_ADD_USER);
        context.addServlet(new ServletHolder(new PageServlet(PAGE_ALL_USERS)), PAGE_ALL_USERS);
        context.addServlet(new ServletHolder(new LoginServlet(authService)), URL_API_LOGIN);
        context.addServlet(new ServletHolder(new UserServlet(dbService)), URL_API_USERS);

        context.addFilter(new FilterHolder(new PagesFilter()), PAGE_MAIN, null);
        context.addFilter(new FilterHolder(new PagesFilter()), PAGE_ADD_USER, null);
        context.addFilter(new FilterHolder(new PagesFilter()), PAGE_ALL_USERS, null);
        context.addFilter(new FilterHolder(new ApiFilter()), URL_API_USERS, null);

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(context));

        server.start();
        server.join();
    }

    private DBService getDbService() {
        SessionFactory sessionFactory =
                HibernateUtils.buildSessionFactory("hibernate.cfg.xml", Admin.class, User.class);
        SessionManagerHibernateImpl sessionManager = new SessionManagerHibernateImpl(sessionFactory);
        Dao dao = new DaoHibernateImpl(sessionManager);
        return new DBServiceImpl(dao);
    }

    private AuthService getAuthService() {
        return new AuthService(dbService);
    }
}
