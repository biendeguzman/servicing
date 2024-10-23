//package com.example.workflow.model;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.Setter;
//
//@Entity
//public class ApprovalModel {
//    @Setter
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String loanRelease;
//    private String approvalStatus;
//
//    public ApprovalModel() {}
//
//    public ApprovalModel(String loanRelease) {
//        this.loanRelease = loanRelease;
//        this.approvalStatus = determineApprovalStatus(loanRelease);
//    }
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public String getLoanRelease() {
//        return loanRelease;
//    }
//
//    public void setLoanRelease(String loanRelease) {
//        this.loanRelease = loanRelease;
//        this.approvalStatus = determineApprovalStatus(loanRelease);
//    }
//
//    public String getApprovalStatus() {
//        return approvalStatus;
//    }
//
//    // Method to determine approval status based on loan release
//    private String determineApprovalStatus(String loanRelease) {
//        switch (loanRelease) {
//            case "Accepted":
//                return "Approved";
//            case "For Review":
//                return "Under Review";
//            case "Rejected":
//                return "Disapproved";
//            default:
//                return "Unknown Status";
//        }
//    }
//}
