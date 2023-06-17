module com.example.familybudget {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.familybudget to javafx.fxml;
    exports com.example.familybudget;
    exports com.example.familybudget.controller;
    opens com.example.familybudget.controller to javafx.fxml;
}