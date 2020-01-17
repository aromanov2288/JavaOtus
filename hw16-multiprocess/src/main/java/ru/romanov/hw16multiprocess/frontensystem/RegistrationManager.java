package ru.romanov.hw16multiprocess.frontensystem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.romanov.hw16multiprocess.frontensystem.socket.Client;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Component
public class RegistrationManager {

    private final Client client;

    @PostConstruct
    public void init() {
        boolean result;
        do {
            result = client.registerService();
        } while (!result);
    }
}
