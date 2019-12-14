package ru.romanov.hw12server.server.auth.service;

import ru.romanov.hw12server.orm.api.service.DBService;
import ru.romanov.hw12server.model.Admin;

public class AuthService {

    private final DBService dbService;

    public AuthService(DBService dbService) {
        this.dbService = dbService;
    }

    public boolean isAuthenticate(Admin admin) {
        return dbService.loadAll(Admin.class).contains(admin);
    }
}
