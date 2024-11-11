package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.CreditDTO;
import com.example.rdfcarrentals.dto.CustomerDTO;
import com.example.rdfcarrentals.dto.ReservationDTO;
import com.example.rdfcarrentals.model.CreditModel;
import com.example.rdfcarrentals.model.CustomerModel;
import com.example.rdfcarrentals.model.ReservationModel;
import com.example.rdfcarrentals.tm.CreditTM;
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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreditsFormController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String > cmbCustomerNIC;

    @FXML
    private ComboBox<String> cmbReservationID;

    @FXML
    private TableColumn<CreditTM, Double> colAmountPaid;

    @FXML
    private TableColumn<CreditTM, Double> colAmountToPay;

    @FXML
    private TableColumn<CreditTM, String > colCreditID;

    @FXML
    private TableColumn<CreditTM, Date> colDueDate;

    @FXML
    private TableColumn<CreditTM, Double> colTotalAmount;

    @FXML
    private AnchorPane creditsContent;

    @FXML
    private Label lblCreditID;

    @FXML
    private TableView<CreditTM> tblCredits;

    @FXML
    private DatePicker txtDueDate;

    @FXML
    private TextField txtFldAmountPaid;

    @FXML
    private TextField txtFldAmountToPay;

    @FXML
    private TextField txtFldSearchHere;

    @FXML
    private TextField txtFldTotalAmount;

    private final CreditModel creditModel = new CreditModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ReservationModel reservationModel = new ReservationModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String creditID = lblCreditID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete this Credit?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = creditModel.deleteCredit(creditID);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Credit Deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Delete Credit...!").show();
            }
        }
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        CreditDTO creditDTO = getTextFieldsValues();

        boolean isSaved = creditModel.saveCredit(creditDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Credit Saved...!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to Save Credit...!").show();
        }
    }

    CreditDTO getTextFieldsValues() {
        String creditID = lblCreditID.getText();
        double totalAmount = Double.parseDouble(txtFldTotalAmount.getText());
        double amountPaid = Double.parseDouble(txtFldAmountPaid.getText());
        double amountToPay = Double.parseDouble(txtFldAmountToPay.getText());
        Date dueDate = Date.valueOf(txtDueDate.getValue());

        return new CreditDTO(creditID, totalAmount, amountPaid, amountToPay, dueDate);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        CreditDTO creditDTO = getTextFieldsValues();

        boolean isUpdate = creditModel.updateCredit(creditDTO);

        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Credit Updated...!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to Update Credit...!").show();
        }
    }

    @FXML
    void cmbCustomerNICOnAction(ActionEvent event) throws SQLException {
        String selectedCustomerNIC = cmbCustomerNIC.getSelectionModel().getSelectedItem();
        CustomerDTO customerDTO = customerModel.findByNIC(selectedCustomerNIC);
    }

    private void loadCustomerNICs() throws SQLException {
        ArrayList<String> customerNICs = customerModel.getAllCustomerNICs();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerNICs);
        cmbCustomerNIC.setItems(observableList);
    }

    @FXML
    void cmbReservationIDOnAction(ActionEvent event) throws SQLException {
        String selectedReservationID = cmbReservationID.getSelectionModel().getSelectedItem();
        ReservationDTO reservationDTO = reservationModel.findByReservationID(selectedReservationID);
    }

    private void loadReservationIDs() throws SQLException {
        ArrayList<String> reservationIDs = reservationModel.getAllReservationIDS();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(reservationIDs);
        cmbReservationID.setItems(observableList);
    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) {

    }

    @FXML
    void tblCreditsOnClicked(MouseEvent event) {
        CreditTM selectedItem = tblCredits.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblCreditID.setText(selectedItem.getCreditId());
            txtFldTotalAmount.setText(Double.toString(selectedItem.getTotalAmount()));
            txtFldAmountPaid.setText(Double.toString(selectedItem.getAmountPaid()));
            txtFldAmountToPay.setText(Double.toString(selectedItem.getAmountToPay()));
            txtDueDate.setValue(LocalDate.parse(LocalDate.now().toString()));

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCreditID.setCellValueFactory(new PropertyValueFactory<>("creditId"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colAmountPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        colAmountToPay.setCellValueFactory(new PropertyValueFactory<>("amountToPay"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        loadCustomerNICs();
        loadReservationIDs();

        lblCreditID.setText(creditModel.getNextCreditId());
        cmbCustomerNIC.setValue("");
        cmbReservationID.setValue("");
        txtFldTotalAmount.setText("");
        txtFldAmountPaid.setText("");
        txtFldAmountToPay.setText("");
        txtDueDate.setValue(null);

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<CreditDTO> creditDTOS = creditModel.getAllCredits();
        ObservableList<CreditTM> creditTMS = FXCollections.observableArrayList();

        for (CreditDTO creditDTO : creditDTOS) {
            CreditTM creditTM = new CreditTM(
                    creditDTO.getCreditId(),
                    creditDTO.getTotalAmount(),
                    creditDTO.getAmountPaid(),
                    creditDTO.getAmountToPay(),
                    creditDTO.getDueDate()
            );
            creditTMS.add(creditTM);
        }
        tblCredits.setItems(creditTMS);
    }

}