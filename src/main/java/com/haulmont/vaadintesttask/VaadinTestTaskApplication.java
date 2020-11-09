package com.haulmont.vaadintesttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class VaadinTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaadinTestTaskApplication.class, args);
    }

}