package org.example.apisonar.dao;

import org.example.apisonar.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

@Repository
public class BookDao {

    // (M4 Correction) Utilisation du Logger JUL (si Lombok n'est pas utilisé)
    private static final Logger LOGGER = Logger.getLogger(BookDao.class.getName());

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    /**
     * (M5 Correction) Méthode helper pour gérer la connexion.
     * En environnement Spring, on préfèrerait JdbcTemplate.
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPassword);
    }

    /**
     * Facteur de code: Mappe les colonnes du ResultSet à un objet Book.
     */
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setIsbn(rs.getString("isbn"));
        // (M6) Ajout des champs isAvailable et publicationDate pour la complétude du mapping
        book.setIsAvailable(rs.getBoolean("is_available"));
        book.setPublicationDate(rs.getDate("publication_date"));
        return book;
    }

    /**
     * Récupère tous les livres de la base de données.
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT title, author, isbn, is_available, publication_date FROM books"; // Sélectionner les colonnes spécifiques

        // (M5 Correction) Utilisation du try-with-resources + méthode helper
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        } catch(SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur SQL lors de la récupération des livres", e);
        }
        return books;
    }

    /**
     * Récupère un livre par son titre.
     */
    public Book getBookByTitle(String title) {
        Book book = null;
        String sql = "SELECT title, author, isbn, is_available, publication_date FROM books WHERE title = ?"; // Sélectionner les colonnes spécifiques

        // (M5 Correction) Utilisation du try-with-resources + méthode helper
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);

            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    book = mapResultSetToBook(rs);
                }
            }
        } catch(SQLException e) {
            // (M7) Remplacement de la concaténation de chaînes dans le log (peut être considéré comme Code Smell/performance)
            LOGGER.log(Level.SEVERE, "Erreur SQL lors de la recherche du livre: {0}", new Object[]{e.getMessage()});
        }
        return book;
    }

    /**
     * Ajoute un livre à la base de données.
     */
    public void addBook(Book book) {
        // (S4 Security Hotspot) Attention à ne jamais utiliser de variable dans la structure de la requête SQL (nom de table, nom de colonne)
        String sql = "INSERT INTO books (title, author, isbn, is_available, publication_date) VALUES (?, ?, ?, ?, ?)";

        // (M5 Correction) Utilisation du try-with-resources + méthode helper
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setBoolean(4, book.getIsAvailable());
            // Ajout du champ publicationDate
            pstmt.setDate(5, new java.sql.Date(book.getPublicationDate().getTime()));
            pstmt.executeUpdate();

        } catch(SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur d'ajout du livre: {0}", new Object[]{e.getMessage()});
        }
    }
}