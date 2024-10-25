package com.example.rdfcarrentals.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminLoginFormController implements Initializable {

    @FXML
    private AnchorPane adminLoginPane;

    @FXML
    private Button btnAdminLogin;

    @FXML
    private TextField txtFldCashierPassword;

    @FXML
    private TextField txtFldCashierUserName;


    @FXML
    void btnAdminLoginOnAction(ActionEvent event) throws IOException {
        String userName = txtFldCashierUserName.getText();
        String password = txtFldCashierPassword.getText();

        if(userName.equals("admin") && password.equals("1234")) {
            adminLoginPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/AdminDashboardMenuForm.fxml"));
            adminLoginPane.getChildren().add(load);
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
