package models;

import java.io.Serializable;

/**
 * Admin class represents a library administrator/librarian
 * Handles authentication and admin details
 */
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Attributes
    private String username;
    private String password;
    private String name;
    
    // Constructors
    public Admin() {
    }
    
    public Admin(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // Business methods
    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    
    // toString for display
    @Override
    public String toString() {
        return "Admin: " + name + " (Username: " + username + ")";
    }
    
    // String representation for file storage
    public String toFileString() {
        return username + "|" + password + "|" + name;
    }
    
    // Create Admin object from file string
    public static Admin fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 3) {
            return null;
        }
        
        return new Admin(parts[0], parts[1], parts[2]);
    }
}
