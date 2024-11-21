package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.CarDTO;
import com.example.rdfcarrentals.dto.DriverAssignmentDTO;
import com.example.rdfcarrentals.dto.DriverDTO;
import com.example.rdfcarrentals.model.CarModel;
import com.example.rdfcarrentals.model.DriverModel;
import com.example.rdfcarrentals.tm.DriverTM;
import com.example.rdfcarrentals.util.CrudUtil;
import com.example.rdfcarrentals.util.OptionButtonsUtil;
import com.example.rdfcarrentals.util.ValidationUtil;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

public class DriverFormController implements Initializable {

    @FXML
    private Button btnSave;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnViewDriverAssignment;

    @FXML
    private ComboBox<String> cmbAvailabilityStatus;

    @FXML
    private ComboBox<String> cmbLicensePlateNo;

    @FXML
    private TableColumn<DriverTM, String> colAvailabilityStatus;

    @FXML
    private TableColumn<DriverTM, String> colContactNumber;

    @FXML
    private TableColumn<DriverTM, String> colEmail;

    @FXML
    private TableColumn<DriverTM, String> colName;

    @FXML
    private TableColumn<DriverTM, String> colNic;

    @FXML
    private TableColumn<DriverTM, Double> colPricePerKm;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private AnchorPane driverContent;

    @FXML
    private AnchorPane driverFormPane;

    @FXML
    private TableView<DriverTM> tblDrivers;

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
    private TextField txtPricePerKm;

    @FXML
    private FontAwesomeIcon searchIcon;

