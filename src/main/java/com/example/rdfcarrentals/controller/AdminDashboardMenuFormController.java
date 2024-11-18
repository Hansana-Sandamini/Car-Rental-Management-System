package com.example.rdfcarrentals.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminDashboardMenuFormController implements Initializable {

    @FXML
    private AnchorPane adminDashboardMenu;

    @FXML
    private AnchorPane adminPane;

    @FXML
    private Button btnCars;

    @FXML
    private Button btnCashiers;

    @FXML
    private Button btnCredits;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnDrivers;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPayments;

    @FXML
    private Button btnRepairs;

    @FXML
    private Button btnReports;

    @FXML
    private Button btnReservations;

    @FXML
    private AnchorPane adminDashboardRightPane;

    public void navigateTo(String fxmlPath) {
        try {
            adminDashboardRightPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            adminDashboardRightPane.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load page...!").show();
            e.printStackTrace();
        }
    }

    private void resetButtonStyles() {
        btnCars.setStyle("-fx-background-color:  white;");
        btnCashiers.setStyle("-fx-background-color:  white;");
        btnCredits.setStyle("-fx-background-color:  white;");
        btnCustomers.setStyle("-fx-background-color:  white;");
        btnDashboard.setStyle("-fx-background-color:  white;");
        btnDrivers.setStyle("-fx-background-color:  white;");
        btnPayments.setStyle("-fx-background-color:  white;");
        btnRepairs.setStyle("-fx-background-color:  white;");
        btnReports.setStyle("-fx-background-color:  white;");
        btnReservations.setStyle("-fx-background-color:  white;");
    }


    @FXML
    void btnCarsOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/CarsForm.fxml");
        btnCars.setStyle("-fx-background-color:  f2e9e4;");
    }

    @FXML
    void btnCashiersOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/CashierSignUpForm.fxml");
        btnCashiers.setStyle("-fx-background-color: f2e9e4;");
    }

    @FXML
    void btnCreditsOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/CreditsForm.fxml");
        btnCredits.setStyle("-fx-background-color: f2e9e4;");
    }

    @FXML
    void btnCustomersOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/CustomerForm.fxml");
        btnCustomers.setStyle("-fx-background-color: f2e9e4;");
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/AdminDashboardForm.fxml");
        btnDashboard.setStyle("-fx-background-color: f2e9e4;");
    }

    @FXML
    void btnDriversOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/DriverForm.fxml");
        btnDrivers.setStyle("-fx-background-color: f2e9e4;");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        resetButtonStyles();
        btnLogout.setStyle("-fx-background-color: f2e9e4;");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Logout?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {
            try {
                adminPane.getChildren().clear();
                AnchorPane load = FXMLLoader.load(getClass().getResource("/view/WelcomeForm.fxml"));
                adminPane.getChildren().add(load);
                new Alert(Alert.AlertType.INFORMATION, "Logged Out Successfully...!").show();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Fail to load page...!").show();
            }
        } else {
            btnLogout.setStyle("-fx-background-color: white;");
        }
    }

    @FXML
    void btnPaymentsOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/PaymentsForm.fxml");
        btnPayments.setStyle("-fx-background-color: f2e9e4;");
    }

    @FXML
    void btnRepairsOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/RepairsForm.fxml");
        btnRepairs.setStyle("-fx-background-color: f2e9e4;");
    }

    @FXML
    void btnReportsOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/ReportsForm.fxml");
        btnReports.setStyle("-fx-background-color: f2e9e4;");
    }

    @FXML
    void btnReservationsOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/ReservationsForm.fxml");
        btnReservations.setStyle("-fx-background-color: f2e9e4;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminDashboardRightPane.getChildren().clear();
        AnchorPane load = null;
        try {
            resetButtonStyles();
            load = FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"));
            btnDashboard.setStyle("-fx-background-color:  f2e9e4;");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adminDashboardRightPane.getChildren().add(load);
    }
}

