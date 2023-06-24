package com.example.familybudget.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BudgetEntity implements Serializable {
private String name;
private BudgetEntityType type;
private LocalDate date;
private BigDecimal amount;

    public BudgetEntity(String name, BudgetEntityType type, LocalDate date, BigDecimal amount) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BudgetEntityType getType() {
        return type;
    }

    public void setType(BudgetEntityType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
