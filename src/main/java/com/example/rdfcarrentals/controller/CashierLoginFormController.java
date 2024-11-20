package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.util.CrudUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CashierLoginFormController {

    @FXML
    private Button btnCashierLogin;

    @FXML
    private AnchorPane cashierLoginPane;

    @FXML
    private TextField txtFldCashierPassword;

    @FXML
    private TextField txtFldCashierUserName;

    @FXML
    private ImageView cashierLoginBackIcon;

    @FXML
    void btnCashierLoginOnAction(ActionEvent event) throws IOException {
        String userName = txtFldCashierUserName.getText();
        String password = txtFldCashierPassword.getText();

        if(userName.equals("cashier") && password.equals("1234")) {
            cashierLoginPane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/CashierDashboardMenuForm.fxml"));
            cashierLoginPane.getChildren().add(load);
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password...").show();
        }
    }

//    @FXML
//    void btnCashierLoginOnAction(ActionEvent event) throws IOException, SQLException {
//        String userName = txtFldCashierUserName.getText();
//        String password = txtFldCashierPassword.getText();
//
//        ResultSet resultSet = CrudUtil.execute(
//                "SELECT name FROM cashier WHERE username = ? AND password = ?",
//                userName, password
//        );
//
//        if (resultSet.next()) {
//            String cashierName = resultSet.getString("name");
//            cashierLoginPane.getChildren().clear();
//            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/CashierDashboardMenuForm.fxml"));
//            cashierLoginPane.getChildren().add(load);
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Invalid username or password...").show();
//        }
//    }

    @FXML
    void cashierLoginBackIconOnAction(MouseEvent event) throws IOException {
        cashierLoginPane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/WelcomeForm.fxml"));
        cashierLoginPane.getChildren().add(load);
    }
}
