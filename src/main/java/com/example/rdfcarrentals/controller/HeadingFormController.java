package com.example.rdfcarrentals.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class HeadingFormController implements Initializable {

    @FXML
    private AnchorPane HeadingPane;

    @FXML
    private Label lblFormName;

    @FXML
    private Label lblHeadingUserName;

    public void setFormName(String formName) {
        lblFormName.setText(formName);
    }

    public void setHeadingUserName(String headingUserName) {
        lblHeadingUserName.setText(CashierLoginFormController.name);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFormName("CashierDashboardMenuFormController");
        setHeadingUserName(CashierLoginFormController.name);
    }
}

