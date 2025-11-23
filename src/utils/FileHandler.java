package utils;

import models.Admin;
import models.Book;
import models.Member;
import models.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileHandler manages all file I/O operations
 * Singleton pattern for centralized file management
 */
public class FileHandler {
    private static FileHandler instance;
    
    // File paths
    private static final String DATA_DIR = "data/";
    private static final String BOOKS_FILE = DATA_DIR + "books.txt";
    private static final String MEMBERS_FILE = DATA_DIR + "members.txt";
    private static final String TRANSACTIONS_FILE = DATA_DIR + "transactions.txt";
    private static final String ADMINS_FILE = DATA_DIR + "admins.txt";
    
    // Private constructor for singleton
    private FileHandler() {
        initializeDataDirectory();
    }
    
    /**
     * Get singleton instance
     */
    public static FileHandler getInstance() {
        if (instance == null) {
            instance = new FileHandler();
        }
        return instance;
    }
    
    /**
     * Initialize data directory and files if they don't exist
     */
    private void initializeDataDirectory() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
        // Create empty files if they don't exist
        createFileIfNotExists(BOOKS_FILE);
        createFileIfNotExists(MEMBERS_FILE);
        createFileIfNotExists(TRANSACTIONS_FILE);
        createFileIfNotExists(ADMINS_FILE);
    }
    
    /**
     * Create file if it doesn't exist
     */
    private void createFileIfNotExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating file: " + filePath);
                e.printStackTrace();
            }
        }
    }
    
    // ============ BOOK OPERATIONS ============
    
    /**
     * Save all books to file
     */
    public void saveBooks(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.write(book.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving books to file!");
            e.printStackTrace();
        }
    }
    
    /**
     * Load all books from file
     */
    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Book book = Book.fromFileString(line);
                    if (book != null) {
                        books.add(book);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, return empty list
            System.out.println("Books file not found. Starting with empty library.");
        } catch (IOException e) {
            System.err.println("Error loading books from file!");
            e.printStackTrace();
        }
        
        return books;
    }
    
    // ============ MEMBER OPERATIONS ============
    
    /**
     * Save all members to file
     */
    public void saveMembers(List<Member> members) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member member : members) {
                writer.write(member.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving members to file!");
            e.printStackTrace();
        }
    }
    
    /**
     * Load all members from file
     */
    public List<Member> loadMembers() {
        List<Member> members = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Member member = Member.fromFileString(line);
                    if (member != null) {
                        members.add(member);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Members file not found. Starting with no members.");
        } catch (IOException e) {
            System.err.println("Error loading members from file!");
            e.printStackTrace();
        }
        
        return members;
    }
    
    // ============ TRANSACTION OPERATIONS ============
    
    /**
     * Save all transactions to file
     */
    public void saveTransactions(List<Transaction> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving transactions to file!");
            e.printStackTrace();
        }
    }
    
    /**
     * Load all transactions from file
     */
    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Transaction transaction = Transaction.fromFileString(line);
                    if (transaction != null) {
                        transactions.add(transaction);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Transactions file not found. Starting with no transactions.");
        } catch (IOException e) {
            System.err.println("Error loading transactions from file!");
            e.printStackTrace();
        }
        
        return transactions;
    }
    
    // ============ ADMIN OPERATIONS ============
    
    /**
     * Save all admins to file
     */
    public void saveAdmins(List<Admin> admins) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMINS_FILE))) {
            for (Admin admin : admins) {
                writer.write(admin.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving admins to file!");
            e.printStackTrace();
        }
    }
    
    /**
     * Load all admins from file
     */
    public List<Admin> loadAdmins() {
        List<Admin> admins = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMINS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Admin admin = Admin.fromFileString(line);
                    if (admin != null) {
                        admins.add(admin);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Admins file not found. Will create default admin.");
        } catch (IOException e) {
            System.err.println("Error loading admins from file!");
            e.printStackTrace();
        }
        
        return admins;
    }
    
    /**
     * Clear all data (for testing purposes)
     */
    public void clearAllData() {
        try {
            new FileWriter(BOOKS_FILE).close();
            new FileWriter(MEMBERS_FILE).close();
            new FileWriter(TRANSACTIONS_FILE).close();
            new FileWriter(ADMINS_FILE).close();
            System.out.println("All data cleared successfully!");
        } catch (IOException e) {
            System.err.println("Error clearing data!");
            e.printStackTrace();
        }
    }
}
