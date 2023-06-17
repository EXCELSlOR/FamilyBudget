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

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

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
    TableView budgetTableView;

    @FXML
    Button addButton;

    @FXML
    Button editButton;

    @FXML
    Button removeButton;

    @FXML
    TableColumn numberTableColumn;

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
        Scene scene = new Scene(fxmlLoader.load());
        inputController = fxmlLoader.getController();
        stage.setScene(scene);
    }

    @FXML
    private void initialize() {
        budgetTableView.setPlaceholder(new Label("данные отсутствуют"));
        typeComboBox.getItems().addAll("любой", "доход", "расход");
        typeComboBox.getSelectionModel().selectFirst();
        fromDatePicker.setValue(LocalDate.now());
        toDatePicker.setValue(LocalDate.now());
        //FillTableView();
    }

    @FXML
    private void fromCheckBoxAction() {
        fromDatePicker.setDisable(!fromCheckBox.isSelected());
    }

    @FXML
    private void toCheckBoxAction() {
        toDatePicker.setDisable(!toCheckBox.isSelected());
    }

    @FXML
    private void addButtonAction() {
        stage.setTitle("Добавление записи");
        inputController.setBudgetEntity(null);
        stage.showAndWait();
        if (inputController.getBudgetEntity() != null) {
            budgetEntityArrayList.add(inputController.getBudgetEntity());
            FillTableView();
        }
    }

    @FXML
    private void editButtonAction() {
        stage.setTitle("Редактирование записи");
        stage.show();

    }

    @FXML
    private void removeButtonAction() {

    }

    private void FillTableView(){
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountTableColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        budgetTableView.setItems(FXCollections.observableArrayList(budgetEntityArrayList));
    }

}