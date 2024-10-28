package com.example.rdfcarrentals.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeFormController implements Initializable {

    @FXML
    private Button btnAdmin;

    @FXML
    private Button btnCashier;

    @FXML
    private AnchorPane selectRolePane;

    @FXML
    private Pane selectRoleSidePane;

    @FXML
    private AnchorPane welcomePane;

    @FXML
    void navigateToAdminLoginForm(ActionEvent event) { navigateTo("/view/AdminLoginForm.fxml"); }

    @FXML
    void navigateToCashierLoginForm(ActionEvent event) { navigateTo("/view/CashierLoginForm.fxml"); }

    public void navigateTo(String fxmlPath) {
        try {
            welcomePane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            welcomePane.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load page...!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
