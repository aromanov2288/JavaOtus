package ru.romanov.hw16multiprocess.databasesystem.socket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@RequiredArgsConstructor
public class RegistrationManager {

    private final Client client;

    @PostConstruct
    public void init() {
        boolean result;
        do {
            result = client.registerService();
        } while (!result);
    }

    @PreDestroy
    public void destroy () {
        boolean result;
        do {
            result = client.unregisterService();
        } while (!result);
    }
}
