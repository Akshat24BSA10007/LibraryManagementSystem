package utils;

import java.util.regex.Pattern;

/**
 * Validator class provides input validation utilities
 * Validates email, phone, ISBN, and other inputs
 */
public class Validator {
    
    // Regex patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^[0-9]{10}$"
    );
    
    private static final Pattern ISBN_PATTERN = Pattern.compile(
        "^(97[89])?[0-9]{10}$"
    );
    
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile(
        "^[A-Za-z0-9]+$"
    );
    
    /**
     * Validate email address
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Validate phone number (10 digits)
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }
    
    /**
     * Validate ISBN (10 or 13 digits)
     */
    public static boolean isValidISBN(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        return ISBN_PATTERN.matcher(isbn.trim()).matches();
    }
    
    /**
     * Validate alphanumeric string (for IDs)
     */
    public static boolean isAlphanumeric(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        return ALPHANUMERIC_PATTERN.matcher(str.trim()).matches();
    }
    
    /**
     * Validate string is not empty
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    /**
     * Validate string length
     */
    public static boolean isValidLength(String str, int minLength, int maxLength) {
        if (str == null) {
            return false;
        }
        int length = str.trim().length();
        return length >= minLength && length <= maxLength;
    }
    
    /**
     * Validate positive integer
     */
    public static boolean isPositiveInteger(String str) {
        try {
            int value = Integer.parseInt(str.trim());
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate non-negative integer
     */
    public static boolean isNonNegativeInteger(String str) {
        try {
            int value = Integer.parseInt(str.trim());
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate member type
     */
    public static boolean isValidMemberType(String memberType) {
        if (memberType == null) {
            return false;
        }
        String type = memberType.trim().toUpperCase();
        return type.equals("STUDENT") || type.equals("FACULTY");
    }
    
    /**
     * Sanitize string input (remove leading/trailing spaces)
     */
    public static String sanitize(String input) {
        if (input == null) {
            return "";
        }
        return input.trim();
    }
    
    /**
     * Validate book ID format (e.g., B001, B002)
     */
    public static boolean isValidBookId(String bookId) {
        if (bookId == null || bookId.trim().isEmpty()) {
            return false;
        }
        return bookId.trim().matches("^B[0-9]{3,}$");
    }
    
    /**
     * Validate member ID format (e.g., M001, M002)
     */
    public static boolean isValidMemberId(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            return false;
        }
        return memberId.trim().matches("^M[0-9]{3,}$");
    }
    
    /**
     * Display validation error message
     */
    public static void displayError(String field, String message) {
        System.out.println("Validation Error [" + field + "]: " + message);
    }
}
