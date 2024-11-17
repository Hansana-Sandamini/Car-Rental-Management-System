package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.ReservationDetailDTO;
import com.example.rdfcarrentals.model.ReservationDetailModel;
import com.example.rdfcarrentals.tm.ReservationDetailTM;
import com.example.rdfcarrentals.util.CrudUtil;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservationDetailFormController implements Initializable {

    @FXML
    private TableColumn<ReservationDetailTM, Double> colDriverCost;

    @FXML
    private TableColumn<ReservationDetailTM, String> colLicensePlateNo;

    @FXML
    private TableColumn<ReservationDetailTM, String> colReservationId;

    @FXML
    private TableColumn<ReservationDetailTM, Double> colTotalAmount;

    @FXML
    private AnchorPane reservationDetailPane;

    @FXML
    private TableView<ReservationDetailTM> tblReservationDetail;

    private final ReservationDetailModel reservationDetailModel = new ReservationDetailModel();
    private final ObservableList<ReservationDetailTM> reservationDetailTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colLicensePlateNo.setCellValueFactory(new PropertyValueFactory<>("licensePlateNo"));
        colDriverCost.setCellValueFactory(new PropertyValueFactory<>("driverCost"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        loadReservationDetails();
    }

    private void loadReservationDetails() {
        try {
            ArrayList<ReservationDetailDTO> reservationDetailDTOS = reservationDetailModel.getReservationDetails();
            reservationDetailTMS.clear();
            for (ReservationDetailDTO reservationDetailDTO : reservationDetailDTOS) {
                reservationDetailTMS.add(new ReservationDetailTM(
                        reservationDetailDTO.getReservationId(),
                        reservationDetailDTO.getLicensePlateNo(),
                        reservationDetailDTO.getDriverCost(),
                        reservationDetailDTO.getTotalAmount()
                ));
                tblReservationDetail.setItems(reservationDetailTMS);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Reservation Details: " + e.getMessage()).show();
        }
    }

    public static ArrayList<String> getTopProducts() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT c.model, COUNT(model) FROM car c LEFT JOIN reservation_detail rd ON c.license_plate_no = rd.license_plate_no GROUP BY model ORDER BY COUNT(model) DESC LIMIT 3;");
        ArrayList<String> arrayList = new ArrayList();
        while (resultSet.next()) {
            arrayList.add(resultSet.getString(1));
        }
        return arrayList;
    }
}

