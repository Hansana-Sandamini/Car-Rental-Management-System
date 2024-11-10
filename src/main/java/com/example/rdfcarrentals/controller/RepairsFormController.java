package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.RepairDTO;
import com.example.rdfcarrentals.model.RepairModel;
import com.example.rdfcarrentals.tm.PaymentTM;
import com.example.rdfcarrentals.tm.RepairTM;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class RepairsFormController implements Initializable {

    @FXML
    private Button btnAddRepair;

    @FXML
    private Button btnRefresh;

    @FXML
    private ComboBox<String> cmbLicensePlateNo;

    @FXML
    private TableColumn<RepairTM, Double> colCost;

    @FXML
    private TableColumn<RepairTM, Date> colDate;

    @FXML
    private TableColumn<RepairTM, String> colDescription;

    @FXML
    private TableColumn<RepairTM, String> colLicensePlateNo;

    @FXML
    private TableColumn<?, ?> colOption;

    @FXML
    private TableColumn<RepairTM, String> colRepairID;

    @FXML
    private Label lblRepairID;

    @FXML
    private AnchorPane repairsContent;

    @FXML
    private TableView<RepairTM> tblRepairs;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtFldCost;

    @FXML
    private TextField txtFldDescription;

    @FXML
    private TextField txtFldSearchHere;

    RepairModel repairModel = new RepairModel();
    private final ObservableList<RepairTM> repairTMS = FXCollections.observableArrayList();

    @FXML
    void btnAddRepairOnAction(ActionEvent event) {

    }

    @FXML
    void btnRefreshOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void cmbLicensePlateNoOnAction(ActionEvent event) {

    }

    @FXML
    void txtFldSearchHereOnAction(KeyEvent event) {

    }

    @FXML
    void tblRepairsOnClicked(MouseEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colRepairID.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        colLicensePlateNo.setCellValueFactory(new PropertyValueFactory<>("licensePlateNo"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        tblRepairs.getColumns().get(5).setCellValueFactory(param -> {
            Button btnRemove = new Button("Remove");

            btnRemove.setOnMouseClicked(event -> {
                RepairTM selectedRepair = param.getValue();
                tblRepairs.getSelectionModel().select(selectedRepair);
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
        RepairTM selectedRepair = tblRepairs.getSelectionModel().getSelectedItem();

        if (selectedRepair != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this Repair?", ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.isPresent() && buttonType.get().equals(ButtonType.YES)) {
                try {
                    CrudUtil.execute("DELETE FROM repair WHERE repair_id = ?", selectedRepair.getRepairId());
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Repair Successfully Deleted...!");
                    successAlert.showAndWait();
                    refreshTable();
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No repair selected to remove...!").show();
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        lblRepairID.setText("");
        cmbLicensePlateNo.setValue("");
        txtFldDescription.setText("");
        txtFldCost.setText("");
        txtDate.setValue(null);
    }

    private void refreshTable() throws SQLException {
        ArrayList<RepairDTO> repairDTOS = repairModel.getAllRepairs();
        repairTMS.clear();

        for (RepairDTO repairDTO : repairDTOS) {
            RepairTM repairTM = new RepairTM(
                    repairDTO.getRepairId(),
                    repairDTO.getLicensePlateNo(),
                    repairDTO.getDescription(),
                    repairDTO.getDate(),
                    repairDTO.getCost()
            );
            repairTMS.add(repairTM);
        }
        tblRepairs.setItems(repairTMS);
    }

}
