package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.CashierDTO;
import com.example.rdfcarrentals.dto.CustomerDTO;
import com.example.rdfcarrentals.model.CashierModel;
import com.example.rdfcarrentals.tm.CashierTM;
import com.example.rdfcarrentals.tm.CustomerTM;
import com.example.rdfcarrentals.util.ValidationUtil;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CashierSignUpFormController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private AnchorPane cashierSignUpPane;

    @FXML
    private TableColumn<CashierTM, String> colContactNumber;

    @FXML
    private TableColumn<CashierTM, String> colEmail;

    @FXML
    private TableColumn<CashierTM, String> colName;

    @FXML
    private TableColumn<CashierTM, String> colPassword;

    @FXML
    private TableColumn<CashierTM, String> colUserName;

    @FXML
    private TableView<CashierTM> tblCashiers;


    @FXML
    private TextField txtFldContactNumber;

    @FXML
    private TextField txtFldEmail;

    @FXML
    private TextField txtFldName;

    @FXML
    private TextField txtFldPassword;

    @FXML
    private TextField txtFldSearchHere;

    @FXML
    private TextField txtFldUserName;

    @FXML
    private FontAwesomeIcon searchIcon;

    private final CashierModel cashierModel = new CashierModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String cashierUserName = txtFldUserName.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete this Cashier?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = cashierModel.deleteCashier(cashierUserName);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Cashier Deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Delete Cashier...!").show();
            }
        }
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        if (validateTextFields()) {
            CashierDTO cashierDTO = getTextFieldsValues();

            boolean isSaved = cashierModel.saveCashier(cashierDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Cashier Saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Save Cashier...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        if (validateTextFields()) {
            CashierDTO cashierDTO = getTextFieldsValues();

            boolean isUpdate = cashierModel.updateCashier(cashierDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Cashier Updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Update Cashier...!").show();
            }
        }
    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) throws SQLException {
        String searchText = txtFldSearchHere.getText().toLowerCase();
        ArrayList<CashierDTO> cashierDTOS = cashierModel.getAllCashiers();
        ObservableList<CashierTM> filteredCashiers = FXCollections.observableArrayList();

        for (CashierDTO cashierDTO : cashierDTOS) {
            if (cashierDTO.getUserName().toLowerCase().contains(searchText) ||
                    cashierDTO.getName().toLowerCase().contains(searchText)) {
                    filteredCashiers.add(new CashierTM(
                            cashierDTO.getUserName(),
                            cashierDTO.getPassword(),
                            cashierDTO.getName(),
                            cashierDTO.getEmail(),
                            cashierDTO.getContactNumber()
                    ));
            }
        }
        tblCashiers.setItems(filteredCashiers);

        if (searchText.isEmpty()) {
            searchIcon.setVisible(true);
        } else {
            searchIcon.setVisible(false);
        }
    }

    CashierDTO getTextFieldsValues() {
        String userName = txtFldUserName.getText();
        String password = txtFldPassword.getText();
        String name = txtFldName.getText();
        String email = txtFldEmail.getText();
        String contactNumber = txtFldContactNumber.getText();

        return new CashierDTO(userName, password, name, email, contactNumber);
    }

    boolean validateTextFields(){
        boolean isValidName = ValidationUtil.isValidName(txtFldName);
        boolean isValidEmail = ValidationUtil.isValidEmail(txtFldEmail);
        boolean isValidContactNumber = ValidationUtil.isValidContactNumber(txtFldContactNumber);

        return isValidName && isValidEmail && isValidContactNumber;
    }

    @FXML
    void tblCashiersOnClicked(MouseEvent event) {
        CashierTM selectedItem = tblCashiers.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtFldUserName.setText(selectedItem.getUserName());
            txtFldPassword.setText(selectedItem.getPassword());
            txtFldName.setText(selectedItem.getName());
            txtFldEmail.setText(selectedItem.getEmail());
            txtFldContactNumber.setText(selectedItem.getContactNumber());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        txtFldUserName.setText("");
        txtFldPassword.setText("");
        txtFldName.setText("");
        txtFldEmail.setText("");
        txtFldContactNumber.setText("");

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<CashierDTO> cashierDTOS = cashierModel.getAllCashiers();
        ObservableList<CashierTM> cashierTMS = FXCollections.observableArrayList();

        for (CashierDTO cashierDTO : cashierDTOS) {
            CashierTM cashierTM = new CashierTM(
                    cashierDTO.getUserName(),
                    cashierDTO.getPassword(),
                    cashierDTO.getName(),
                    cashierDTO.getEmail(),
                    cashierDTO.getContactNumber()
            );
            cashierTMS.add(cashierTM);
        }
        tblCashiers.setItems(cashierTMS);
    }
}