    private final DriverModel driverModel = new DriverModel();
    private final ObservableList<DriverTM> driverTMS = FXCollections.observableArrayList();
    private final CarModel carModel = new CarModel();

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        if (validateTextFields()) {
            DriverDTO driverDTO = getTextFieldsValues();

            boolean isSaved = driverModel.saveDriver(driverDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Driver Saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Save Driver...!").show();
            }
        }
    }

    DriverDTO getTextFieldsValues() {
        String nic = txtFldNIC.getText();
        String name = txtFldName.getText();
        String email = txtFldEmail.getText();
        String availabilityStatus = cmbAvailabilityStatus.getValue();
        String contactNumber = txtFldContactNumber.getText();
        double pricePerKm = Double.parseDouble(txtPricePerKm.getText());
        String licensePlateNo = cmbLicensePlateNo.getValue();
        Date date = Date.valueOf(LocalDate.now());
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        DriverAssignmentDTO driverAssignmentDTO = new DriverAssignmentDTO(
                licensePlateNo, nic, pricePerKm, date, time
        );
        ArrayList<DriverAssignmentDTO> driverAssignmentDTOS = new ArrayList<>(Collections.singletonList(driverAssignmentDTO));

        return new DriverDTO(nic, name, email, availabilityStatus, contactNumber, pricePerKm, driverAssignmentDTOS);
    }

    boolean validateTextFields() {
        boolean isValidNic = ValidationUtil.isValidNic(txtFldNIC);
        boolean isValidName = ValidationUtil.isValidName(txtFldName);
        boolean isValidEmail = ValidationUtil.isValidEmail(txtFldEmail);
        boolean isValidContactNumber = ValidationUtil.isValidContactNumber(txtFldContactNumber);
        boolean isValidPrice = ValidationUtil.isValidPrice(txtPricePerKm);

        return isValidNic && isValidName && isValidEmail && isValidContactNumber && isValidPrice;
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void cmbLicensePlateNoOnAction(ActionEvent event) throws SQLException {
        String selectedLicensePlateNo = cmbLicensePlateNo.getSelectionModel().getSelectedItem();
        CarDTO carDTO = carModel.findByLicensePlateNo(selectedLicensePlateNo);
    }

    private void loadLicensePlateNos() throws SQLException {
        ArrayList<String> licensePlateNos = carModel.getAllLicensePlateNos();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(licensePlateNos);
        cmbLicensePlateNo.setItems(observableList);
    }

    @FXML
    void btnViewDriverAssignmentOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DriverAssignmentForm.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load page...!").show();
        }
    }

    @FXML
    void tblDriversOnClicked(MouseEvent event) {
        DriverTM selectedItem = tblDrivers.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtFldNIC.setText(selectedItem.getNic());
            txtFldName.setText(selectedItem.getName());
            txtFldEmail.setText(selectedItem.getEmail());
            cmbAvailabilityStatus.setValue(selectedItem.getAvailabilityStatus());
            txtFldContactNumber.setText(selectedItem.getContactNumber());
            txtPricePerKm.setText(String.valueOf(selectedItem.getPricePerKm()));
        }
    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) throws SQLException {
        String searchText = txtFldSearchHere.getText().toLowerCase();
        ArrayList<DriverDTO> driverDTOS = driverModel.getAllDrivers();
        ObservableList<DriverTM> filteredDrivers = FXCollections.observableArrayList();

        for (DriverDTO driverDTO : driverDTOS) {
            if (driverDTO.getNic().toLowerCase().contains(searchText) ||
                    driverDTO.getName().toLowerCase().contains(searchText)) {
                    filteredDrivers.add(new DriverTM(
                            driverDTO.getNic(),
                            driverDTO.getName(),
                            driverDTO.getEmail(),
                            driverDTO.getAvailabilityStatus(),
                            driverDTO.getContactNumber(),
                            driverDTO.getPricePerKm()
                    ));
            }
        }
        tblDrivers.setItems(filteredDrivers);

        if (searchText.isEmpty()) {
            searchIcon.setVisible(true);
        } else {
            searchIcon.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbAvailabilityStatus.getItems().addAll("Yes", "No");

        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAvailabilityStatus.setCellValueFactory(new PropertyValueFactory<>("availabilityStatus"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colPricePerKm.setCellValueFactory(new PropertyValueFactory<>("pricePerKm"));

        tblDrivers.getColumns().get(6).setCellValueFactory(param -> {
            ImageView btnRemove = OptionButtonsUtil.setRemoveButton();
            ImageView btnUpdate = OptionButtonsUtil.setUpdateButton();

            btnRemove.setOnMouseClicked(event -> {
                DriverTM selectedDriver = param.getValue();
                tblDrivers.getSelectionModel().select(selectedDriver);
                setBtnRemove(event);
            });

            btnUpdate.setOnMouseClicked(event -> {
                DriverTM selectedDriver = param.getValue();
                tblDrivers.getSelectionModel().select(selectedDriver);
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

    private void refreshPage() throws SQLException {
        refreshTable();
        loadLicensePlateNos();

        txtFldNIC.setText("");
        txtFldName.setText("");
        txtFldEmail.setText("");
        cmbAvailabilityStatus.setValue("");
        txtFldContactNumber.setText("");
        txtPricePerKm.setText("");
        cmbLicensePlateNo.setValue("");
    }

    private void refreshTable() throws SQLException {
        ArrayList<DriverDTO> driverDTOS = driverModel.getAllDrivers();
        driverTMS.clear();

        for (DriverDTO driverDTO : driverDTOS) {
            DriverTM driverTM = new DriverTM(
                    driverDTO.getNic(),
                    driverDTO.getName(),
                    driverDTO.getEmail(),
                    driverDTO.getAvailabilityStatus(),
                    driverDTO.getContactNumber(),
                    driverDTO.getPricePerKm()
            );
            driverTMS.add(driverTM);
        }
        tblDrivers.setItems(driverTMS);
    }

    private void setBtnRemove(MouseEvent event) {
        DriverTM selectedDriver = tblDrivers.getSelectionModel().getSelectedItem();

        if (selectedDriver != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this Driver?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {
                try {
                    CrudUtil.execute("DELETE FROM driver WHERE nic = ?", selectedDriver.getNic());
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Driver Successfully Deleted...!");
                    successAlert.showAndWait();
                    refreshTable();
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No driver selected to remove...!").show();
        }
    }

    private void setBtnUpdate(MouseEvent event) throws SQLException {
        DriverTM selectedDriver = tblDrivers.getSelectionModel().getSelectedItem();

        if (selectedDriver != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update this Driver?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {

                if (validateTextFields()) {
                    DriverDTO driverDTO = getTextFieldsValues();

                    boolean isUpdate = driverModel.updateDriver(driverDTO);

                    if (isUpdate) {
                        new Alert(Alert.AlertType.INFORMATION, "Driver Updated...!").show();
                        refreshPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Fail to Update Driver...!").show();
                    }
                }
            } else {
                refreshPage();
            }
        }
    }

}
