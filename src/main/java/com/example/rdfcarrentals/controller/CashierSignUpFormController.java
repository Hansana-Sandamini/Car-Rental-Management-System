package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.CashierDTO;
import com.example.rdfcarrentals.model.CashierModel;
import com.example.rdfcarrentals.tm.CashierTM;
import com.example.rdfcarrentals.util.CrudUtil;
import com.example.rdfcarrentals.util.OptionButtonsUtil;
import com.example.rdfcarrentals.util.ValidationUtil;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CashierSignUpFormController implements Initializable {

    @FXML
    private Label lblHeadingUserName;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

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
    private TableColumn<?, ?> colOption;

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
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        tblCashiers.getColumns().get(5).setCellValueFactory(param -> {
            ImageView btnRemove = OptionButtonsUtil.setRemoveButton();
            ImageView btnUpdate = OptionButtonsUtil.setUpdateButton();

            btnRemove.setOnMouseClicked(event -> {
                CashierTM selectedCashier = param.getValue();
                tblCashiers.getSelectionModel().select(selectedCashier);
                setBtnRemove(event);
            });

            btnUpdate.setOnMouseClicked(event -> {
                CashierTM selectedCashier = param.getValue();
                tblCashiers.getSelectionModel().select(selectedCashier);
                try {
                    setBtnUpdate(event);
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
                }
            });

            return new ReadOnlyObjectWrapper(new HBox(24, btnUpdate, btnRemove));
        });

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setBtnRemove(MouseEvent event) {
        CashierTM selectedCashier = tblCashiers.getSelectionModel().getSelectedItem();

        if (selectedCashier != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this Cashier?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {
                try {
                    CrudUtil.execute("DELETE FROM cashier WHERE username = ?", selectedCashier.getUserName());
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Cashier Successfully Deleted...!");
                    successAlert.showAndWait();
                    refreshTable();
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No Cashier selected to Remove...!").show();
        }
    }

    private void setBtnUpdate(MouseEvent event) throws SQLException {
        CashierTM selectedCashier = tblCashiers.getSelectionModel().getSelectedItem();

        if (selectedCashier != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update this Cashier?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {

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
            } else {
                refreshPage();
            }
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        txtFldUserName.setText("");
        txtFldPassword.setText("");
        txtFldName.setText("");
        txtFldEmail.setText("");
        txtFldContactNumber.setText("");
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