package com.example.rdfcarrentals.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardMenuFormController implements Initializable {

    @FXML
    private AnchorPane adminDashboardMenu;

    @FXML
    private Button btnBuys;

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
    private Button btnSales;

    @FXML
    private AnchorPane adminDashboardRightPane;

    public void navigateTo(String fxmlPath) {
        try {
            adminDashboardRightPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            adminDashboardRightPane.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    @FXML
    void btnBuysOnAction(ActionEvent event) {

    }

    @FXML
    void btnCarsOnAction(ActionEvent event) {
        navigateTo("/view/CarsForm.fxml");
    }

    @FXML
    void btnCashiersOnAction(ActionEvent event) {

    }

    @FXML
    void btnCreditsOnAction(ActionEvent event) {
        navigateTo("/view/CreditsForm.fxml");
    }

    @FXML
    void btnCustomersOnAction(ActionEvent event) {
        navigateTo("/view/CustomerForm.fxml");
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        navigateTo("/view/AdminDashboardForm.fxml");
    }

    @FXML
    void btnDriversOnAction(ActionEvent event) {
        navigateTo("/view/DriverForm.fxml");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

    }

    @FXML
    void btnPaymentsOnAction(ActionEvent event) {
        navigateTo("/view/PaymentsForm.fxml");
    }

    @FXML
    void btnRepairsOnAction(ActionEvent event) {
        navigateTo("/view/RepairsForm.fxml");
    }

    @FXML
    void btnReportsOnAction(ActionEvent event) {
        navigateTo("/view/ReportsForm.fxml");
    }

    @FXML
    void btnReservationsOnAction(ActionEvent event) {
        navigateTo("/view/ReservationsForm.fxml");
    }

    @FXML
    void btnSalesOnAction(ActionEvent event) {
        navigateTo("/view/AdminSalesForm.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminDashboardRightPane.getChildren().clear();
        AnchorPane load = null;
        try {
            load = FXMLLoader.load(getClass().getResource("/view/AdminDashboardForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adminDashboardRightPane.getChildren().add(load);
    }
}

