package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * DateUtils provides date manipulation and calculation utilities
 * Handles date formatting, parsing, and calculations
 */
public class DateUtils {
    
    // Standard date format
    private static final DateTimeFormatter STANDARD_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    private static final DateTimeFormatter DISPLAY_FORMATTER = 
        DateTimeFormatter.ofPattern("dd MMM yyyy");
    
    /**
     * Get current date
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
    
    /**
     * Format date to standard format (yyyy-MM-dd)
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(STANDARD_FORMATTER);
    }
    
    /**
     * Format date for display (dd MMM yyyy)
     */
    public static String formatDateForDisplay(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DISPLAY_FORMATTER);
    }
    
    /**
     * Parse date from string (yyyy-MM-dd)
     */
    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, STANDARD_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + dateStr);
            return null;
        }
    }
    
    /**
     * Add days to a date
     */
    public static LocalDate addDays(LocalDate date, int days) {
        if (date == null) {
            return null;
        }
        return date.plusDays(days);
    }
    
    /**
     * Subtract days from a date
     */
    public static LocalDate subtractDays(LocalDate date, int days) {
        if (date == null) {
            return null;
        }
        return date.minusDays(days);
    }
    
    /**
     * Calculate days between two dates
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
    
    /**
     * Check if first date is before second date
     */
    public static boolean isBefore(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.isBefore(date2);
    }
    
    /**
     * Check if first date is after second date
     */
    public static boolean isAfter(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.isAfter(date2);
    }
    
    /**
     * Check if date is in the past
     */
    public static boolean isPast(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isBefore(LocalDate.now());
    }
    
    /**
     * Check if date is in the future
     */
    public static boolean isFuture(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isAfter(LocalDate.now());
    }
    
    /**
     * Check if date is today
     */
    public static boolean isToday(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.equals(LocalDate.now());
    }
    
    /**
     * Get due date (current date + loan period)
     */
    public static LocalDate getDueDate(int loanPeriodDays) {
        return LocalDate.now().plusDays(loanPeriodDays);
    }
    
    /**
     * Calculate overdue days (0 if not overdue)
     */
    public static long calculateOverdueDays(LocalDate dueDate) {
        if (dueDate == null) {
            return 0;
        }
        
        LocalDate today = LocalDate.now();
        if (today.isAfter(dueDate)) {
            return ChronoUnit.DAYS.between(dueDate, today);
        }
        return 0;
    }
    
    /**
     * Calculate fine based on overdue days
     */
    public static double calculateFine(LocalDate dueDate, double finePerDay) {
        long overdueDays = calculateOverdueDays(dueDate);
        return overdueDays * finePerDay;
    }
    
    /**
     * Get month name from date
     */
    public static String getMonthName(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.getMonth().toString();
    }
    
    /**
     * Get year from date
     */
    public static int getYear(LocalDate date) {
        if (date == null) {
            return 0;
        }
        return date.getYear();
    }
}
