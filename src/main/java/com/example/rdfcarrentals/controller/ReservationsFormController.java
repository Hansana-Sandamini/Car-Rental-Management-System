package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.CashierDTO;
import com.example.rdfcarrentals.dto.CustomerDTO;
import com.example.rdfcarrentals.dto.ReservationDTO;
import com.example.rdfcarrentals.model.CashierModel;
import com.example.rdfcarrentals.model.CustomerModel;
import com.example.rdfcarrentals.model.ReservationModel;
import com.example.rdfcarrentals.tm.ReservationTM;
import com.example.rdfcarrentals.util.CrudUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReservationsFormController implements Initializable {

    @FXML
    private Button btnAddReservation;

    @FXML
    private Button btnViewReservationDetails;

    @FXML
    private ComboBox<String> cmbCashierUserName;

    @FXML
    private ComboBox<String> cmbCustomerNIC;

    @FXML
    private ComboBox<String> cmbIsDriverWant;

    @FXML
    private TableColumn<ReservationTM, String> colCashierUserName;

    @FXML
    private TableColumn<ReservationTM, String> colCreditID;

    @FXML
    private TableColumn<ReservationTM, String> colCustomerNIC;

    @FXML
    private TableColumn<ReservationTM, String> colIsDriverWant;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private TableColumn<ReservationTM, Date> colPickUpDate;

    @FXML
    private TableColumn<ReservationTM, String> colPickUpTime;

    @FXML
    private TableColumn<ReservationTM, String> colReservationID;

    @FXML
    private TableColumn<ReservationTM, Date> colReturnDate;

    @FXML
    private TableColumn<ReservationTM, String> colReturnTime;

    @FXML
    private Label lblReservationID;

    @FXML
    private AnchorPane reservationsContent;

    @FXML
    private TableView<ReservationTM> tblReservations;

    @FXML
    private TextField txtFldSearchHere;

    @FXML
    private DatePicker txtPickUpDate;

    @FXML
    private DatePicker txtReturnDate;

    @FXML
    private TextField txtPickUpTime;

    @FXML
    private TextField txtReturnTime;

    @FXML
    private AnchorPane reservationFormPane;

    ReservationModel reservationModel = new ReservationModel();
    private final ObservableList<ReservationTM> reservationTMS = FXCollections.observableArrayList();
    private final CustomerModel customerModel = new CustomerModel();
    private final CashierModel cashierModel = new CashierModel();

    @FXML
    void btnAddReservationOnAction(ActionEvent event) throws SQLException {
        ReservationDTO reservationDTO = getTextFieldsValues();

        boolean isAdded = reservationModel.addReservation(reservationDTO);

        if (isAdded) {
            new Alert(Alert.AlertType.INFORMATION, "Reservation Added...!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to Add Reservation...!").show();
        }
    }

    ReservationDTO getTextFieldsValues() {
        String reservationID = lblReservationID.getText();
        String customerNIC = cmbCustomerNIC.getValue();
        String cashierUserName = cmbCashierUserName.getValue();
        String creditId = null;
        Date pickUpDate = Date.valueOf(txtPickUpDate.getValue());
        String pickUpTime = txtPickUpTime.getText();
        Date returnDate = Date.valueOf(txtReturnDate.getValue());
        String returnTime = txtReturnTime.getText();
        String isDriverWant = cmbIsDriverWant.getValue();

        return new ReservationDTO(reservationID, customerNIC,cashierUserName, creditId, pickUpDate, pickUpTime, returnDate, returnTime, isDriverWant);
    }

    @FXML
    void btnViewReservationDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCashierUserNameOnAction(ActionEvent event) throws SQLException {
        String selectedCashierUsername = cmbCashierUserName.getSelectionModel().getSelectedItem();
        CashierDTO cashierDTO = cashierModel.findByUsername(selectedCashierUsername);
    }

    private void loadCashierUsernames() throws SQLException {
        ArrayList<String> cashierUsernames = cashierModel.getAllCashierUsernames();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(cashierUsernames);
        cmbCashierUserName.setItems(observableList);
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
    void txtFldSearchHereOnAction(KeyEvent event) {

    }

    @FXML
    void tblReservationsOnClicked(MouseEvent event) {
        ReservationTM selectedItem = tblReservations.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblReservationID.setText(selectedItem.getReservationId());
            cmbCustomerNIC.setValue(selectedItem.getCustomerNic());
            cmbCashierUserName.setValue(selectedItem.getCashierUsername());
            txtPickUpDate.setValue(LocalDate.parse(LocalDate.now().toString()));
            txtPickUpTime.setText(selectedItem.getPickUpTime().toString());
            txtReturnDate.setValue(LocalDate.parse(LocalDate.now().toString()));
            txtReturnTime.setText(selectedItem.getReturnTime().toString());
            cmbIsDriverWant.setValue(selectedItem.getIsDriverWant());

            btnAddReservation.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbIsDriverWant.getItems().addAll("Yes", "No");

        colReservationID.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colCustomerNIC.setCellValueFactory(new PropertyValueFactory<>("customerNic"));
        colCashierUserName.setCellValueFactory(new PropertyValueFactory<>("cashierUsername"));
        colCreditID.setCellValueFactory(new PropertyValueFactory<>("creditId"));
        colPickUpDate.setCellValueFactory(new PropertyValueFactory<>("pickUpDate"));
        colPickUpTime.setCellValueFactory(new PropertyValueFactory<>("pickUpTime"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colReturnTime.setCellValueFactory(new PropertyValueFactory<>("returnTime"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colIsDriverWant.setCellValueFactory(new PropertyValueFactory<>("isDriverWant"));

        tblReservations.getColumns().get(9).setCellValueFactory(param -> {
            Button btnRemove = new Button("Remove");

            btnRemove.setOnMouseClicked(event -> {
                ReservationTM selectedReservation = param.getValue();
                tblReservations.getSelectionModel().select(selectedReservation);
                setBtnRemove(event);
            });
            return new ReadOnlyObjectWrapper(new HBox(100, btnRemove));
        });

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setBtnRemove(MouseEvent event) {
        ReservationTM selectedReservation = tblReservations.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this Reservation?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {
                try {
                    CrudUtil.execute("DELETE FROM reservation WHERE reservation_id = ?", selectedReservation.getReservationId());
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Reservation Successfully Deleted...!");
                    successAlert.showAndWait();
                    refreshTable();
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No reservation selected to remove...!").show();
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        loadCustomerNICs();
        loadCashierUsernames();

        lblReservationID.setText(reservationModel.getNextReservationId());
        cmbCustomerNIC.setValue("");
        cmbCashierUserName.setValue("");
        txtPickUpDate.setValue(null);
        txtPickUpTime.setText("");
        txtReturnDate.setValue(null);
        txtReturnTime.setText("");
        cmbIsDriverWant.setValue(null);
    }

    private void refreshTable() throws SQLException {
        ArrayList<ReservationDTO> reservationDTOS = reservationModel.getAllReservations();
        reservationTMS.clear();

        for (ReservationDTO reservationDTO : reservationDTOS) {
            ReservationTM reservationTM = new ReservationTM(
                    reservationDTO.getReservationId(),
                    reservationDTO.getCustomerNic(),
                    reservationDTO.getCashierUsername(),
                    reservationDTO.getCreditId(),
                    reservationDTO.getPickUpDate(),
                    reservationDTO.getPickUpTime(),
                    reservationDTO.getReturnDate(),
                    reservationDTO.getReturnTime(),
                    reservationDTO.getIsDriverWant()
            );
            reservationTMS.add(reservationTM);
        }
        tblReservations.setItems(reservationTMS);
    }

}
