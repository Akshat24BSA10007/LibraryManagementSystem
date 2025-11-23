package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Transaction class represents a book issue/return transaction
 * Tracks borrowing details and calculates fines
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final double FINE_PER_DAY = 5.0; // Rs. 5 per day
    private static final int LOAN_PERIOD_DAYS = 14; // 2 weeks
    
    // Attributes
    private String transactionId;
    private String bookId;
    private String memberId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;
    private String status; // ISSUED or RETURNED
    
    // Constructors
    public Transaction() {
    }
    
    public Transaction(String transactionId, String bookId, String memberId) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = LocalDate.now();
        this.dueDate = issueDate.plusDays(LOAN_PERIOD_DAYS);
        this.returnDate = null;
        this.fine = 0.0;
        this.status = "ISSUED";
    }
    
    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    
    public String getMemberId() {
        return memberId;
    }
    
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    
    public LocalDate getIssueDate() {
        return issueDate;
    }
    
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
    public double getFine() {
        return fine;
    }
    
    public void setFine(double fine) {
        this.fine = fine;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    // Business methods
    public boolean isOverdue() {
        if (status.equals("RETURNED")) {
            return false;
        }
        return LocalDate.now().isAfter(dueDate);
    }
    
    public long getOverdueDays() {
        if (!isOverdue()) {
            return 0;
        }
        return ChronoUnit.DAYS.between(dueDate, LocalDate.now());
    }
    
    public double calculateFine() {
        if (status.equals("RETURNED")) {
            return this.fine; // Already calculated
        }
        
        long overdueDays = getOverdueDays();
        if (overdueDays > 0) {
            this.fine = overdueDays * FINE_PER_DAY;
        } else {
            this.fine = 0.0;
        }
        return this.fine;
    }
    
    public void markReturned() {
        this.returnDate = LocalDate.now();
        this.status = "RETURNED";
        calculateFine();
    }
    
    // toString for display
    @Override
    public String toString() {
        String returnInfo = (returnDate != null) ? returnDate.format(DATE_FORMATTER) : "Not Returned";
        return String.format("TxnID: %s | BookID: %s | MemberID: %s | Issued: %s | Due: %s | Returned: %s | Fine: ₹%.2f | Status: %s",
                transactionId, bookId, memberId, 
                issueDate.format(DATE_FORMATTER), 
                dueDate.format(DATE_FORMATTER), 
                returnInfo, fine, status);
    }
    
    // String representation for file storage
    public String toFileString() {
        String returnDateStr = (returnDate != null) ? returnDate.format(DATE_FORMATTER) : "NULL";
        return transactionId + "|" + bookId + "|" + memberId + "|" + 
               issueDate.format(DATE_FORMATTER) + "|" + 
               dueDate.format(DATE_FORMATTER) + "|" + 
               returnDateStr + "|" + fine + "|" + status;
    }
    
    // Create Transaction object from file string
    public static Transaction fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 8) {
            return null;
        }
        
        Transaction transaction = new Transaction();
        transaction.setTransactionId(parts[0]);
        transaction.setBookId(parts[1]);
        transaction.setMemberId(parts[2]);
        transaction.setIssueDate(LocalDate.parse(parts[3], DATE_FORMATTER));
        transaction.setDueDate(LocalDate.parse(parts[4], DATE_FORMATTER));
        
        if (!parts[5].equals("NULL")) {
            transaction.setReturnDate(LocalDate.parse(parts[5], DATE_FORMATTER));
        }
        
        transaction.setFine(Double.parseDouble(parts[6]));
        transaction.setStatus(parts[7]);
        
        return transaction;
    }
}
