package com.example.familybudget.controller;

import com.example.familybudget.model.BudgetEntity;
import com.example.familybudget.model.BudgetEntityType;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InputController {

    private BudgetEntity budgetEntity;

    public BudgetEntity getBudgetEntity() {
        return budgetEntity;
    }

    public void setBudgetEntity(BudgetEntity budgetEntity) {
        this.budgetEntity = budgetEntity;
    }

    @FXML
    private TextField nameTextField;

    @FXML
    private RadioButton incomeRadioButton;

    @FXML
    private RadioButton expenseRadioButton;

    @FXML
    private DatePicker dateDatePicker;

    @FXML
    private TextField amountTextField;

    @FXML
    private void initialize() {
        if (budgetEntity == null) {
            clearButtonAction();
        } else {
            nameTextField.setText(budgetEntity.getName());
            dateDatePicker.setValue(budgetEntity.getDate());
            amountTextField.setText(String.valueOf(budgetEntity.getAmount()));
            incomeRadioButton.setSelected(budgetEntity.getType()
                    .equals(BudgetEntityType.INCOME));
            expenseRadioButton.setSelected(budgetEntity.getType()
                    .equals(BudgetEntityType.EXPENSE));
        }
    }

    @FXML
    private void saveButtonAction() {
        double amount = Double.valueOf(amountTextField.getText());
        BudgetEntityType type = null;
        if (incomeRadioButton.isSelected()) {
            type = BudgetEntityType.INCOME;
        }
        if (expenseRadioButton.isSelected()) {
            type = BudgetEntityType.EXPENSE;
        }
        budgetEntity = new BudgetEntity(
                nameTextField.getText(),
                type,
                dateDatePicker.getValue(),
                BigDecimal.valueOf(amount)
        );
    }

    @FXML
    private void clearButtonAction() {
        nameTextField.clear();
        incomeRadioButton.setSelected(true);
        dateDatePicker.setValue(LocalDate.now());
        amountTextField.setText("0");
    }
}
