import models.Book;
import models.Member;
import models.Transaction;
import services.AuthService;
import services.BookService;
import services.MemberService;
import services.TransactionService;
import utils.Validator;

import java.util.List;
import java.util.Scanner;

/**
 * Main class - Entry point for Library Management System
 * Provides menu-driven console interface
 */
public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService;
    private static BookService bookService;
    private static MemberService memberService;
    private static TransactionService transactionService;
    
    public static void main(String[] args) {
        // Initialize services
        initializeServices();
        
        // Display welcome message
        displayWelcome();
        
        // Main application loop
        boolean running = true;
        while (running) {
            if (!authService.isLoggedIn()) {
                running = showPublicMenu();
            } else {
                running = showAdminMenu();
            }
        }
        
        // Cleanup
        scanner.close();
        System.out.println("\nThank you for using Library Management System!");
        System.out.println("Goodbye!\n");
    }
    
    /**
     * Initialize all services
     */
    private static void initializeServices() {
        authService = new AuthService();
        bookService = new BookService();
        memberService = new MemberService();
        transactionService = new TransactionService(bookService, memberService);
    }
    
    /**
     * Display welcome message
     */
    private static void displayWelcome() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   LIBRARY MANAGEMENT SYSTEM            ║");
        System.out.println("║   VIT Bhopal University                ║");
        System.out.println("╚════════════════════════════════════════╝\n");
    }
    
    /**
     * Show public menu (before login)
     */
    private static boolean showPublicMenu() {
        System.out.println("\n========================================");
        System.out.println("   MAIN MENU");
        System.out.println("========================================");
        System.out.println("1. Admin Login");
        System.out.println("2. Search Books (Public)");
        System.out.println("3. View Available Books");
        System.out.println("4. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                handleLogin();
                break;
            case "2":
                handlePublicBookSearch();
                break;
            case "3":
                bookService.displayAvailableBooks();
                break;
            case "4":
                return false;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
        
        return true;
    }
    
    /**
     * Show admin menu (after login)
     */
    private static boolean showAdminMenu() {
        System.out.println("\n========================================");
        System.out.println("   ADMIN DASHBOARD");
        System.out.println("========================================");
        System.out.println("1. Book Management");
        System.out.println("2. Member Management");
        System.out.println("3. Transaction Management");
        System.out.println("4. Reports");
        System.out.println("5. Logout");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                showBookManagementMenu();
                break;
            case "2":
                showMemberManagementMenu();
                break;
            case "3":
                showTransactionManagementMenu();
                break;
            case "4":
                showReportsMenu();
                break;
            case "5":
                authService.logout();
                System.out.println("Logged out successfully!");
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
        
        return true;
    }
    
    /**
     * Handle admin login
     */
    private static void handleLogin() {
        System.out.println("\n========================================");
        System.out.println("   ADMIN LOGIN");
        System.out.println("========================================");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        authService.login(username, password);
    }
    
    /**
     * Public book search (no login required)
     */
    private static void handlePublicBookSearch() {
        System.out.println("\n========================================");
        System.out.println("   SEARCH BOOKS");
        System.out.println("========================================");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by Category");
        System.out.println("4. Back");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                System.out.print("Enter book title: ");
                String title = scanner.nextLine().trim();
                List<Book> booksByTitle = bookService.searchByTitle(title);
                displayBookList(booksByTitle, "Search Results");
                break;
            case "2":
                System.out.print("Enter author name: ");
                String author = scanner.nextLine().trim();
                List<Book> booksByAuthor = bookService.searchByAuthor(author);
                displayBookList(booksByAuthor, "Search Results");
                break;
            case "3":
                System.out.print("Enter category: ");
                String category = scanner.nextLine().trim();
                List<Book> booksByCategory = bookService.searchByCategory(category);
                displayBookList(booksByCategory, "Search Results");
                break;
            case "4":
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    // ==================== BOOK MANAGEMENT ====================
    
    private static void showBookManagementMenu() {
        System.out.println("\n========================================");
        System.out.println("   BOOK MANAGEMENT");
        System.out.println("========================================");
        System.out.println("1. Add New Book");
        System.out.println("2. Update Book");
        System.out.println("3. Remove Book");
        System.out.println("4. Search Book");
        System.out.println("5. View All Books");
        System.out.println("6. View Available Books");
        System.out.println("7. Back");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                handleAddBook();
                break;
            case "2":
                handleUpdateBook();
                break;
            case "3":
                handleRemoveBook();
                break;
            case "4":
                handleBookSearch();
                break;
            case "5":
                bookService.displayAllBooks();
                break;
            case "6":
                bookService.displayAvailableBooks();
                break;
            case "7":
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private static void handleAddBook() {
        System.out.println("\n========================================");
        System.out.println("   ADD NEW BOOK");
        System.out.println("========================================");
        
        System.out.print("Book ID (e.g., B001): ");
        String bookId = scanner.nextLine().trim();
        
        if (!Validator.isValidBookId(bookId)) {
            System.out.println("Invalid Book ID format! Use format B001, B002, etc.");
            return;
        }
        
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        
        if (!Validator.isNotEmpty(title)) {
            System.out.println("Title cannot be empty!");
            return;
        }
        
        System.out.print("Author: ");
        String author = scanner.nextLine().trim();
        
        if (!Validator.isNotEmpty(author)) {
            System.out.println("Author cannot be empty!");
            return;
        }
        
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        
        if (!Validator.isValidISBN(isbn)) {
            System.out.println("Invalid ISBN format! Should be 10 or 13 digits.");
            return;
        }
        
        System.out.print("Category: ");
        String category = scanner.nextLine().trim();
        
        System.out.print("Total Quantity: ");
        String qtyStr = scanner.nextLine().trim();
        
        if (!Validator.isPositiveInteger(qtyStr)) {
            System.out.println("Quantity must be a positive number!");
            return;
        }
        
        int quantity = Integer.parseInt(qtyStr);
        
        Book book = new Book(bookId, title, author, isbn, category, quantity);
        bookService.addBook(book);
    }
    
    private static void handleUpdateBook() {
        System.out.println("\n========================================");
        System.out.println("   UPDATE BOOK");
        System.out.println("========================================");
        
        System.out.print("Enter Book ID to update: ");
        String bookId = scanner.nextLine().trim();
        
        Book existingBook = bookService.findBookById(bookId);
        if (existingBook == null) {
            System.out.println("Book not found!");
            return;
        }
        
        System.out.println("\nCurrent Details:");
        System.out.println(existingBook);
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("Title [" + existingBook.getTitle() + "]: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) title = existingBook.getTitle();
        
        System.out.print("Author [" + existingBook.getAuthor() + "]: ");
        String author = scanner.nextLine().trim();
        if (author.isEmpty()) author = existingBook.getAuthor();
        
        System.out.print("ISBN [" + existingBook.getIsbn() + "]: ");
        String isbn = scanner.nextLine().trim();
        if (isbn.isEmpty()) isbn = existingBook.getIsbn();
        
        System.out.print("Category [" + existingBook.getCategory() + "]: ");
        String category = scanner.nextLine().trim();
        if (category.isEmpty()) category = existingBook.getCategory();
        
        System.out.print("Total Quantity [" + existingBook.getTotalQuantity() + "]: ");
        String qtyStr = scanner.nextLine().trim();
        int quantity = qtyStr.isEmpty() ? existingBook.getTotalQuantity() : Integer.parseInt(qtyStr);
        
        Book updatedBook = new Book(bookId, title, author, isbn, category, quantity);
        updatedBook.setAvailableQuantity(existingBook.getAvailableQuantity());
        bookService.updateBook(updatedBook);
    }
    
    private static void handleRemoveBook() {
        System.out.println("\n========================================");
        System.out.println("   REMOVE BOOK");
        System.out.println("========================================");
        
        System.out.print("Enter Book ID to remove: ");
        String bookId = scanner.nextLine().trim();
        
        Book book = bookService.findBookById(bookId);
        if (book != null) {
            System.out.println("\nBook to remove:");
            System.out.println(book);
            System.out.print("\nAre you sure? (yes/no): ");
            String confirm = scanner.nextLine().trim();
            
            if (confirm.equalsIgnoreCase("yes")) {
                bookService.removeBook(bookId);
            } else {
                System.out.println("Operation cancelled.");
            }
        }
    }
    
    private static void handleBookSearch() {
        System.out.println("\n========================================");
        System.out.println("   SEARCH BOOK");
        System.out.println("========================================");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Title");
        System.out.println("3. Search by Author");
        System.out.println("4. Search by ISBN");
        System.out.println("5. Search by Category");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                System.out.print("Enter Book ID: ");
                String id = scanner.nextLine().trim();
                Book book = bookService.findBookById(id);
                if (book != null) {
                    System.out.println("\n" + book);
                } else {
                    System.out.println("Book not found!");
                }
                break;
            case "2":
                System.out.print("Enter Title: ");
                String title = scanner.nextLine().trim();
                displayBookList(bookService.searchByTitle(title), "Search Results");
                break;
            case "3":
                System.out.print("Enter Author: ");
                String author = scanner.nextLine().trim();
                displayBookList(bookService.searchByAuthor(author), "Search Results");
                break;
            case "4":
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine().trim();
                Book bookByIsbn = bookService.searchByIsbn(isbn);
                if (bookByIsbn != null) {
                    System.out.println("\n" + bookByIsbn);
                } else {
                    System.out.println("Book not found!");
                }
                break;
            case "5":
                System.out.print("Enter Category: ");
                String category = scanner.nextLine().trim();
                displayBookList(bookService.searchByCategory(category), "Search Results");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    // ==================== MEMBER MANAGEMENT ====================
    
    private static void showMemberManagementMenu() {
        System.out.println("\n========================================");
        System.out.println("   MEMBER MANAGEMENT");
        System.out.println("========================================");
        System.out.println("1. Register New Member");
        System.out.println("2. Update Member");
        System.out.println("3. Search Member");
        System.out.println("4. View All Members");
        System.out.println("5. View Member Details");
        System.out.println("6. Back");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                handleRegisterMember();
                break;
            case "2":
                handleUpdateMember();
                break;
            case "3":
                handleMemberSearch();
                break;
            case "4":
                memberService.displayAllMembers();
                break;
            case "5":
                handleViewMemberDetails();
                break;
            case "6":
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private static void handleRegisterMember() {
        System.out.println("\n========================================");
        System.out.println("   REGISTER NEW MEMBER");
        System.out.println("========================================");
        
        System.out.print("Member ID (e.g., M001): ");
        String memberId = scanner.nextLine().trim();
        
        if (!Validator.isValidMemberId(memberId)) {
            System.out.println("Invalid Member ID format! Use format M001, M002, etc.");
            return;
        }
        
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        
        if (!Validator.isNotEmpty(name)) {
            System.out.println("Name cannot be empty!");
            return;
        }
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        if (!Validator.isValidEmail(email)) {
            System.out.println("Invalid email format!");
            return;
        }
        
        System.out.print("Phone (10 digits): ");
        String phone = scanner.nextLine().trim();
        
        if (!Validator.isValidPhone(phone)) {
            System.out.println("Invalid phone number! Must be 10 digits.");
            return;
        }
        
        System.out.print("Member Type (STUDENT/FACULTY): ");
        String memberType = scanner.nextLine().trim().toUpperCase();
        
        if (!Validator.isValidMemberType(memberType)) {
            System.out.println("Invalid member type! Must be STUDENT or FACULTY.");
            return;
        }
        
        Member member = new Member(memberId, name, email, phone, memberType);
        memberService.registerMember(member);
    }
    
    private static void handleUpdateMember() {
        System.out.println("\n========================================");
        System.out.println("   UPDATE MEMBER");
        System.out.println("========================================");
        
        System.out.print("Enter Member ID to update: ");
        String memberId = scanner.nextLine().trim();
        
        Member existingMember = memberService.findMemberById(memberId);
        if (existingMember == null) {
            System.out.println("Member not found!");
            return;
        }
        
        System.out.println("\nCurrent Details:");
        System.out.println(existingMember);
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("Name [" + existingMember.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = existingMember.getName();
        
        System.out.print("Email [" + existingMember.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) email = existingMember.getEmail();
        
        System.out.print("Phone [" + existingMember.getPhone() + "]: ");
        String phone = scanner.nextLine().trim();
        if (phone.isEmpty()) phone = existingMember.getPhone();
        
        System.out.print("Member Type [" + existingMember.getMemberType() + "]: ");
        String memberType = scanner.nextLine().trim().toUpperCase();
        if (memberType.isEmpty()) memberType = existingMember.getMemberType();
        
        Member updatedMember = new Member(memberId, name, email, phone, memberType);
        memberService.updateMember(updatedMember);
    }
    
    private static void handleMemberSearch() {
        System.out.println("\n========================================");
        System.out.println("   SEARCH MEMBER");
        System.out.println("========================================");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Email");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                System.out.print("Enter Member ID: ");
                String id = scanner.nextLine().trim();
                Member member = memberService.findMemberById(id);
                if (member != null) {
                    System.out.println("\n" + member);
                } else {
                    System.out.println("Member not found!");
                }
                break;
            case "2":
                System.out.print("Enter Name: ");
                String name = scanner.nextLine().trim();
                displayMemberList(memberService.searchByName(name), "Search Results");
                break;
            case "3":
                System.out.print("Enter Email: ");
                String email = scanner.nextLine().trim();
                Member memberByEmail = memberService.findMemberByEmail(email);
                if (memberByEmail != null) {
                    System.out.println("\n" + memberByEmail);
                } else {
                    System.out.println("Member not found!");
                }
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private static void handleViewMemberDetails() {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        memberService.displayMemberDetails(memberId);
    }
    
    // ==================== TRANSACTION MANAGEMENT ====================
    
    private static void showTransactionManagementMenu() {
        System.out.println("\n========================================");
        System.out.println("   TRANSACTION MANAGEMENT");
        System.out.println("========================================");
        System.out.println("1. Issue Book");
        System.out.println("2. Return Book");
        System.out.println("3. View All Transactions");
        System.out.println("4. View Issued Books");
        System.out.println("5. View Member Transactions");
        System.out.println("6. Back");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                handleIssueBook();
                break;
            case "2":
                handleReturnBook();
                break;
            case "3":
                transactionService.displayAllTransactions();
                break;
            case "4":
                displayTransactionList(transactionService.getIssuedTransactions(), "Currently Issued Books");
                break;
            case "5":
                handleViewMemberTransactions();
                break;
            case "6":
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private static void handleIssueBook() {
        System.out.println("\n========================================");
        System.out.println("   ISSUE BOOK");
        System.out.println("========================================");
        
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        
        Member member = memberService.findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }
        
        System.out.println("Member: " + member.getName() + " (" + member.getMemberType() + ")");
        System.out.println("Books borrowed: " + member.getBorrowedBooks() + "/" + member.getMaxBooksAllowed());
        
        System.out.print("\nEnter Book ID: ");
        String bookId = scanner.nextLine().trim();
        
        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }
        
        System.out.println("Book: " + book.getTitle() + " by " + book.getAuthor());
        System.out.println("Available copies: " + book.getAvailableQuantity());
        
        System.out.print("\nConfirm issue? (yes/no): ");
        String confirm = scanner.nextLine().trim();
        
        if (confirm.equalsIgnoreCase("yes")) {
            transactionService.issueBook(bookId, memberId);
        } else {
            System.out.println("Operation cancelled.");
        }
    }
    
    private static void handleReturnBook() {
        System.out.println("\n========================================");
        System.out.println("   RETURN BOOK");
        System.out.println("========================================");
        
        System.out.print("Enter Transaction ID: ");
        String transactionId = scanner.nextLine().trim();
        
        Transaction transaction = transactionService.findTransactionById(transactionId);
        if (transaction == null) {
            System.out.println("Transaction not found!");
            return;
        }
        
        if (transaction.getStatus().equals("RETURNED")) {
            System.out.println("This book has already been returned!");
            return;
        }
        
        System.out.println("\nTransaction Details:");
        System.out.println(transaction);
        
        if (transaction.isOverdue()) {
            System.out.println("\n⚠ WARNING: Book is overdue!");
            System.out.println("Overdue days: " + transaction.getOverdueDays());
            System.out.println("Fine: ₹" + String.format("%.2f", transaction.calculateFine()));
        }
        
        System.out.print("\nConfirm return? (yes/no): ");
        String confirm = scanner.nextLine().trim();
        
        if (confirm.equalsIgnoreCase("yes")) {
            transactionService.returnBook(transactionId);
        } else {
            System.out.println("Operation cancelled.");
        }
    }
    
    private static void handleViewMemberTransactions() {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        
        List<Transaction> transactions = transactionService.getTransactionsByMember(memberId);
        displayTransactionList(transactions, "Member Transaction History");
    }
    
    // ==================== REPORTS ====================
    
    private static void showReportsMenu() {
        System.out.println("\n========================================");
        System.out.println("   REPORTS");
        System.out.println("========================================");
        System.out.println("1. Overdue Books Report");
        System.out.println("2. All Books Report");
        System.out.println("3. All Members Report");
        System.out.println("4. All Transactions Report");
        System.out.println("5. Statistics");
        System.out.println("6. Back");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                transactionService.displayOverdueReport();
                break;
            case "2":
                bookService.displayAllBooks();
                break;
            case "3":
                memberService.displayAllMembers();
                break;
            case "4":
                transactionService.displayAllTransactions();
                break;
            case "5":
                displayStatistics();
                break;
            case "6":
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private static void displayStatistics() {
        System.out.println("\n========================================");
        System.out.println("   SYSTEM STATISTICS");
        System.out.println("========================================");
        System.out.println("Total Books: " + bookService.getAllBooks().size());
        System.out.println("Available Books: " + bookService.getAvailableBooks().size());
        System.out.println("Total Members: " + memberService.getAllMembers().size());
        System.out.println("Total Transactions: " + transactionService.getAllTransactions().size());
        System.out.println("Currently Issued: " + transactionService.getIssuedTransactions().size());
        System.out.println("Overdue Books: " + transactionService.getOverdueTransactions().size());
        System.out.println("Total Fines: ₹" + String.format("%.2f", transactionService.calculateTotalFines()));
        System.out.println("========================================");
    }
    
    // ==================== HELPER METHODS ====================
    
    private static void displayBookList(List<Book> books, String title) {
        if (books.isEmpty()) {
            System.out.println("\nNo books found.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("   " + title);
        System.out.println("========================================");
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println("========================================");
        System.out.println("Total: " + books.size());
    }
    
    private static void displayMemberList(List<Member> members, String title) {
        if (members.isEmpty()) {
            System.out.println("\nNo members found.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("   " + title);
        System.out.println("========================================");
        for (Member member : members) {
            System.out.println(member);
        }
        System.out.println("========================================");
        System.out.println("Total: " + members.size());
    }
    
    private static void displayTransactionList(List<Transaction> transactions, String title) {
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions found.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("   " + title);
        System.out.println("========================================");
        for (Transaction txn : transactions) {
            System.out.println(txn);
        }
        System.out.println("========================================");
        System.out.println("Total: " + transactions.size());
    }
}
