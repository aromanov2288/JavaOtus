package ru.romanov.hw12server.server.servlet.pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.ClassLoader.getSystemResourceAsStream;

public class PageServlet extends HttpServlet {

    private final String url;
    private Map<String, String> pathPageMap = new HashMap<>();

    public PageServlet(String url) {
        this.url = url;
        pathPageMap.put("/login", "static/login.html");
        pathPageMap.put("/main_page", "static/index.html");
        pathPageMap.put("/add_user", "static/addUser.html");
        pathPageMap.put("/all_users", "static/allUsers.html");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(getSystemResourceAsStream(pathPageMap.get(url)));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String htmlAsString = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(htmlAsString);
    }
}
