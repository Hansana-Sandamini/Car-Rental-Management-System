package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.PaymentDTO;
import com.example.rdfcarrentals.dto.ReservationDTO;
import com.example.rdfcarrentals.model.BillModel;
import com.example.rdfcarrentals.model.PaymentModel;
import com.example.rdfcarrentals.model.ReservationModel;
import com.example.rdfcarrentals.tm.PaymentTM;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentsFormController implements Initializable {

    @FXML
    private Label lblHeadingUserName;

    @FXML
    private Button btnAddPayment;

    @FXML
    private Button btnRefresh;

    @FXML
    private ComboBox<String> cmbReservationID;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentTM, Date> colDate;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentID;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentMethod;

    @FXML
    private TableColumn<PaymentTM, String> colReservationID;

    @FXML
    private TableColumn<PaymentTM, String> colTime;

    @FXML
    private Label lblPaymentID;

    @FXML
    private Label lblBillID;

    @FXML
    private AnchorPane paymentsContent;

    @FXML
    private TableView<PaymentTM> tblPayments;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtFldAmount;

    @FXML
    private TextField txtFldPaymentMethod;

    @FXML
    private TextField txtFldSearchHere;

    @FXML
    private TextField txtTime;

    @FXML
    private Button btnViewBill;

    @FXML
    private FontAwesomeIcon searchIcon;

    private final PaymentModel paymentModel = new PaymentModel();
    private final ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();
    private final ReservationModel reservationModel = new ReservationModel();
    private final BillModel billModel = new BillModel();

    @FXML
    void btnAddPaymentOnAction(ActionEvent event) throws SQLException {
        if (validateTextFields()) {
            PaymentDTO paymentDTO = getTextFieldsValues();

            boolean isAdded = paymentModel.addPayment(paymentDTO);

            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Added...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Add Payment...!").show();
            }
        }
    }

    PaymentDTO getTextFieldsValues() {
        String paymentID = lblPaymentID.getText();
        String reservationID = cmbReservationID.getValue();
        String billID = lblBillID.getText();
        String paymentMethod = txtFldPaymentMethod.getText();
        double amount = Double.parseDouble(txtFldAmount.getText());
        Date date = Date.valueOf(txtDate.getValue());
        String time = txtTime.getText();

        return new PaymentDTO(paymentID, reservationID, billID, paymentMethod, amount, date, time);
    }

    boolean validateTextFields() {
        boolean isValidAmount = ValidationUtil.isValidPrice(txtFldAmount);
        boolean isValidTime = ValidationUtil.isValidTime(txtTime);

        return isValidAmount && isValidTime;
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) throws SQLException {
        refreshPage();
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
        ArrayList<PaymentDTO> paymentDTOS = paymentModel.getAllPayments();
        ObservableList<PaymentTM> filteredPayments = FXCollections.observableArrayList();

        for (PaymentDTO paymentDTO : paymentDTOS) {
            if (paymentDTO.getPaymentId().toLowerCase().contains(searchText) ||
                    paymentDTO.getPaymentMethod().toLowerCase().contains(searchText) ||
                    paymentDTO.getReservationId().toLowerCase().contains(searchText)) {
                    filteredPayments.add(new PaymentTM(
                        paymentDTO.getPaymentId(),
                        paymentDTO.getReservationId(),
                        paymentDTO.getPaymentMethod(),
                        paymentDTO.getAmount(),
                        paymentDTO.getDate(),
                        paymentDTO.getTime()
                ));
            }
        }
        tblPayments.setItems(filteredPayments);

        if (searchText.isEmpty()) {
            searchIcon.setVisible(true);
        } else {
            searchIcon.setVisible(false);
        }
    }

    @FXML
    void tblPaymentsOnClicked(MouseEvent event) {
        PaymentTM selectedItem = tblPayments.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblPaymentID.setText(selectedItem.getPaymentId());
            cmbReservationID.setValue(selectedItem.getReservationId());
            txtFldPaymentMethod.setText(selectedItem.getPaymentMethod());
            txtFldAmount.setText(String.valueOf(selectedItem.getAmount()));
            txtDate.setValue(selectedItem.getDate().toLocalDate());
            txtTime.setText(selectedItem.getTime());

            btnAddPayment.setDisable(true);
        }
    }

    @FXML
    void btnViewBillOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colReservationID.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        tblPayments.getColumns().get(6).setCellValueFactory(param -> {
            ImageView btnRemove = OptionButtonsUtil.setRemoveButton();

            btnRemove.setOnMouseClicked(event -> {
                PaymentTM selectedPayment = param.getValue();
                tblPayments.getSelectionModel().select(selectedPayment);
                setBtnRemove(event);
            });
            return new ReadOnlyObjectWrapper(new HBox(24, btnRemove));
        });

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setBtnRemove(MouseEvent event) {
        PaymentTM selectedPayment = tblPayments.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this Payment?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {
                try {
                    CrudUtil.execute("DELETE FROM payment WHERE payment_id = ?", selectedPayment.getPaymentId());
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Payment Successfully Deleted...!");
                    successAlert.showAndWait();
                    refreshTable();
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No payment selected to remove...!").show();
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        loadReservationIDs();

        lblPaymentID.setText(paymentModel.getNextPaymentId());
        cmbReservationID.setValue("");
        lblBillID.setText(billModel.getNextBillId());
        txtFldPaymentMethod.setText("");
        txtFldAmount.setText("");
        txtDate.setValue(null);
        txtTime.setText("");
    }

    private void refreshTable() throws SQLException {
        ArrayList<PaymentDTO> paymentDTOS = paymentModel.getAllPayments();
        paymentTMS.clear();

        for (PaymentDTO paymentDTO : paymentDTOS) {
            PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getPaymentId(),
                    paymentDTO.getReservationId(),
                    paymentDTO.getPaymentMethod(),
                    paymentDTO.getAmount(),
                    paymentDTO.getDate(),
                    paymentDTO.getTime()
            );
            paymentTMS.add(paymentTM);
        }
        tblPayments.setItems(paymentTMS);
    }
}
