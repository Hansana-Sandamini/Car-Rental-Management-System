package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.util.ClockUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardFormController implements Initializable {

    @FXML
    private AnchorPane adminDashboardContent;

    @FXML
    private AnchorPane adminDashboardHeadingPane;

    @FXML
    private Label lblCreditNotPaid;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotalSales;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClockUtil.startClock(lblDate, lblTime);
    }
}
