package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.CreditDTO;
import com.example.rdfcarrentals.dto.CustomerDTO;
import com.example.rdfcarrentals.dto.ReservationDTO;
import com.example.rdfcarrentals.model.CreditModel;
import com.example.rdfcarrentals.model.CustomerModel;
import com.example.rdfcarrentals.model.ReservationModel;
import com.example.rdfcarrentals.tm.CreditTM;
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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreditsFormController implements Initializable {

    @FXML
    private Label lblHeadingUserName;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<String> cmbCustomerNIC;

    @FXML
    private ComboBox<String> cmbReservationID;

    @FXML
    private TableColumn<CreditTM, Double> colAmountPaid;

    @FXML
    private TableColumn<CreditTM, String> colReservationId;

    @FXML
    private TableColumn<CreditTM, Double> colAmountToPay;

    @FXML
    private TableColumn<CreditTM, String> colCreditID;

    @FXML
    private TableColumn<CreditTM, Date> colDueDate;

    @FXML
    private TableColumn<CreditTM, Double> colTotalAmount;

    @FXML
    private TableColumn<?, ?> colOption;

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

    @FXML
    private FontAwesomeIcon searchIcon;

    private final CreditModel creditModel = new CreditModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ReservationModel reservationModel = new ReservationModel();

    @FXML
    void btnRefreshOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        if (validateTextFields()) {
            CreditDTO creditDTO = getTextFieldsValues();

            boolean isSaved = creditModel.saveCredit(creditDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Credit Saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Save Credit...!").show();
            }
        }
    }

    CreditDTO getTextFieldsValues() {
        String creditID = lblCreditID.getText();
        String reservationId = cmbReservationID.getValue();
        double totalAmount = Double.parseDouble(txtFldTotalAmount.getText());
        double amountPaid = Double.parseDouble(txtFldAmountPaid.getText());
        double amountToPay = Double.parseDouble(txtFldAmountToPay.getText());
        Date dueDate = Date.valueOf(txtDueDate.getValue());

        return new CreditDTO(creditID, reservationId, totalAmount, amountPaid, amountToPay, dueDate);
    }

    boolean validateTextFields() {
        boolean isValidTotalAmount = ValidationUtil.isValidPrice(txtFldTotalAmount);
        boolean isValidAmountPaid = ValidationUtil.isValidPrice(txtFldAmountPaid);
        boolean isValidAmountToPay = ValidationUtil.isValidPrice(txtFldAmountToPay);

        return isValidTotalAmount && isValidAmountPaid && isValidAmountToPay;
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
    void txtFldSearchHereOnAction(KeyEvent event) throws SQLException {
        String searchText = txtFldSearchHere.getText().toLowerCase();
        ArrayList<CreditDTO> creditDTOS = creditModel.getAllCredits();
        ObservableList<CreditTM> filteredCredits = FXCollections.observableArrayList();

        for (CreditDTO creditDTO : creditDTOS) {
            if (creditDTO.getCreditId().toLowerCase().contains(searchText) ||
                creditDTO.getReservationId().toLowerCase().contains(searchText)) {
                filteredCredits.add(new CreditTM(
                        creditDTO.getCreditId(),
                        creditDTO.getReservationId(),
                        creditDTO.getTotalAmount(),
                        creditDTO.getAmountPaid(),
                        creditDTO.getAmountToPay(),
                        creditDTO.getDueDate()
                ));
            }
        }
        tblCredits.setItems(filteredCredits);

        if (searchText.isEmpty()) {
            searchIcon.setVisible(true);
        } else {
            searchIcon.setVisible(false);
        }
    }

    @FXML
    void tblCreditsOnClicked(MouseEvent event) {
        CreditTM selectedItem = tblCredits.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblCreditID.setText(selectedItem.getCreditId());
            cmbReservationID.setValue(selectedItem.getReservationId());
            txtFldTotalAmount.setText(Double.toString(selectedItem.getTotalAmount()));
            txtFldAmountPaid.setText(Double.toString(selectedItem.getAmountPaid()));
            txtFldAmountToPay.setText(Double.toString(selectedItem.getAmountToPay()));
            txtDueDate.setValue(LocalDate.parse(LocalDate.now().toString()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCreditID.setCellValueFactory(new PropertyValueFactory<>("creditId"));
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colAmountPaid.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        colAmountToPay.setCellValueFactory(new PropertyValueFactory<>("amountToPay"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        tblCredits.getColumns().get(6).setCellValueFactory(param -> {
            ImageView btnRemove = OptionButtonsUtil.setRemoveButton();
            ImageView btnUpdate = OptionButtonsUtil.setUpdateButton();

            btnRemove.setOnMouseClicked(event -> {
                CreditTM selectedCredit = param.getValue();
                tblCredits.getSelectionModel().select(selectedCredit);
                setBtnRemove(event);
            });

            btnUpdate.setOnMouseClicked(event -> {
                CreditTM selectedCredit = param.getValue();
                tblCredits.getSelectionModel().select(selectedCredit);
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
        CreditTM selectedCredit = tblCredits.getSelectionModel().getSelectedItem();

        if (selectedCredit != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this Credit?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {
                try {
                    CrudUtil.execute("DELETE FROM credit WHERE credit_id = ?", selectedCredit.getCreditId());
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Credit Successfully Deleted...!");
                    successAlert.showAndWait();
                    refreshTable();
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No Credit selected to Remove...!").show();
        }
    }

    private void setBtnUpdate(MouseEvent event) throws SQLException {
        CreditTM selectedCredit = tblCredits.getSelectionModel().getSelectedItem();

        if (selectedCredit != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update this Credit?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {

                if (validateTextFields()) {
                    CreditDTO creditDTO = getTextFieldsValues();

                    boolean isUpdate = creditModel.updateCredit(creditDTO);

                    if (isUpdate) {
                        new Alert(Alert.AlertType.INFORMATION, "Credit Updated...!").show();
                        refreshPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Fail to Update Credit...!").show();
                    }
                }
            } else {
                refreshPage();
            }
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
    }

    private void refreshTable() throws SQLException {
        ArrayList<CreditDTO> creditDTOS = creditModel.getAllCredits();
        ObservableList<CreditTM> creditTMS = FXCollections.observableArrayList();

        for (CreditDTO creditDTO : creditDTOS) {
            CreditTM creditTM = new CreditTM(
                    creditDTO.getCreditId(),
                    creditDTO.getReservationId(),
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