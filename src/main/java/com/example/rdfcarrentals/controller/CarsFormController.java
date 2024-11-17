package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.CarDTO;
import com.example.rdfcarrentals.dto.FuelTypeDTO;
import com.example.rdfcarrentals.model.CarModel;
import com.example.rdfcarrentals.model.FuelTypeModel;
import com.example.rdfcarrentals.tm.CarTM;
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

public class CarsFormController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private AnchorPane carsContent;

    @FXML
    private ComboBox<String> cmbAvailabilityStatus;

    @FXML
    private ComboBox<String> cmbTypeID;

    @FXML
    private TableColumn<CarTM, String> colAvailabilityStatus;

    @FXML
    private TableColumn<CarTM, String> colColour;

    @FXML
    private TableColumn<CarTM, Double> colDailyRate;

    @FXML
    private TableColumn<CarTM, String> colLicensePlateNo;

    @FXML
    private TableColumn<CarTM, String> colModel;

    @FXML
    private TableColumn<CarTM, Double> colMonthlyRate;

    @FXML
    private TableColumn<CarTM, String> colTypeID;

    @FXML
    private TableView<CarTM> tblCars;

    @FXML
    private TextField txtFldColour;

    @FXML
    private TextField txtFldMonthlyRate;

    @FXML
    private TextField txtFldDailyRate;

    @FXML
    private TextField txtFldLicensePlateNo;

    @FXML
    private TextField txtFldModel;

    @FXML
    private TextField txtFldSearchHere;

    @FXML
    private TextField txtFldTypeName;

    @FXML
    private FontAwesomeIcon searchIcon;

    private final FuelTypeModel fuelTypeModel = new FuelTypeModel();
    private final CarModel carModel = new CarModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String licensePlateNo = txtFldLicensePlateNo.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Delete this Car?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = carModel.deleteCar(licensePlateNo);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Car Deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Delete Car...!").show();
            }
        }
    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        CarDTO carDTO = getTextFieldsValues();

        boolean isSaved = carModel.saveCar(carDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Car Saved...!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to Save Car...!").show();
        }
    }

    CarDTO getTextFieldsValues() {
        String licensePlateNo = txtFldLicensePlateNo.getText();
        String typeId = cmbTypeID.getValue();
        String model = txtFldModel.getText();
        String colour = txtFldColour.getText();
        double dailyRate = Double.parseDouble(txtFldDailyRate.getText());
        double monthlyRate = Double.parseDouble(txtFldMonthlyRate.getText());
        String availabilityStatus = cmbAvailabilityStatus.getValue();

        return new CarDTO(licensePlateNo, model, colour, dailyRate, monthlyRate, availabilityStatus, typeId);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        CarDTO carDTO = getTextFieldsValues();

        boolean isUpdate = carModel.updateCar(carDTO);

        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Car Updated...!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to Update Car...!").show();
        }
    }

    @FXML
    void cmbTypeIDOnAction(ActionEvent event) throws SQLException {
        String selectedTypeID = cmbTypeID.getSelectionModel().getSelectedItem();
        FuelTypeDTO fuelTypeDTO = fuelTypeModel.findById(selectedTypeID);

        if (fuelTypeDTO != null) {
            txtFldTypeName.setText(fuelTypeDTO.getTypeName());
        }
    }

    private void loadFuelTypeIds() throws SQLException {
        ArrayList<String> fuelTypeIds = fuelTypeModel.getAllFuelTypeIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(fuelTypeIds);
        cmbTypeID.setItems(observableList);
    }

    @FXML
    void tblCarsOnClicked(MouseEvent event) {
        CarTM selectedItem = tblCars.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtFldLicensePlateNo.setText(selectedItem.getLicensePlateNo());
            cmbTypeID.setValue(selectedItem.getTypeId());
            txtFldModel.setText(selectedItem.getModel());
            txtFldColour.setText(selectedItem.getColour());
            txtFldDailyRate.setText(String.valueOf(selectedItem.getDailyRate()));
            txtFldMonthlyRate.setText(String.valueOf(selectedItem.getMonthlyRate()));
            cmbAvailabilityStatus.setValue(selectedItem.getAvailabilityStatus());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) throws SQLException {
        String searchText = txtFldSearchHere.getText().toLowerCase();
        ArrayList<CarDTO> carDTOS = carModel.getAllCars();
        ObservableList<CarTM> filteredCars = FXCollections.observableArrayList();

        for (CarDTO carDTO : carDTOS) {
            if (carDTO.getLicensePlateNo().toLowerCase().contains(searchText) ||
                    carDTO.getModel().toLowerCase().contains(searchText)) {
                    filteredCars.add(new CarTM(
                            carDTO.getLicensePlateNo(),
                            carDTO.getTypeId(),
                            carDTO.getModel(),
                            carDTO.getColour(),
                            carDTO.getDailyRate(),
                            carDTO.getMonthlyRate(),
                            carDTO.getAvailabilityStatus()
                    ));
            }
        }
        tblCars.setItems(filteredCars);

        if (searchText.isEmpty()) {
            searchIcon.setVisible(true);
        } else {
            searchIcon.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbAvailabilityStatus.getItems().addAll("Yes", "No");

        colLicensePlateNo.setCellValueFactory(new PropertyValueFactory<>("licensePlateNo"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colColour.setCellValueFactory(new PropertyValueFactory<>("colour"));
        colDailyRate.setCellValueFactory(new PropertyValueFactory<>("dailyRate"));
        colMonthlyRate.setCellValueFactory(new PropertyValueFactory<>("monthlyRate"));
        colAvailabilityStatus.setCellValueFactory(new PropertyValueFactory<>("availabilityStatus"));
        colTypeID.setCellValueFactory(new PropertyValueFactory<>("typeId"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        loadFuelTypeIds();

        txtFldLicensePlateNo.setText("");
        cmbTypeID.getSelectionModel().clearSelection();
        txtFldTypeName.setText("");
        txtFldModel.setText("");
        txtFldColour.setText("");
        txtFldDailyRate.setText("");
        txtFldMonthlyRate.setText("");
        cmbAvailabilityStatus.setValue("");

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<CarDTO> carDTOS = carModel.getAllCars();
        ObservableList<CarTM> carTMS = FXCollections.observableArrayList();

        for (CarDTO carDTO : carDTOS) {
            CarTM carTM = new CarTM(
                    carDTO.getLicensePlateNo(),
                    carDTO.getTypeId(),
                    carDTO.getModel(),
                    carDTO.getColour(),
                    carDTO.getDailyRate(),
                    carDTO.getMonthlyRate(),
                    carDTO.getAvailabilityStatus()
            );
            carTMS.add(carTM);
        }
        tblCars.setItems(carTMS);
    }
}

