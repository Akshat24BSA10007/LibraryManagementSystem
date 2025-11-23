package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Member class represents a library member (student/faculty)
 * Manages member information and borrowing privileges
 */
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    // Attributes
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private String memberType; // STUDENT or FACULTY
    private LocalDate registrationDate;
    private int borrowedBooks;
    private int maxBooksAllowed;
    
    // Constructors
    public Member() {
        this.registrationDate = LocalDate.now();
        this.borrowedBooks = 0;
        this.maxBooksAllowed = 3; // Default limit
    }
    
    public Member(String memberId, String name, String email, String phone, String memberType) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.memberType = memberType;
        this.registrationDate = LocalDate.now();
        this.borrowedBooks = 0;
        
        // Faculty can borrow more books
        if (memberType.equalsIgnoreCase("FACULTY")) {
            this.maxBooksAllowed = 5;
        } else {
            this.maxBooksAllowed = 3;
        }
    }
    
    // Getters and Setters
    public String getMemberId() {
        return memberId;
    }
    
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getMemberType() {
        return memberType;
    }
    
    public void setMemberType(String memberType) {
        this.memberType = memberType;
        // Update max books based on type
        if (memberType.equalsIgnoreCase("FACULTY")) {
            this.maxBooksAllowed = 5;
        } else {
            this.maxBooksAllowed = 3;
        }
    }
    
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }
    
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    public int getBorrowedBooks() {
        return borrowedBooks;
    }
    
    public void setBorrowedBooks(int borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
    
    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }
    
    public void setMaxBooksAllowed(int maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }
    
    // Business methods
    public boolean canBorrow() {
        return borrowedBooks < maxBooksAllowed;
    }
    
    public void incrementBorrowedBooks() {
        if (canBorrow()) {
            borrowedBooks++;
        }
    }
    
    public void decrementBorrowedBooks() {
        if (borrowedBooks > 0) {
            borrowedBooks--;
        }
    }
    
    // toString for display
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Type: %s | Email: %s | Phone: %s | Books: %d/%d | Registered: %s",
                memberId, name, memberType, email, phone, borrowedBooks, maxBooksAllowed, 
                registrationDate.format(DATE_FORMATTER));
    }
    
    // String representation for file storage
    public String toFileString() {
        return memberId + "|" + name + "|" + email + "|" + phone + "|" + 
               memberType + "|" + registrationDate.format(DATE_FORMATTER) + "|" + 
               borrowedBooks + "|" + maxBooksAllowed;
    }
    
    // Create Member object from file string
    public static Member fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 8) {
            return null;
        }
        
        Member member = new Member();
        member.setMemberId(parts[0]);
        member.setName(parts[1]);
        member.setEmail(parts[2]);
        member.setPhone(parts[3]);
        member.setMemberType(parts[4]);
        member.setRegistrationDate(LocalDate.parse(parts[5], DATE_FORMATTER));
        member.setBorrowedBooks(Integer.parseInt(parts[6]));
        member.setMaxBooksAllowed(Integer.parseInt(parts[7]));
        
        return member;
    }
}
