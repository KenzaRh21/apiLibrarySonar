package org.example.apisonar.model;

import java.util.Date;

/**
 * Représente un livre dans la bibliothèque
 */
public class Book {

    // PROBLÈME 1: Champs publics (violation d'encapsulation)
    public String title;
    public String author;
    public String isbn;
    public boolean isAvailable;
    public Date publicationDate;

    // PROBLÈME 2: Variable non utilisée
    private int unusedVariable = 0;

    public Book() {}

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    // PROBLÈME 3: Méthode récursive infinie
    public void recursiveMethod() {
        recursiveMethod();
    }

    // PROBLÈME 4: Méthode qui ne fait rien
    public void emptyMethod() {

    }

    // Getters et setters manquants volontairement pour créer des problèmes
}