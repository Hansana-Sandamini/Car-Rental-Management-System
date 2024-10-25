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

public class RepairsFormController {

    @FXML
    private Button btnAddRepair;

    @FXML
    private Button btnRefresh;

    @FXML
    private ComboBox<?> cmbLicensePlateNo;

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colLicensePlateNo;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private TableColumn<?, ?> colRepairID;

    @FXML
    private Label lblRepairID;

    @FXML
    private AnchorPane repairsContent;

    @FXML
    private TableView<?> tblRepairs;

    @FXML
    private DatePicker txtDate;

    @FXML
    private Label txtFldAvailabilityStatus;

    @FXML
    private TextField txtFldCost;

    @FXML
    private TextField txtFldDescription;

    @FXML
    private TextField txtFldSearchHere;

    @FXML
    void btnAddRepairOnAction(ActionEvent event) {

    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {

    }

    @FXML
    void cmbLicensePlateNoOnAction(ActionEvent event) {

    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) {

    }

}
