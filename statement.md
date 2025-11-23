# Library Management System - Project Statement

## Problem Statement

Libraries face significant challenges in managing their operations manually, including:
- Difficulty in tracking book availability and locations
- Time-consuming manual record-keeping for book issues and returns
- Lack of systematic fine calculation for overdue books
- Inefficient member management and borrowing history tracking
- No centralized system for librarians to monitor library operations

This project aims to develop a **Library Management System** that automates and streamlines library operations, making it easier for librarians to manage books, members, and transactions efficiently.

---

## Project Scope

The Library Management System is designed to digitize and automate core library operations through a Java-based desktop application. The system will provide:

### In Scope:
- Complete book inventory management (add, remove, search, update)
- Member registration and profile management
- Book issue and return processing with automated fine calculation
- Transaction history tracking
- Admin authentication and access control
- File-based data persistence
- Search functionality by book title, author, or category
- Overdue book tracking and fine management

### Out of Scope:
- Web-based interface (console/desktop only)
- Online payment gateway integration
- Email/SMS notifications
- Multi-library branch management
- E-book or digital resource management
- Integration with external library databases

---

## Target Users

### Primary Users:
1. **Librarians/Admins**
   - Manage complete library operations
   - Add/remove books from inventory
   - Register new members
   - Process book issues and returns
   - Generate reports and view statistics

2. **Library Members (Students/Faculty)**
   - Search for available books
   - View borrowing history
   - Check due dates and pending fines

---

## High-Level Features

### 1. Book Management Module
- Add new books with details (title, author, ISBN, category, quantity)
- Update book information
- Remove books from inventory
- Search books by title, author, category, or ISBN
- View all available books
- Track book availability status

### 2. Member Management Module
- Register new library members
- Update member information
- View member details and borrowing history
- Track active borrowings per member
- Member search functionality

### 3. Transaction Management Module
- Issue books to members with due date assignment
- Process book returns
- Automatic fine calculation for overdue books
- View transaction history (all/by member/by book)
- Track currently issued books
- Generate overdue book reports

### 4. Authentication & Access Control
- Admin login system
- Secure password handling
- Role-based access (admin operations vs member queries)

### 5. Data Persistence
- File-based storage for books, members, and transactions
- Automatic save on every operation
- Data loading on application startup
- Backup and recovery capabilities

---

## Technical Constraints

- **Language:** Pure Java (Core Java only)
- **UI:** Console-based menu-driven interface
- **Storage:** File I/O (text files or serialization)
- **No external frameworks:** No Spring, Hibernate, or web frameworks
- **No database:** File-based persistence only
- **Java Version:** JDK 8 or higher

---

## Success Criteria

The project will be considered successful if:
1. All three major modules (Book, Member, Transaction) are fully functional
2. Data persists across application restarts
3. Fine calculation works accurately based on overdue days
4. Search functionality returns correct results
5. No data loss occurs during normal operations
6. Code follows OOP principles and is well-documented
7. Application handles errors gracefully without crashing

---

## Expected Outcomes

- A fully functional Library Management System
- Reduced manual effort in library operations
- Accurate tracking of books and transactions
- Automated fine calculation
- Easy-to-use interface for librarians
- Scalable codebase for future enhancements

---

**Project Duration:** 4-5 weeks  
**Technology Stack:** Core Java, File I/O, Collections Framework  
**Development Approach:** Iterative development with modular design
