package services;

import models.Admin;
import utils.FileHandler;

import java.util.List;

/**
 * AuthService handles authentication operations
 * Manages admin login and session
 */
public class AuthService {
    private List<Admin> admins;
    private FileHandler fileHandler;
    private Admin currentAdmin;
    
    public AuthService() {
        this.fileHandler = FileHandler.getInstance();
        this.admins = fileHandler.loadAdmins();
        this.currentAdmin = null;
        
        // Create default admin if none exists
        if (admins.isEmpty()) {
            Admin defaultAdmin = new Admin("admin", "admin123", "System Administrator");
            admins.add(defaultAdmin);
            fileHandler.saveAdmins(admins);
        }
    }
    
    /**
     * Authenticate admin with username and password
     */
    public boolean login(String username, String password) {
        Admin admin = findAdminByUsername(username);
        
        if (admin == null) {
            System.out.println("Error: Invalid username!");
            return false;
        }
        
        if (!admin.authenticate(password)) {
            System.out.println("Error: Invalid password!");
            return false;
        }
        
        currentAdmin = admin;
        System.out.println("\n========================================");
        System.out.println("   LOGIN SUCCESSFUL");
        System.out.println("========================================");
        System.out.println("Welcome, " + admin.getName() + "!");
        System.out.println("========================================\n");
        return true;
    }
    
    /**
     * Logout current admin
     */
    public void logout() {
        if (currentAdmin != null) {
            System.out.println("Goodbye, " + currentAdmin.getName() + "!");
            currentAdmin = null;
        }
    }
    
    /**
     * Check if admin is logged in
     */
    public boolean isLoggedIn() {
        return currentAdmin != null;
    }
    
    /**
     * Get current logged-in admin
     */
    public Admin getCurrentAdmin() {
        return currentAdmin;
    }
    
    /**
     * Find admin by username
     */
    private Admin findAdminByUsername(String username) {
        return admins.stream()
                .filter(admin -> admin.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Change password for current admin
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        if (currentAdmin == null) {
            System.out.println("Error: No admin logged in!");
            return false;
        }
        
        if (!currentAdmin.authenticate(oldPassword)) {
            System.out.println("Error: Old password is incorrect!");
            return false;
        }
        
        currentAdmin.setPassword(newPassword);
        fileHandler.saveAdmins(admins);
        System.out.println("Password changed successfully!");
        return true;
    }
}
