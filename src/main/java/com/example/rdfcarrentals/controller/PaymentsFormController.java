package com.example.rdfcarrentals.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class PaymentsFormController {

    @FXML
    private Button btnAddPayment;

    @FXML
    private Button btnRefresh;

    @FXML
    private ComboBox<?> cmbReservationID;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colBillID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private TableColumn<?, ?> colPaymentID;

    @FXML
    private TableColumn<?, ?> colPaymentMethod;

    @FXML
    private TableColumn<?, ?> colReservationID;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private Label lblPaymentID;

    @FXML
    private AnchorPane paymentsContent;

    @FXML
    private TableView<?> tblPayments;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtFldAmount;

    @FXML
    private Label txtFldAvailabilityStatus;

    @FXML
    private TextField txtFldPaymentMethod;

    @FXML
    private TextField txtFldSearchHere;

    @FXML
    private DatePicker txtTime;

    @FXML
    void btnAddPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {

    }

    @FXML
    void cmbReservationIDOnAction(ActionEvent event) {

    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) {

    }

}
