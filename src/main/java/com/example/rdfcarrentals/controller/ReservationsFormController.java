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

public class ReservationsFormController {

    @FXML
    private Button btnAddReservation;

    @FXML
    private Button btnViewReservationDetails;

    @FXML
    private ComboBox<?> cmbCashierUserName;

    @FXML
    private ComboBox<?> cmbCreditID;

    @FXML
    private ComboBox<?> cmbCustomerNIC;

    @FXML
    private ComboBox<?> cmbIsDriverWant;

    @FXML
    private TableColumn<?, ?> colCashierUserName;

    @FXML
    private TableColumn<?, ?> colCreditID;

    @FXML
    private TableColumn<?, ?> colCustomerNIC;

    @FXML
    private TableColumn<?, ?> colIsDriverWant;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private TableColumn<?, ?> colPickUpDate;

    @FXML
    private TableColumn<?, ?> colPickUpTime;

    @FXML
    private TableColumn<?, ?> colReservationID;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colReturnTime;

    @FXML
    private Label lblReservationID;

    @FXML
    private AnchorPane reservationsContent;

    @FXML
    private TableView<?> tblReservations;

    @FXML
    private Label txtFldAvailabilityStatus;

    @FXML
    private Label txtFldAvailabilityStatus1;

    @FXML
    private TextField txtFldSearchHere;

    @FXML
    private DatePicker txtPickUpDate;

    @FXML
    private DatePicker txtPickUpTime;

    @FXML
    private DatePicker txtReturnDate;

    @FXML
    private DatePicker txtReturnTime;

    @FXML
    private AnchorPane reservationFormPane;

    @FXML
    void btnAddReservationOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewReservationDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCashierUserNameOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCreditIDOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCustomerNICOnAction(ActionEvent event) {

    }

    @FXML
    void cmbIsDriverWantOnAction(ActionEvent event) {

    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) {

    }

}
