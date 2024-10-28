package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.tm.DriverTM;
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
    private Button btnRefresh;

    @FXML
    private Button btnViewDriverAssignment;

    @FXML
    private ComboBox<?> cmbAvailabilityStatus;

    @FXML
    private TableColumn<DriverTM, String> colAvailabilityStatus;

    @FXML
    private TableColumn<DriverTM, String> colContactNumber;

    @FXML
    private TableColumn<DriverTM, String> colEmail;

    @FXML
    private TableColumn<DriverTM, String> colName;

    @FXML
    private TableColumn<DriverTM, String> colNic;

    @FXML
    private TableColumn<DriverTM, String> colOption;

    @FXML
    private AnchorPane driverContent;

    @FXML
    private AnchorPane driverFormPane;

    @FXML
    private TableView<DriverTM> tblDrivers;

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
    void btnRefreshOnAction(ActionEvent event) {

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
