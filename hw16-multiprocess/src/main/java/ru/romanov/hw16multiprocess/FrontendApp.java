package ru.romanov.hw16multiprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.romanov.hw16multiprocess.frontensystem.socket.Server;

@SpringBootApplication
public class FrontendApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FrontendApp.class, args);
        Server server = context.getBean(Server.class);
        server.start();
    }
}
