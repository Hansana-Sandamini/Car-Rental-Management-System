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

    public static String userName;
    public static String name;

    @FXML
    void btnCashierLoginOnAction(ActionEvent event) throws IOException {
        login();
    }

    private void login() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM cashier WHERE username=?",txtFldCashierUserName.getText());
            if (resultSet.next()){
                txtFldCashierUserName.setStyle(";-fx-border-color: #7367F0;");
                if (resultSet.getString(2).equals(txtFldCashierPassword.getText())){
                    txtFldCashierPassword.setStyle(";-fx-border-color: #7367F0;");
                    userName = resultSet.getString(1);
                    name = resultSet.getString(3);
                    cashierLoginPane.getChildren().clear();
                    AnchorPane load = FXMLLoader.load(getClass().getResource("/view/CashierDashboardMenuForm.fxml"));
                    cashierLoginPane.getChildren().add(load);
                }else {
                    txtFldCashierPassword.setStyle(";-fx-border-color: red;");
                    new Alert(Alert.AlertType.ERROR, "Wrong Password. Please Try Again...!").show();
                }
            }else {
                txtFldCashierUserName.setStyle(";-fx-border-color: red;");
                new Alert(Alert.AlertType.ERROR, "Wrong Username. Please Try Again...!").show();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cashierLoginBackIconOnAction(MouseEvent event) throws IOException {
        cashierLoginPane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/WelcomeForm.fxml"));
        cashierLoginPane.getChildren().add(load);
    }

}
