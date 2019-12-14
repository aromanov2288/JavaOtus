package ru.romanov.hw12server.server.servlet.rest;

import com.google.gson.Gson;
import ru.romanov.hw12server.model.Admin;
import ru.romanov.hw12server.server.auth.service.AuthService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;

public class LoginServlet extends HttpServlet {

    private final AuthService authService;
    private final Gson gson = new Gson();

    public LoginServlet(AuthService authService) {
        this.authService = authService;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Admin adminCandidate = gson.fromJson(body, Admin.class);

        if (authService.isAuthenticate(adminCandidate)) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(30);
        } else {
            response.sendRedirect("/login");
        }
    }

}
