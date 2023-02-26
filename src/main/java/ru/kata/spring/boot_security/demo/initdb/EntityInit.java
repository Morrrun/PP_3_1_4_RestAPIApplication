package ru.kata.spring.boot_security.demo.initdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EntityInit implements CommandLineRunner {
    private final DataInitService dataInitService;

    @Autowired
    public EntityInit(DataInitService dataInitService) {
        this.dataInitService = dataInitService;
    }

    @Override
    public void run(String... args) throws Exception {
        dataInitService.createEntity();
    }
}
