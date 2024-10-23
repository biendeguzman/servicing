package com.example.workflow.model;

public class LoanEvaluationRequest {
    private LoanModel loanModel;
    private PersonalInfoModel personalInfoModel;
    private CollateralModel collateralModel;

    // Getter for LoanModel
    public LoanModel getLoanModel() {
        return loanModel;
    }

    // Setter for LoanModel
    public void setLoanModel(LoanModel loanModel) {
        this.loanModel = loanModel;
    }

    // Getter for PersonalInfoModel
    public PersonalInfoModel getPersonalInfoModel() {
        return personalInfoModel;
    }

    // Setter for PersonalInfoModel
    public void setPersonalInfoModel(PersonalInfoModel personalInfoModel) {
        this.personalInfoModel = personalInfoModel;
    }

    // Getter for CollateralModel
    public CollateralModel getCollateralModel() {
        return collateralModel;
    }

    // Setter for CollateralModel
    public void setCollateralModel(CollateralModel collateralModel) {
        this.collateralModel = collateralModel;
    }

}
