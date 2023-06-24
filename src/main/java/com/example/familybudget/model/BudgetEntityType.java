package com.example.familybudget.model;

public enum BudgetEntityType {

    INCOME("доход"), EXPENSE("расход");

    private String description;

    BudgetEntityType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
