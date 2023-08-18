package classes;

import java.util.ArrayList;
import java.util.List;

// ENCAPSULATION
public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private final int year;
    private final List<String> logs;
    private boolean isBorrowed;
    private boolean isActive;

    public Book(String isbn, String title, String author, int year, List<String> logs, boolean isBorrowed, boolean isActive) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.logs = logs;
        this.isBorrowed = isBorrowed;
        this.isActive = isActive;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getYear() {
        return this.year;
    }

    public boolean getBorrowStatus() {
        return this.isBorrowed;
    }

    public void setBorrowStatus(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }

    public boolean getActiveStatus() {
        return this.isActive;
    }

    public void setActiveStatus(boolean isActive) {
        this.isActive = isActive;
    }

    public List<String> getLogs() {
        return this.logs;
    }

    public void addLog(String message) {
        this.logs.add(message);
    }
}
