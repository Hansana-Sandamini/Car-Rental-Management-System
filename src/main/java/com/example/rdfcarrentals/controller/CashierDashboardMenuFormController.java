package com.example.rdfcarrentals.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CashierDashboardMenuFormController implements Initializable {

    @FXML
    private AnchorPane cashierDashboardRightPane;

    @FXML
    private AnchorPane cashierPane;

    @FXML
    private Button btnCars;

    @FXML
    private Button btnCredits;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnPayments;

    @FXML
    private Button btnRepairs;

    @FXML
    private Button btnReservations;

    @FXML
    private AnchorPane cashierDashboardMenu;

    @Setter
    private HeadingFormController headingFormController;

    public void navigateTo(String fxmlPath, String formName) {
        try {
            cashierDashboardRightPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            cashierDashboardRightPane.getChildren().add(load);

            if (headingFormController != null) {
                headingFormController.setFormName(formName);
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load page...!").show();
        }
    }

    private void resetButtonStyles() {
        btnCars.setStyle("-fx-background-color:  white;");
        btnCredits.setStyle("-fx-background-color:  white;");
        btnCustomers.setStyle("-fx-background-color:  white;");
        btnDashboard.setStyle("-fx-background-color:  white;");
        btnPayments.setStyle("-fx-background-color:  white;");
        btnRepairs.setStyle("-fx-background-color:  white;");
        btnReservations.setStyle("-fx-background-color:  white;");
    }

    @FXML
    void btnCarsOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/CarsForm.fxml", "Cars");
        btnCars.setStyle("-fx-background-color:  f2e9e4;");
    }

    @FXML
    void btnCreditsOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/CreditsForm.fxml", "Credits");
        btnCredits.setStyle("-fx-background-color:  f2e9e4;");
    }

    @FXML
    void btnCustomersOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/CustomerForm.fxml", "Customers");
        btnCustomers.setStyle("-fx-background-color:  f2e9e4;");
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/CashierDashboardForm.fxml", "Dashboard");
        btnDashboard.setStyle("-fx-background-color:  f2e9e4;");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        resetButtonStyles();
        btnLogout.setStyle("-fx-background-color: f2e9e4;");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Logout?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {
            try {
                cashierPane.getChildren().clear();
                AnchorPane load = FXMLLoader.load(getClass().getResource("/view/WelcomeForm.fxml"));
                cashierPane.getChildren().add(load);
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
        navigateTo("/view/PaymentsForm.fxml", "Payments");
        btnPayments.setStyle("-fx-background-color:  f2e9e4;");
    }

    @FXML
    void btnRepairsOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/RepairsForm.fxml", "Repairs");
        btnRepairs.setStyle("-fx-background-color:  f2e9e4;");
    }

    @FXML
    void btnReservationsOnAction(ActionEvent event) {
        resetButtonStyles();
        navigateTo("/view/ReservationsForm.fxml", "Reservations");
        btnReservations.setStyle("-fx-background-color:  f2e9e4;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cashierDashboardRightPane.getChildren().clear();
        AnchorPane load = null;
        try {
            resetButtonStyles();
            load = FXMLLoader.load(getClass().getResource("/view/CashierDashboardForm.fxml"));
            btnDashboard.setStyle("-fx-background-color:  f2e9e4;");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cashierDashboardRightPane.getChildren().add(load);
    }
}

