package models;

import java.io.Serializable;

/**
 * Book class represents a book in the library system
 * Contains all book-related information and operations
 */
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Attributes
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private int totalQuantity;
    private int availableQuantity;
    
    // Constructors
    public Book() {
    }
    
    public Book(String bookId, String title, String author, String isbn, 
                String category, int totalQuantity) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = totalQuantity;
    }
    
    // Getters and Setters
    public String getBookId() {
        return bookId;
    }
    
    public void setBookId(String bookId) {
        this.bookId = bookId;
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
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public int getTotalQuantity() {
        return totalQuantity;
    }
    
    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    
    public int getAvailableQuantity() {
        return availableQuantity;
    }
    
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
    
    // Business methods
    public boolean isAvailable() {
        return availableQuantity > 0;
    }
    
    public void incrementAvailableQuantity() {
        if (availableQuantity < totalQuantity) {
            availableQuantity++;
        }
    }
    
    public void decrementAvailableQuantity() {
        if (availableQuantity > 0) {
            availableQuantity--;
        }
    }
    
    // toString for display
    @Override
    public String toString() {
        return String.format("ID: %s | Title: %s | Author: %s | ISBN: %s | Category: %s | Available: %d/%d",
                bookId, title, author, isbn, category, availableQuantity, totalQuantity);
    }
    
    // String representation for file storage (pipe-delimited)
    public String toFileString() {
        return bookId + "|" + title + "|" + author + "|" + isbn + "|" + 
               category + "|" + totalQuantity + "|" + availableQuantity;
    }
    
    // Create Book object from file string
    public static Book fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 7) {
            return null;
        }
        
        Book book = new Book();
        book.setBookId(parts[0]);
        book.setTitle(parts[1]);
        book.setAuthor(parts[2]);
        book.setIsbn(parts[3]);
        book.setCategory(parts[4]);
        book.setTotalQuantity(Integer.parseInt(parts[5]));
        book.setAvailableQuantity(Integer.parseInt(parts[6]));
        
        return book;
    }
}
