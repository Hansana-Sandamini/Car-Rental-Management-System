package com.example.rdfcarrentals.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DriverFormController {

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnViewDriverAssignment;

    @FXML
    private ComboBox<?> cmbAvailabilityStatus;

    @FXML
    private TableColumn<?, ?> colAvailabilityStatus;

    @FXML
    private TableColumn<?, ?> colContactNumber;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNic;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private AnchorPane driverContent;

    @FXML
    private AnchorPane driverFormPane;

    @FXML
    private TableView<?> tblDrivers;

    @FXML
    private Label txtFldAvailabilityStatus;

    @FXML
    private TextField txtFldContactNumber;

    @FXML
    private TextField txtFldEmail;

    @FXML
    private TextField txtFldNIC;

    @FXML
    private TextField txtFldName;

    @FXML
    private TextField txtFldSearchHere;

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnViewDriverAssignmentOnAction(ActionEvent event) {

    }

    @FXML
    void cmbAvailabilityStatusOnAction(ActionEvent event) {

    }

    @FXML
    void tblDriversOnAction(MouseEvent event) {

    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) {

    }

}
