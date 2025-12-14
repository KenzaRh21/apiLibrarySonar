package org.example.apisonar.controller;


import org.example.apisonar.dao.BookDao;
import org.example.apisonar.model.Book;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookDao bookDao = new BookDao();

    // PROBLÈME 1: Credentials admin en dur
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    @GetMapping
    public List<Book> getAllBooks() {
        // PROBLÈME 2: System.out pour logging
        System.out.println("Récupération de tous les livres");
        return bookDao.getAllBooks();
    }

    @GetMapping("/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        System.out.println("Recherche du livre: " + title);

        // PROBLÈME 3: Pas de gestion des null
        Book book = bookDao.getBookByTitle(title);
        return book; // Peut retourner null sans erreur explicite
    }

    @PostMapping
    public String addBook(@RequestBody Book book) {
        // PROBLÈME 4: Validation manquante
        if(book.title == null || book.title.isEmpty()) {
            System.out.println("Titre vide");
        }

        bookDao.addBook(book);
        System.out.println("Livre ajouté: " + book.title);
        return "Success";
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable int id) {
        // PROBLÈME 5: Méthode vide
        // TODO: implémenter la suppression
    }

    // PROBLÈME 6: Méthode privée non utilisée
    private void unusedMethod() {
        System.out.println("Cette méthode n'est jamais appelée");
    }

    // PROBLÈME 7: Complexité cyclomatique élevée
    public String validateBook(Book book) {
        if(book == null) {
            if(book.title == null) {
                if(book.author == null) {
                    if(book.isbn == null) {
                        return "Invalid";
                    } else {
                        return "OK";
                    }
                } else {
                    return "OK";
                }
            } else {
                return "OK";
            }
        } else {
            return "Valid";
        }
    }
}