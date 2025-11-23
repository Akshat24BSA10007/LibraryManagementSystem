package services;

import models.Book;
import utils.FileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BookService handles all book-related operations
 * Manages book inventory and search functionality
 */
public class BookService {
    private List<Book> books;
    private FileHandler fileHandler;
    
    public BookService() {
        this.fileHandler = FileHandler.getInstance();
        this.books = fileHandler.loadBooks();
    }
    
    /**
     * Add a new book to the library
     */
    public boolean addBook(Book book) {
        // Check if book ID already exists
        if (findBookById(book.getBookId()) != null) {
            System.out.println("Error: Book with ID " + book.getBookId() + " already exists!");
            return false;
        }
        
        books.add(book);
        fileHandler.saveBooks(books);
        System.out.println("Book added successfully!");
        return true;
    }
    
    /**
     * Update existing book details
     */
    public boolean updateBook(Book updatedBook) {
        Book existingBook = findBookById(updatedBook.getBookId());
        if (existingBook == null) {
            System.out.println("Error: Book not found!");
            return false;
        }
        
        // Update book details
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setTotalQuantity(updatedBook.getTotalQuantity());
        
        fileHandler.saveBooks(books);
        System.out.println("Book updated successfully!");
        return true;
    }
    
    /**
     * Remove a book from the library
     */
    public boolean removeBook(String bookId) {
        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Error: Book not found!");
            return false;
        }
        
        // Check if book is currently issued
        if (book.getAvailableQuantity() < book.getTotalQuantity()) {
            System.out.println("Error: Cannot remove book. Some copies are currently issued!");
            return false;
        }
        
        books.remove(book);
        fileHandler.saveBooks(books);
        System.out.println("Book removed successfully!");
        return true;
    }
    
    /**
     * Find book by ID
     */
    public Book findBookById(String bookId) {
        return books.stream()
                .filter(book -> book.getBookId().equalsIgnoreCase(bookId))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Search books by title
     */
    public List<Book> searchByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Search books by author
     */
    public List<Book> searchByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Search books by category
     */
    public List<Book> searchByCategory(String category) {
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    /**
     * Search books by ISBN
     */
    public Book searchByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equalsIgnoreCase(isbn))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Get all books
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }
    
    /**
     * Get all available books
     */
    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }
    
    /**
     * Get all categories
     */
    public List<String> getAllCategories() {
        return books.stream()
                .map(Book::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }
    
    /**
     * Update book quantity (used during issue/return)
     */
    public boolean updateBookQuantity(String bookId, int change) {
        Book book = findBookById(bookId);
        if (book == null) {
            return false;
        }
        
        if (change < 0) {
            book.decrementAvailableQuantity();
        } else {
            book.incrementAvailableQuantity();
        }
        
        fileHandler.saveBooks(books);
        return true;
    }
    
    /**
     * Display all books in formatted manner
     */
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("           ALL BOOKS");
        System.out.println("========================================");
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println("========================================");
        System.out.println("Total books: " + books.size());
    }
    
    /**
     * Display available books only
     */
    public void displayAvailableBooks() {
        List<Book> availableBooks = getAvailableBooks();
        
        if (availableBooks.isEmpty()) {
            System.out.println("No books currently available.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("         AVAILABLE BOOKS");
        System.out.println("========================================");
        for (Book book : availableBooks) {
            System.out.println(book);
        }
        System.out.println("========================================");
        System.out.println("Total available books: " + availableBooks.size());
    }
}
