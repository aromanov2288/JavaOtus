package ru.romanov.hw12server.server.servlet.rest;

import com.google.gson.Gson;
import ru.romanov.hw12server.orm.api.service.DBService;
import ru.romanov.hw12server.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UserServlet extends HttpServlet {

    private static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    private final DBService dbService;
    private final Gson gson = new Gson();

    public UserServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> userList = dbService.loadAll(User.class);
        response.setContentType(APPLICATION_JSON);
        response.getWriter().print(gson.toJson(userList));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        User user = gson.fromJson(body, User.class);
        dbService.create(user);
    }
}
