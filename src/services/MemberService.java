package services;

import models.Member;
import utils.FileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MemberService handles all member-related operations
 * Manages member registration and profiles
 */
public class MemberService {
    private List<Member> members;
    private FileHandler fileHandler;
    
    public MemberService() {
        this.fileHandler = FileHandler.getInstance();
        this.members = fileHandler.loadMembers();
    }
    
    /**
     * Register a new member
     */
    public boolean registerMember(Member member) {
        // Check if member ID already exists
        if (findMemberById(member.getMemberId()) != null) {
            System.out.println("Error: Member with ID " + member.getMemberId() + " already exists!");
            return false;
        }
        
        // Check if email already exists
        if (findMemberByEmail(member.getEmail()) != null) {
            System.out.println("Error: Member with email " + member.getEmail() + " already exists!");
            return false;
        }
        
        members.add(member);
        fileHandler.saveMembers(members);
        System.out.println("Member registered successfully!");
        return true;
    }
    
    /**
     * Update member details
     */
    public boolean updateMember(Member updatedMember) {
        Member existingMember = findMemberById(updatedMember.getMemberId());
        if (existingMember == null) {
            System.out.println("Error: Member not found!");
            return false;
        }
        
        // Update member details
        existingMember.setName(updatedMember.getName());
        existingMember.setEmail(updatedMember.getEmail());
        existingMember.setPhone(updatedMember.getPhone());
        existingMember.setMemberType(updatedMember.getMemberType());
        
        fileHandler.saveMembers(members);
        System.out.println("Member updated successfully!");
        return true;
    }
    
    /**
     * Find member by ID
     */
    public Member findMemberById(String memberId) {
        return members.stream()
                .filter(member -> member.getMemberId().equalsIgnoreCase(memberId))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Find member by email
     */
    public Member findMemberByEmail(String email) {
        return members.stream()
                .filter(member -> member.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Search members by name
     */
    public List<Member> searchByName(String name) {
        return members.stream()
                .filter(member -> member.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Get all members
     */
    public List<Member> getAllMembers() {
        return new ArrayList<>(members);
    }
    
    /**
     * Get members by type (STUDENT or FACULTY)
     */
    public List<Member> getMembersByType(String memberType) {
        return members.stream()
                .filter(member -> member.getMemberType().equalsIgnoreCase(memberType))
                .collect(Collectors.toList());
    }
    
    /**
     * Update borrowed books count (used during issue/return)
     */
    public boolean updateBorrowedBooks(String memberId, int change) {
        Member member = findMemberById(memberId);
        if (member == null) {
            return false;
        }
        
        if (change < 0) {
            member.decrementBorrowedBooks();
        } else {
            member.incrementBorrowedBooks();
        }
        
        fileHandler.saveMembers(members);
        return true;
    }
    
    /**
     * Check if member can borrow more books
     */
    public boolean canMemberBorrow(String memberId) {
        Member member = findMemberById(memberId);
        if (member == null) {
            return false;
        }
        return member.canBorrow();
    }
    
    /**
     * Display all members
     */
    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("           ALL MEMBERS");
        System.out.println("========================================");
        for (Member member : members) {
            System.out.println(member);
        }
        System.out.println("========================================");
        System.out.println("Total members: " + members.size());
    }
    
    /**
     * Display member details
     */
    public void displayMemberDetails(String memberId) {
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("         MEMBER DETAILS");
        System.out.println("========================================");
        System.out.println(member);
        System.out.println("========================================");
    }
}
