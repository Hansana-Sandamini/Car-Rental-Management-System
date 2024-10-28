package com.example.rdfcarrentals.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

public class ReportsFormController {

    @FXML
    private Button btnDurationReportGenerate;

    @FXML
    private Button btnMonthOverviewGenerate;

    @FXML
    private Button btnMonthReportGenerate;

    @FXML
    private Button btnYearOverviewGenerate;

    @FXML
    private Button btnYearReportGenerate;

    @FXML
    private ComboBox<?> cmbSelectMonth;

    @FXML
    private ComboBox<?> cmbSelectRange;

    @FXML
    private ComboBox<?> cmbSelectYear;

    @FXML
    private AnchorPane reportsContent;

    @FXML
    private DatePicker txtFrom;

    @FXML
    private DatePicker txtTo;

    @FXML
    void btnDurationReportGenerateOnAction(ActionEvent event) {

    }

    @FXML
    void btnMonthOverviewGenerateOnAction(ActionEvent event) {

    }

    @FXML
    void btnMonthReportGenerateOnAction(ActionEvent event) {

    }

    @FXML
    void btnYearOverviewGenerateOnAction(ActionEvent event) {

    }

    @FXML
    void btnYearReportGenerateOnAction(ActionEvent event) {

    }

}
