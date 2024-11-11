package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.DriverAssignmentDTO;
import com.example.rdfcarrentals.model.DriverAssignmentModel;
import com.example.rdfcarrentals.tm.DriverAssignmentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DriverAssignmentFormController implements Initializable {

    @FXML
    private TableColumn<DriverAssignmentTM, String> colDriverNic;

    @FXML
    private TableColumn<DriverAssignmentTM, String> colLicensePlateNo;

    @FXML
    private TableColumn<DriverAssignmentTM, Double> colPricePerKm;

    @FXML
    private AnchorPane driverAssignmentPane;

    @FXML
    private TableView<DriverAssignmentTM> tblDriverAssignment;

    private final DriverAssignmentModel driverAssignmentModel = new DriverAssignmentModel();
    private final ObservableList<DriverAssignmentTM> driverAssignmentTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colLicensePlateNo.setCellValueFactory(new PropertyValueFactory<>("licensePlateNo"));
        colDriverNic.setCellValueFactory(new PropertyValueFactory<>("driverNic"));
        colPricePerKm.setCellValueFactory(new PropertyValueFactory<>("pricePerKm"));

        loadDriverAssignments();
    }

    private void loadDriverAssignments() {
        try {
            ArrayList<DriverAssignmentDTO> driverAssignmentDTOS = driverAssignmentModel.getDriverAssignments();
            driverAssignmentTMS.clear();
            for (DriverAssignmentDTO driverAssignmentDTO : driverAssignmentDTOS) {
                driverAssignmentTMS.add(new DriverAssignmentTM(
                        driverAssignmentDTO.getLicensePlateNo(),
                        driverAssignmentDTO.getDriverNic(),
                        driverAssignmentDTO.getPricePerKm()
                ));
            }
            tblDriverAssignment.setItems(driverAssignmentTMS);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Driver Assignments: " + e.getMessage()).show();
        }
    }
}