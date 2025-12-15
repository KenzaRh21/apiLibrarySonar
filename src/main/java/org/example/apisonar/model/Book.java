package org.example.apisonar.model;

import java.util.Date;

/**
 * Représente un livre dans la bibliothèque
 */
public class Book {

    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;
    private Date publicationDate; // (M6) Laissez le champ, il est utilisé dans le DAO corrigé

    public Book() {}

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
        this.publicationDate = new Date(); // Initialisation par défaut
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // (M10 Correction) Convention de nommage pour les booléens : isAvailable()
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
}