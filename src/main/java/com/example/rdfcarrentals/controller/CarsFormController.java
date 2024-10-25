package com.example.rdfcarrentals.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CarsFormController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private AnchorPane carsContent;

    @FXML
    private ComboBox<?> cmbAvailabilityStatus;

    @FXML
    private ComboBox<?> cmbTypeID;

    @FXML
    private TableColumn<?, ?> colAvailabilityStatus;

    @FXML
    private TableColumn<?, ?> colColour;

    @FXML
    private TableColumn<?, ?> colDailyRate;

    @FXML
    private TableColumn<?, ?> colLicensePlateNo;

    @FXML
    private TableColumn<?, ?> colModel;

    @FXML
    private TableColumn<?, ?> colMonthlyRate;

    @FXML
    private TableColumn<?, ?> colTypeID;

    @FXML
    private TableView<?> tblCars;

    @FXML
    private TextField txtFldColour;

    @FXML
    private TextField txtFldContactNumber1;

    @FXML
    private TextField txtFldDailyRate;

    @FXML
    private TextField txtFldLicensePlateNo;

    @FXML
    private TextField txtFldModel;

    @FXML
    private TextField txtFldSearchHere;

    @FXML
    private TextField txtFldTypeName;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void cmbAvailabilityStatusOnAction(ActionEvent event) {

    }

    @FXML
    void cmbTypeIDOnAction(ActionEvent event) {

    }

    @FXML
    void tblCarsOnClicked(MouseEvent event) {

    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) {

    }

}

