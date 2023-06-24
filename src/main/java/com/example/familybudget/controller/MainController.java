package com.example.familybudget.controller;

import com.example.familybudget.Application;
import com.example.familybudget.model.BudgetEntity;
import com.example.familybudget.model.BudgetEntityType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class MainController {

    private Stage stage = new Stage();

    private InputController inputController;

    private ArrayList<BudgetEntity> budgetEntityArrayList = new ArrayList<>();

    @FXML
    ComboBox typeComboBox;

    @FXML
    CheckBox fromCheckBox;

    @FXML
    CheckBox toCheckBox;

    @FXML
    DatePicker fromDatePicker;

    @FXML
    DatePicker toDatePicker;

    @FXML
    TableView<BudgetEntity> budgetTableView;

    @FXML
    Button addButton;

    @FXML
    Button editButton;

    @FXML
    Button removeButton;

    @FXML
    Label totalAmountLabel;
    /*
        @FXML
        TableColumn<BalanceRow, String> numberTableColumn;
    */
    @FXML
    TableColumn nameTableColumn;

    @FXML
    TableColumn typeTableColumn;

    @FXML
    TableColumn dateTableColumn;

    @FXML
    TableColumn amountTableColumn;

    public MainController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("input-view.fxml"));
        inputController = new InputController(stage);
        fxmlLoader.setController(inputController);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setOnShown(event -> inputController.loadAction());
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.setScene(scene);
    }

    @FXML
    private void initialize() {
        budgetTableView.setPlaceholder(new Label("данные отсутствуют"));
        typeComboBox.getItems().addAll("любой", "доход", "расход");
        typeComboBox.getSelectionModel().selectFirst();
        fromDatePicker.setValue(LocalDate.now());
        toDatePicker.setValue(LocalDate.now());

        nameTableColumn = new TableColumn<BudgetEntity, String>("name");
        typeTableColumn = new TableColumn<BudgetEntity, BudgetEntityType>("type");
        dateTableColumn = new TableColumn<BudgetEntity, LocalDate>("date");
        amountTableColumn = new TableColumn<BudgetEntity, BigDecimal>("amount");
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("budget.dat"));
            budgetEntityArrayList = (ArrayList<BudgetEntity>) objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        /*
        budgetEntityArrayList.add(new BudgetEntity("1", BudgetEntityType.INCOME,
                LocalDate.now().minusDays(1), BigDecimal.ONE));
        budgetEntityArrayList.add(new BudgetEntity("2", BudgetEntityType.EXPENSE,
                LocalDate.now().plusDays(1), BigDecimal.TWO));
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("budget.dat"));
            objectOutputStream.writeObject(budgetEntityArrayList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

 */
        FillTableView();
    }

    @FXML
    private void fromCheckBoxAction() {
        if (fromCheckBox.isSelected()) {
            fromDatePicker.setDisable(false);
            filterByFromDate();
        } else {
            fromDatePicker.setDisable(true);
            FillTableView();
        }
    }

    @FXML
    private void toCheckBoxAction() {
        if (toCheckBox.isSelected()) {
            toDatePicker.setDisable(false);
            filterByToDate();
        } else {
            toDatePicker.setDisable(true);
            FillTableView();
        }
    }

    @FXML
    private void fromDatePickerAction() {
        filterByFromDate();
    }

    @FXML
    private void toDatePickerAction() {
        filterByToDate();
    }

    @FXML
    private void addButtonAction() {
        stage.setTitle("Добавление записи");
        inputController.setBudgetEntity(null);
        stage.showAndWait();
        if (inputController.getBudgetEntity() != null) {
            budgetEntityArrayList.add(inputController.getBudgetEntity());
        }
        FillTableView();
    }

    @FXML
    private void editButtonAction() {
        stage.setTitle("Редактирование записи");
        BudgetEntity budgetEntity = budgetTableView.getSelectionModel().getSelectedItem();
        inputController.setBudgetEntity(budgetEntity);
        stage.showAndWait();
        if (inputController.getBudgetEntity() != null) {
            int index = budgetEntityArrayList.indexOf(budgetEntity);
            budgetEntityArrayList.set(index, inputController.getBudgetEntity());
        }
        FillTableView();
    }

    @FXML
    private void removeButtonAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Выделенная запись будет удалена.\nПродолжить?");
        alert.setTitle("Удаление записи");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            BudgetEntity budgetEntity = budgetTableView.getSelectionModel().getSelectedItem();
            budgetEntityArrayList.remove(budgetEntity);
            FillTableView();
        }
    }

    @FXML
    private void typeComboBoxAction() {
        FilterByType();
    }

    private void FillTableView() {
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<BudgetEntity, String>("name"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<BudgetEntity, BudgetEntityType>("type"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<BudgetEntity, LocalDate>("date"));
        amountTableColumn.setCellValueFactory(new PropertyValueFactory<BudgetEntity, BigDecimal>("amount"));
        budgetTableView.setItems(FXCollections.observableArrayList(budgetEntityArrayList));
        editButton.setDisable(budgetEntityArrayList.isEmpty());
        removeButton.setDisable(budgetEntityArrayList.isEmpty());
        getTotalAmount();
    }

    private void FilterByType() {
        ArrayList<BudgetEntity> filteredBudgetEntityArrayList = new ArrayList<>();
        for (BudgetEntity budgetEntity : budgetEntityArrayList) {
            switch (typeComboBox.getSelectionModel().getSelectedIndex()) {
                case 0:
                    filteredBudgetEntityArrayList.add(budgetEntity);
                case 1:
                    if (budgetEntity.getType().equals(BudgetEntityType.INCOME)) {
                        filteredBudgetEntityArrayList.add(budgetEntity);
                    }
                    break;
                case 2:
                    if (budgetEntity.getType().equals(BudgetEntityType.EXPENSE)) {
                        filteredBudgetEntityArrayList.add(budgetEntity);
                    }
                    break;
            }
        }
        budgetTableView.setItems(FXCollections.observableArrayList(filteredBudgetEntityArrayList));
        editButton.setDisable(filteredBudgetEntityArrayList.isEmpty());
        removeButton.setDisable(filteredBudgetEntityArrayList.isEmpty());
        getTotalAmount();
    }

    private void filterByFromDate() {
        ArrayList<BudgetEntity> filteredBudgetEntityArrayList = new ArrayList<>();
        for (BudgetEntity budgetEntity : budgetEntityArrayList) {
            if (budgetEntity.getDate().isEqual(fromDatePicker.getValue()) ||
                    budgetEntity.getDate().isAfter(fromDatePicker.getValue()))
                filteredBudgetEntityArrayList.add(budgetEntity);
        }
        budgetTableView.setItems(FXCollections.observableArrayList(filteredBudgetEntityArrayList));
        editButton.setDisable(filteredBudgetEntityArrayList.isEmpty());
        removeButton.setDisable(filteredBudgetEntityArrayList.isEmpty());
        getTotalAmount();
    }

    private void filterByToDate() {
        ArrayList<BudgetEntity> filteredBudgetEntityArrayList = new ArrayList<>();
        for (BudgetEntity budgetEntity : budgetEntityArrayList) {
            if (budgetEntity.getDate().isEqual(toDatePicker.getValue()) ||
                    budgetEntity.getDate().isBefore(toDatePicker.getValue()))
                filteredBudgetEntityArrayList.add(budgetEntity);
        }
        budgetTableView.setItems(FXCollections.observableArrayList(filteredBudgetEntityArrayList));
        editButton.setDisable(filteredBudgetEntityArrayList.isEmpty());
        removeButton.setDisable(filteredBudgetEntityArrayList.isEmpty());
        getTotalAmount();
    }

    private void getTotalAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        boolean isValid = true;
        for (BudgetEntity budgetEntity : budgetEntityArrayList) {
            switch (typeComboBox.getSelectionModel().getSelectedIndex()) {
                case 1:
                    isValid = budgetEntity.getType().equals(BudgetEntityType.INCOME);
                    break;
                case 2:
                    isValid = budgetEntity.getType().equals(BudgetEntityType.EXPENSE);
                    break;
            }
            if (isValid && fromCheckBox.isSelected()) {
                isValid = budgetEntity.getDate().isEqual(fromDatePicker.getValue()) ||
                        budgetEntity.getDate().isAfter(fromDatePicker.getValue());
            }
            if (isValid && toCheckBox.isSelected()) {
                isValid = budgetEntity.getDate().isEqual(toDatePicker.getValue()) ||
                        budgetEntity.getDate().isBefore(toDatePicker.getValue());
            }
            if (isValid) {
                totalAmount = totalAmount.add(budgetEntity.getAmount());
            }
        }
        totalAmountLabel.setText("Бюджет: " + totalAmount);
    }
}