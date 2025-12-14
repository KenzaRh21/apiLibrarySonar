package org.example.apisonar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookControllerTest {

    // PROBLÈME: Test sans assertion
    @Test
    public void testGetAllBooks() {
        // Test vide - pas de vérification
        System.out.println("Test exécuté");
    }

    // PROBLÈME: Couverture de test faible - manque beaucoup de tests
}