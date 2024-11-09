package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.CreditDTO;
import com.example.rdfcarrentals.dto.CustomerDTO;
import com.example.rdfcarrentals.dto.FuelTypeDTO;
import com.example.rdfcarrentals.model.CreditModel;
import com.example.rdfcarrentals.tm.CreditTM;
import com.example.rdfcarrentals.tm.CustomerTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private ComboBox<?> cmbCustomerNIC;

    @FXML
    private ComboBox<?> cmbReservationID;

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
    private Label txtFldAvailabilityStatus;

    @FXML
    private Label txtFldAvailabilityStatus1;

    @FXML
    private TextField txtFldSearchHere;

    @FXML
    private TextField txtFldTotalAmount;

    CreditModel creditModel = new CreditModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCustomerNICOnAction(ActionEvent event) {

    }

    @FXML
    void cmbReservationIDOnAction(ActionEvent event) {

    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCreditID.setCellValueFactory(new PropertyValueFactory<>("creditID"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colAmountPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        colAmountToPay.setCellValueFactory(new PropertyValueFactory<>("amountToPay"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        lblCreditID.setText("");
        //cmbCustomerNIC.setValue("");
       // cmbReservationID.setValue("");
        txtFldTotalAmount.setText("");
        txtFldAmountPaid.setText("");
        txtFldAmountToPay.setText("");
        //txtDueDate.setValue("");

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