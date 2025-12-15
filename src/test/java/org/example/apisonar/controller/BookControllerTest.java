package org.example.apisonar.controller;

import org.example.apisonar.dao.BookDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; // Nouvelle importation
import org.mockito.InjectMocks; // Nouvelle importation
import org.mockito.Mock; // Nouvelle importation
import org.mockito.junit.jupiter.MockitoExtension; // Nouvelle importation
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

// ⚠️ Suppression de @SpringBootTest car nous utilisons MockitoExtension
// pour un test unitaire pur qui simule complètement les dépendances.

// 🛠️ Correction : Utilisation de l'extension JUnit pour intégrer Mockito
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    // 🛠️ Correction : InjectMocks demande à Mockito d'injecter les @Mock (ici bookDao) dans cet objet.
    @InjectMocks
    private BookController controller;

    // 🛠️ Correction : @Mock crée un faux objet (un mock) pour la dépendance BookDao.
    // Ceci remplace @MockBean pour les tests unitaires.
    @Mock
    private BookDao bookDao;

    @Test
    public void testGetAllBooksReturnsList() {
        // Simuler le comportement du DAO : retourne une liste vide
        when(bookDao.getAllBooks()).thenReturn(java.util.Collections.emptyList());

        var books = controller.getAllBooks();

        // Vérifie que la liste de livres n'est pas null
        assertNotNull(books, "La liste des livres ne doit pas être null.");
    }

    @Test
    public void testGetBookByTitleFound() {
        // Création d'un livre mock pour la simulation
        org.example.apisonar.model.Book mockBook =
                new org.example.apisonar.model.Book("TitreA", "Auteur", "123");

        // Simuler le comportement : quand on cherche "TitreA", retourne le livre mock
        when(bookDao.getBookByTitle("TitreA")).thenReturn(mockBook);

        ResponseEntity<org.example.apisonar.model.Book> response = controller.getBookByTitle("TitreA");

        // Vérifie que la réponse est 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetBookByTitleNotFound() {
        // Simuler le comportement : quand on cherche "Inconnu", retourne null
        when(bookDao.getBookByTitle("Inconnu")).thenReturn(null);

        ResponseEntity<org.example.apisonar.model.Book> response = controller.getBookByTitle("Inconnu");

        // Vérifie que la réponse est 404 NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}