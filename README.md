# Library Management System

A comprehensive Java-based Library Management System designed to automate and streamline library operations including book management, member registration, and transaction processing.

---

## Project Overview

This Library Management System is a desktop application built using Core Java that helps librarians efficiently manage:
- Book inventory and cataloging
- Member registrations and profiles
- Book issue and return transactions
- Fine calculations for overdue books
- Transaction history and reports

The system uses a menu-driven console interface and file-based storage for data persistence.

---

## Features

### Book Management
-  Add new books to the library inventory
-  Update existing book details
-  Remove books from the system
-  Search books by title, author, category, or ISBN
-  View all available books
-  Track book availability and quantities

### Member Management
- Register new library members
-  Update member information
-  Search and view member details
-  View member borrowing history
-  Track active borrowings per member

### Transaction Management
-  Issue books to members with automatic due date calculation
-  Process book returns
-  Automatic fine calculation for overdue books (₹5 per day)
-  View complete transaction history
-  Track and report overdue books
-  Generate transaction reports

###  Authentication
- Admin login system
- Secure access control
- Password-protected operations

---

## Technologies Used

- **Language:** Java (JDK 8+)
- **UI:** Console-based menu interface
- **Data Storage:** File I/O (Text files)
- **Collections:** ArrayList, HashMap, TreeSet
- **Date/Time:** java.time.LocalDate API
- **I/O:** BufferedReader, FileWriter, Serialization

---

## Installation & Setup

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (VS Code, IntelliJ IDEA, Eclipse, or NetBeans)
- Git (for version control)

### Steps to Run

1. **Clone the repository**
git clone https://github.com/YOUR_USERNAME/LibraryManagementSystem.git cd LibraryManagementSystem

2. **Compile the project**
cd src javac Main.java models/.java services/.java utils/*.java

3. **Run the application**
java Main

4. **Default Admin Credentials**
- Username: `admin`
- Password: `admin123`

---

## How to Use

### Main Menu Options

========================================
LIBRARY MANAGEMENT SYSTEM
Admin Login

Search Books (Public)

Exit

Choose an option:

text

### After Admin Login

========================================
ADMIN DASHBOARD
Book Management

Member Management

Transaction Management

View Reports

Logout

Choose an option:

### Book Management Operations
- Add new book
- Update book details
- Remove book
- Search books
- View all books

### Member Management Operations
- Register new member
- Update member info
- Search member
- View all members
- View member borrowing history

### Transaction Operations
- Issue book to member
- Return book
- Calculate fines
- View all transactions
- View overdue books

---

## Sample Data

The system comes with sample data including:
- **10 Books** across various categories (Fiction, Science, History, Technology)
- **5 Members** (Students and Faculty)
- **Sample Transactions** for demonstration

---

## Testing

### Manual Testing Checklist
-  Add, update, remove books
-  Register and update members
-  Issue books and verify availability update
-  Return books and verify fine calculation
-  Search functionality for books and members
-  Data persistence across application restarts
-  Error handling for invalid inputs
-  Admin authentication

### Test Scenarios
1. Issue a book and return after due date - verify fine calculation
2. Try to issue more than 3 books to a member - verify limit enforcement
3. Search for non-existent book - verify error handling
4. Close and restart application - verify data persistence

---

## Design Patterns Used

- **Model-View-Controller (MVC):** Separation of concerns
- **Service Layer Pattern:** Business logic encapsulation
- **Singleton Pattern:** FileHandler for centralized file operations
- **Factory Pattern:** Object creation in services

---

## Future Enhancements

-  GUI interface using JavaFX or Swing
-  Database integration (MySQL/PostgreSQL)
-  Email notifications for due dates
-  Advanced analytics and reporting
-  Barcode scanning for books
-  Web-based interface
-  Mobile app integration
-  SMS reminders for overdue books

---

## Author

**Akshat Sharma**  
Registration No.: 24BSA10007
VIT Bhopal University  
Course: Programming in Java  

---

## License

This project is created for educational purposes as part of the Programming in Java course at VIT Bhopal University.

---

## Acknowledgments

- Course Instructor: Pramod Bhat
- VIT Bhopal University
- Java Documentation and Community

---

## Contact

For any queries regarding this project:
- Email: akshat.24bsa10007@vitbhopal.ac.in
- GitHub: Akshat24BSA10007(https://github.com/Akshat24BSA10007)

---

**Last Updated:** November 24, 2025
