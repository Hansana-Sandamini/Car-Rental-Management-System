package com.example.rdfcarrentals.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CashierDashboardFormController {

    @FXML
    private AnchorPane addCustomerPane;

    @FXML
    private AnchorPane carListPane;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private AnchorPane cashierDashboardContent;

    @FXML
    private AnchorPane cashierDashboardHeadingPane;

    @FXML
    private AnchorPane cashierDashboardImagePane;

    @FXML
    private AnchorPane makePaymentPane;

    @FXML
    private AnchorPane makeReservationPane;

    public void navigateTo(String fxmlPath) {
        try {
            cashierDashboardContent.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            cashierDashboardContent.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load page...!").show();
        }
    }

    @FXML
    void addCustomerPaneOnClicked(MouseEvent event) {
        navigateTo("/view/CustomerForm.fxml");
    }

    @FXML
    void carListPaneOnClicked(MouseEvent event) {
        navigateTo("/view/CarsForm.fxml");
    }

    @FXML
    void makePaymentPaneOnClicked(MouseEvent event) {
        navigateTo("/view/PaymentsForm.fxml");
    }

    @FXML
    void makeReservationPaneOnClicked(MouseEvent event) {
        navigateTo("/view/ReservationsForm.fxml");
    }

}
