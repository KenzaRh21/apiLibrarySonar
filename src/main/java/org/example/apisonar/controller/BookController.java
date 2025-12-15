package org.example.apisonar.controller;

import org.example.apisonar.dao.BookDao;
import org.example.apisonar.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Logger; // Importation pour le logging JUL
import java.util.logging.Level;

@RestController
@RequestMapping("/api/books")
public class BookController {

    // Utilisation du Logger JUL
    private static final Logger LOGGER = Logger.getLogger(BookController.class.getName());

    // CORRECTION R1: Dépendance au DAO. Le mot clé 'final' est conservé.
    private final BookDao bookDao;

    // CORRECTION R2: Injection par Constructeur. Spring fournit l'instance de BookDao (@Repository).
    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        // M6: Remplacement de System.out par un log INFO
        LOGGER.log(Level.INFO, "Récupération de tous les livres");
        return bookDao.getAllBooks();
    }

    @GetMapping("/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title) {
        LOGGER.log(Level.INFO, "Recherche du livre: {0}", title);

        Book book = bookDao.getBookByTitle(title);

        // R2: Gestion des null explicite, retourne 404 NOT FOUND si le livre n'est pas trouvé
        if (book == null) {
            LOGGER.log(Level.WARNING, "Livre non trouvé pour le titre: {0}", title);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        // R3: Validation de base implémentée (devrait idéalement utiliser Bean Validation @Valid)
        if(book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Tentative d'ajout d'un livre sans titre (400 BAD REQUEST)");
            return new ResponseEntity<>("Le titre ne peut pas être vide", HttpStatus.BAD_REQUEST);
        }

        bookDao.addBook(book);
        LOGGER.log(Level.INFO, "Livre ajouté: {0}", book.getTitle());
        return new ResponseEntity<>("Livre ajouté avec succès", HttpStatus.CREATED); // 201 CREATED
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        // M7: Logique de suppression à implémenter dans le DAO
        // bookDao.deleteBook(id);
        LOGGER.log(Level.INFO, "Suppression du livre avec ID: {0}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 NO CONTENT
    }

    // M8: Complexité Cyclomatique réduite
    public String validateBook(Book book) {
        if (book == null) {
            return "Invalid: Book is null";
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            return "Invalid: Title is missing";
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            return "Invalid: Author is missing";
        }
        if (book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            return "Invalid: ISBN is missing";
        }
        return "Valid";
    }
}