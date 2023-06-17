package com.example.familybudget.controller;

import com.example.familybudget.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    Stage stage = new Stage();

    public MainController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("input-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //stage.setTitle("Семейный бюджет");
        stage.setScene(scene);
        //stage.show();
    }

}