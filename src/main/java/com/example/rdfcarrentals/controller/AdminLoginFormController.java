package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.util.CrudUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminLoginFormController implements Initializable {

    @FXML
    private AnchorPane adminLoginPane;

    @FXML
    private Button btnAdminLogin;

    @FXML
    private TextField txtFldAdminPassword;

    @FXML
    private TextField txtFldAdminUserName;

    @FXML
    private ImageView adminLoginBackIcon;

    public static String userName;
    public static String name1;

    @FXML
    void btnAdminLoginOnAction(ActionEvent event) throws IOException {
        login();
    }

    private void login() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM admin WHERE username=?",txtFldAdminUserName.getText());
            if (resultSet.next()){
                txtFldAdminUserName.setStyle(";-fx-border-color: #7367F0;");
                if (resultSet.getString(2).equals(txtFldAdminPassword.getText())){
                    txtFldAdminPassword.setStyle(";-fx-border-color: #7367F0;");
                    userName = resultSet.getString(1);
                    name1 = resultSet.getString(3);
                    adminLoginPane.getChildren().clear();
                    AnchorPane load = FXMLLoader.load(getClass().getResource("/view/AdminDashboardMenuForm.fxml"));
                    adminLoginPane.getChildren().add(load);
                }else {
                    txtFldAdminPassword.setStyle(";-fx-border-color: red;");
                    new Alert(Alert.AlertType.ERROR, "Wrong Password. Please Try Again...!").show();
                }
            }else {
                txtFldAdminUserName.setStyle(";-fx-border-color: red;");
                new Alert(Alert.AlertType.ERROR, "Wrong Username. Please Try Again...!").show();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void adminLoginBackIconOnAction(MouseEvent event) throws IOException {
        adminLoginPane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/WelcomeForm.fxml"));
        adminLoginPane.getChildren().add(load);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
