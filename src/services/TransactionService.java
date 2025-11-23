package services;

import models.Book;
import models.Member;
import models.Transaction;
import utils.FileHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TransactionService handles all transaction operations
 * Manages book issue, return, and fine calculations
 */
public class TransactionService {
    private List<Transaction> transactions;
    private FileHandler fileHandler;
    private BookService bookService;
    private MemberService memberService;
    private int transactionCounter;
    
    public TransactionService(BookService bookService, MemberService memberService) {
        this.fileHandler = FileHandler.getInstance();
        this.transactions = fileHandler.loadTransactions();
        this.bookService = bookService;
        this.memberService = memberService;
        this.transactionCounter = transactions.size() + 1;
    }
    
    /**
     * Issue a book to a member
     */
    public Transaction issueBook(String bookId, String memberId) {
        // Validate book
        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("Error: Book not found!");
            return null;
        }
        
        if (!book.isAvailable()) {
            System.out.println("Error: Book is not available!");
            return null;
        }
        
        // Validate member
        Member member = memberService.findMemberById(memberId);
        if (member == null) {
            System.out.println("Error: Member not found!");
            return null;
        }
        
        if (!member.canBorrow()) {
            System.out.println("Error: Member has reached borrowing limit (" + 
                             member.getMaxBooksAllowed() + " books)!");
            return null;
        }
        
        // Create transaction
        String transactionId = "TXN" + String.format("%05d", transactionCounter++);
        Transaction transaction = new Transaction(transactionId, bookId, memberId);
        
        // Update book and member
        bookService.updateBookQuantity(bookId, -1);
        memberService.updateBorrowedBooks(memberId, 1);
        
        // Save transaction
        transactions.add(transaction);
        fileHandler.saveTransactions(transactions);
        
        System.out.println("\n========================================");
        System.out.println("       BOOK ISSUED SUCCESSFULLY");
        System.out.println("========================================");
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Book: " + book.getTitle());
        System.out.println("Member: " + member.getName());
        System.out.println("Issue Date: " + transaction.getIssueDate());
        System.out.println("Due Date: " + transaction.getDueDate());
        System.out.println("========================================");
        
        return transaction;
    }
    
    /**
     * Return a book
     */
    public boolean returnBook(String transactionId) {
        Transaction transaction = findTransactionById(transactionId);
        
        if (transaction == null) {
            System.out.println("Error: Transaction not found!");
            return false;
        }
        
        if (transaction.getStatus().equals("RETURNED")) {
            System.out.println("Error: Book already returned!");
            return false;
        }
        
        // Mark as returned and calculate fine
        transaction.markReturned();
        
        // Update book and member
        bookService.updateBookQuantity(transaction.getBookId(), 1);
        memberService.updateBorrowedBooks(transaction.getMemberId(), -1);
        
        // Save transaction
        fileHandler.saveTransactions(transactions);
        
        // Get book and member details for display
        Book book = bookService.findBookById(transaction.getBookId());
        Member member = memberService.findMemberById(transaction.getMemberId());
        
        System.out.println("\n========================================");
        System.out.println("       BOOK RETURNED SUCCESSFULLY");
        System.out.println("========================================");
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Book: " + (book != null ? book.getTitle() : transaction.getBookId()));
        System.out.println("Member: " + (member != null ? member.getName() : transaction.getMemberId()));
        System.out.println("Return Date: " + transaction.getReturnDate());
        System.out.println("Overdue Days: " + transaction.getOverdueDays());
        System.out.println("Fine: ₹" + String.format("%.2f", transaction.getFine()));
        System.out.println("========================================");
        
        return true;
    }
    
    /**
     * Find transaction by ID
     */
    public Transaction findTransactionById(String transactionId) {
        return transactions.stream()
                .filter(txn -> txn.getTransactionId().equalsIgnoreCase(transactionId))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Get all transactions
     */
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
    
    /**
     * Get transactions by member ID
     */
    public List<Transaction> getTransactionsByMember(String memberId) {
        return transactions.stream()
                .filter(txn -> txn.getMemberId().equalsIgnoreCase(memberId))
                .collect(Collectors.toList());
    }
    
    /**
     * Get transactions by book ID
     */
    public List<Transaction> getTransactionsByBook(String bookId) {
        return transactions.stream()
                .filter(txn -> txn.getBookId().equalsIgnoreCase(bookId))
                .collect(Collectors.toList());
    }
    
    /**
     * Get currently issued books
     */
    public List<Transaction> getIssuedTransactions() {
        return transactions.stream()
                .filter(txn -> txn.getStatus().equals("ISSUED"))
                .collect(Collectors.toList());
    }
    
    /**
     * Get overdue transactions
     */
    public List<Transaction> getOverdueTransactions() {
        return transactions.stream()
                .filter(Transaction::isOverdue)
                .collect(Collectors.toList());
    }
    
    /**
     * Calculate total fines for overdue books
     */
    public double calculateTotalFines() {
        return getOverdueTransactions().stream()
                .mapToDouble(Transaction::calculateFine)
                .sum();
    }
    
    /**
     * Display all transactions
     */
    public void displayAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("         ALL TRANSACTIONS");
        System.out.println("========================================");
        for (Transaction txn : transactions) {
            System.out.println(txn);
        }
        System.out.println("========================================");
        System.out.println("Total transactions: " + transactions.size());
    }
    
    /**
     * Display overdue books report
     */
    public void displayOverdueReport() {
        List<Transaction> overdueList = getOverdueTransactions();
        
        if (overdueList.isEmpty()) {
            System.out.println("No overdue books!");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("         OVERDUE BOOKS REPORT");
        System.out.println("========================================");
        for (Transaction txn : overdueList) {
            System.out.println(txn);
            System.out.println("  → Overdue by: " + txn.getOverdueDays() + " days");
            System.out.println("  → Current Fine: ₹" + String.format("%.2f", txn.calculateFine()));
            System.out.println("----------------------------------------");
        }
        System.out.println("Total overdue books: " + overdueList.size());
        System.out.println("Total fines: ₹" + String.format("%.2f", calculateTotalFines()));
        System.out.println("========================================");
    }
}
