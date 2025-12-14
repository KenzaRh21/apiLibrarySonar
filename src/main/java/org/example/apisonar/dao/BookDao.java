package org.example.apisonar.dao;


import org.example.apisonar.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    // PROBLÈME 1: Credentials en dur (vulnérabilité de sécurité)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password123";

    // PROBLÈME 2: Code dupliqué
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM books";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Book book = new Book();
                book.title = rs.getString("title");
                book.author = rs.getString("author");
                book.isbn = rs.getString("isbn");
                books.add(book);
            }
        } catch(SQLException e) {
            // PROBLÈME 3: Utilisation de System.out au lieu d'un logger
            System.out.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        }

        return books;
    }

    // PROBLÈME 2 (suite): Code dupliqué - méthode quasi identique
    public List<Book> getAllBooksDuplicate() {
        List<Book> books = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM books";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Book book = new Book();
                book.title = rs.getString("title");
                book.author = rs.getString("author");
                book.isbn = rs.getString("isbn");
                books.add(book);
            }
        } catch(SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }

        return books;
    }

    // PROBLÈME 4: SQL Injection
    public Book getBookByTitle(String title) {
        Book book = null;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();

            // Concaténation de chaînes - vulnérable à SQL injection
            String sql = "SELECT * FROM books WHERE title = '" + title + "'";
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()) {
                book = new Book();
                book.title = rs.getString("title");
                book.author = rs.getString("author");
                book.isbn = rs.getString("isbn");
            }
        } catch(SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }

        return book;
    }

    // PROBLÈME 5: Ressources non fermées
    public void addBook(Book book) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "INSERT INTO books (title, author, isbn) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, book.title);
            pstmt.setString(2, book.author);
            pstmt.setString(3, book.isbn);
            pstmt.executeUpdate();
            // Pas de fermeture des ressources!
        } catch(SQLException e) {
            System.out.println("Erreur d'ajout: " + e.getMessage());
        }
    }
}