package com.example.rdfcarrentals.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CashierLoginFormController {

    @FXML
    private Button btnCashierLogin;

    @FXML
    private AnchorPane cashierLoginPane;

    @FXML
    private TextField txtFldCashierPassword;

    @FXML
    private TextField txtFldCashierUserName;

    @FXML
    private ImageView cashierLoginBackIcon;

    @FXML
    void btnCashierLoginOnAction(ActionEvent event) throws IOException {
        String userName = txtFldCashierUserName.getText();
        String password = txtFldCashierPassword.getText();

        if(userName.equals("cashier") && password.equals("1234")) {
            cashierLoginPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/CashierDashboardMenuForm.fxml"));
            cashierLoginPane.getChildren().add(load);
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password...").show();
        }
    }

    @FXML
    void cashierLoginBackIconOnAction(MouseEvent event) throws IOException {
        cashierLoginPane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/WelcomeForm.fxml"));
        cashierLoginPane.getChildren().add(load);
    }
}
