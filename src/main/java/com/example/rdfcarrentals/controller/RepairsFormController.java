package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.CarDTO;
import com.example.rdfcarrentals.dto.RepairDTO;
import com.example.rdfcarrentals.model.CarModel;
import com.example.rdfcarrentals.model.RepairModel;
import com.example.rdfcarrentals.tm.RepairTM;
import com.example.rdfcarrentals.util.CrudUtil;
import com.example.rdfcarrentals.util.OptionButtonsUtil;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class RepairsFormController implements Initializable {

    @FXML
    private Label lblHeadingUserName;

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

    @FXML
    private FontAwesomeIcon searchIcon;

    private final RepairModel repairModel = new RepairModel();
    private final ObservableList<RepairTM> repairTMS = FXCollections.observableArrayList();
    private final CarModel carModel = new CarModel();

    @FXML
    void btnAddRepairOnAction(ActionEvent event) throws SQLException {
        RepairDTO repairDTO = getTextFieldsValues();

        boolean isSaved = repairModel.addRepair(repairDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Repair Saved...!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to Save Repair...!").show();
        }
    }

    RepairDTO getTextFieldsValues() {
        String repairId = lblRepairID.getText();
        String licensePlateNo = cmbLicensePlateNo.getValue();
        String description = txtFldDescription.getText();
        Date date = java.sql.Date.valueOf(txtDate.getValue());
        double cost = Double.parseDouble(txtFldCost.getText());

        return new RepairDTO(repairId, description, date, cost, licensePlateNo);
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
    void txtFldSearchHereOnAction(KeyEvent event) throws SQLException {
        String searchText = txtFldSearchHere.getText().toLowerCase();
        ArrayList<RepairDTO> repairDTOS = repairModel.getAllRepairs();
        ObservableList<RepairTM> filteredRepairs = FXCollections.observableArrayList();

        for (RepairDTO repairDTO : repairDTOS) {
            if (repairDTO.getRepairId().toLowerCase().contains(searchText) ||
                repairDTO.getLicensePlateNo().toLowerCase().contains(searchText) ||
                repairDTO.getDescription().toLowerCase().contains(searchText)) {
                filteredRepairs.add(new RepairTM(
                        repairDTO.getRepairId(),
                        repairDTO.getLicensePlateNo(),
                        repairDTO.getDescription(),
                        repairDTO.getDate(),
                        repairDTO.getCost()
                ));
            }
        }
        tblRepairs.setItems(filteredRepairs);

        if (searchText.isEmpty()) {
            searchIcon.setVisible(true);
        } else {
            searchIcon.setVisible(false);
        }
    }

    @FXML
    void tblRepairsOnClicked(MouseEvent event) {
        RepairTM selectedItem = tblRepairs.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblRepairID.setText(selectedItem.getRepairId());
            cmbLicensePlateNo.setValue(selectedItem.getLicensePlateNo());
            txtFldDescription.setText(selectedItem.getDescription());
            txtDate.setValue(LocalDate.parse(LocalDate.now().toString()));
            txtFldCost.setText(String.valueOf(selectedItem.getCost()));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colRepairID.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        colLicensePlateNo.setCellValueFactory(new PropertyValueFactory<>("licensePlateNo"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        tblRepairs.getColumns().get(5).setCellValueFactory(param -> {
            ImageView btnRemove = OptionButtonsUtil.setRemoveButton();

            btnRemove.setOnMouseClicked(event -> {
                RepairTM selectedRepair = param.getValue();
                tblRepairs.getSelectionModel().select(selectedRepair);
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
        loadLicensePlateNos();

        lblRepairID.setText(repairModel.getNextRepairId());
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
