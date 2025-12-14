package org.example.apisonar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiSonarApplication {

    public static void main(String[] args) {
        // PROBLÈME: Utilisation de System.out au lieu d'un logger
        System.out.println("Démarrage de l'application Library Management...");

        SpringApplication.run(ApiSonarApplication.class, args);

        System.out.println("Application démarrée avec succès!");
    }
}
