package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.CustomerDTO;
import com.example.rdfcarrentals.model.CustomerModel;
import com.example.rdfcarrentals.tm.CustomerTM;
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

public class CustomerFormController implements Initializable {

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private TableColumn<CustomerTM, String> colAddress;

    @FXML
    private TableColumn<CustomerTM, String> colContactNumber;

    @FXML
    private TableColumn<CustomerTM, String> colEmail;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colNic;

    @FXML
    private TableColumn<?, ?> colOptions;

    @FXML
    private AnchorPane customerContent;

    @FXML
    private TableView<CustomerTM> tblCustomers;

    @FXML
    private TextField txtFldAddress;

    @FXML
    private TextField txtFldContactNumber;

    @FXML
    private TextField txtFldEmail;

    @FXML
    private TextField txtFldNIC;

    @FXML
    private TextField txtFldName;

    @FXML
    private TextField txtFldSearchHere;

    @FXML
    private FontAwesomeIcon searchIcon;

    private final CustomerModel customerModel = new CustomerModel();

    @FXML
    void btnRefreshOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        if (validateTextFields()) {
            CustomerDTO customerDTO = getTextFieldsValues();

            boolean isSaved = customerModel.saveCustomer(customerDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Save Customer...!").show();
            }
        }
    }

    CustomerDTO getTextFieldsValues() {
        String nic = txtFldNIC.getText();
        String name = txtFldName.getText();
        String address = txtFldAddress.getText();
        String email = txtFldEmail.getText();
        String contactNumber = txtFldContactNumber.getText();

        return new CustomerDTO(nic, name, address, email, contactNumber);
    }

    boolean validateTextFields() {
        boolean isValidNic = ValidationUtil.isValidNic(txtFldNIC);
        boolean isValidName = ValidationUtil.isValidName(txtFldName);
        boolean isValidAddress = ValidationUtil.isValidAddress(txtFldAddress);
        boolean isValidEmail = ValidationUtil.isValidEmail(txtFldEmail);
        boolean isValidContactNumber = ValidationUtil.isValidContactNumber(txtFldContactNumber);

        return isValidNic && isValidName && isValidAddress && isValidEmail && isValidContactNumber;
    }

    @FXML
    void tblCustomersOnClicked(MouseEvent event) {
        CustomerTM selectedItem = tblCustomers.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtFldNIC.setText(selectedItem.getNic());
            txtFldName.setText(selectedItem.getName());
            txtFldAddress.setText(selectedItem.getAddress());
            txtFldEmail.setText(selectedItem.getEmail());
            txtFldContactNumber.setText(selectedItem.getContactNumber());
        }
    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) throws SQLException {
        String searchText = txtFldSearchHere.getText().toLowerCase();
        ArrayList<CustomerDTO> customerDTOS = customerModel.getAllCustomers();
        ObservableList<CustomerTM> filteredCustomers = FXCollections.observableArrayList();

        for (CustomerDTO customerDTO : customerDTOS) {
            if (customerDTO.getNic().toLowerCase().contains(searchText) ||
                    customerDTO.getName().toLowerCase().contains(searchText)) {
                    filteredCustomers.add(new CustomerTM(
                            customerDTO.getNic(),
                            customerDTO.getName(),
                            customerDTO.getAddress(),
                            customerDTO.getEmail(),
                            customerDTO.getContactNumber()
                    ));
            }
        }
        tblCustomers.setItems(filteredCustomers);

        if (searchText.isEmpty()) {
            searchIcon.setVisible(true);
        } else {
            searchIcon.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        tblCustomers.getColumns().get(5).setCellValueFactory(param -> {
            ImageView btnRemove = OptionButtonsUtil.setRemoveButton();
            ImageView btnUpdate = OptionButtonsUtil.setUpdateButton();

            btnRemove.setOnMouseClicked(event -> {
                CustomerTM selectedCustomer = param.getValue();
                tblCustomers.getSelectionModel().select(selectedCustomer);
                setBtnRemove(event);
            });

            btnUpdate.setOnMouseClicked(event -> {
                CustomerTM selectedCustomer = param.getValue();
                tblCustomers.getSelectionModel().select(selectedCustomer);
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
        CustomerTM selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this Customer?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {
                try {
                    CrudUtil.execute("DELETE FROM customer WHERE nic = ?", selectedCustomer.getNic());
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Customer Successfully Deleted...!");
                    successAlert.showAndWait();
                    refreshTable();
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No Customer selected to Remove...!").show();
        }
    }

    private void setBtnUpdate(MouseEvent event) throws SQLException {
        CustomerTM selectedCustomer = tblCustomers.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update this Customer?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {

                if (validateTextFields()) {
                    CustomerDTO customerDTO = getTextFieldsValues();

                    boolean isUpdate = customerModel.updateCustomer(customerDTO);

                    if (isUpdate) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer Updated...!").show();
                        refreshPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Fail to Update Customer...!").show();
                    }
                }
            } else {
                refreshPage();
            }
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        txtFldNIC.setText("");
        txtFldName.setText("");
        txtFldAddress.setText("");
        txtFldEmail.setText("");
        txtFldContactNumber.setText("");
    }

    private void refreshTable() throws SQLException {
        ArrayList<CustomerDTO> customerDTOS = customerModel.getAllCustomers();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDTO customerDTO : customerDTOS) {
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getNic(),
                    customerDTO.getName(),
                    customerDTO.getAddress(),
                    customerDTO.getEmail(),
                    customerDTO.getContactNumber()
            );
            customerTMS.add(customerTM);
        }
        tblCustomers.setItems(customerTMS);
    }
}
