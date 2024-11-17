package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.dto.CarDTO;
import com.example.rdfcarrentals.model.CarModel;
import com.example.rdfcarrentals.model.CreditModel;
import com.example.rdfcarrentals.model.ReservationModel;
import com.example.rdfcarrentals.util.ClockUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.chart.BarChart;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminDashboardFormController implements Initializable {

    @FXML
    private AnchorPane adminDashboardContent;

    @FXML
    private AnchorPane adminDashboardHeadingPane;

    @FXML
    private Label lblCreditNotPaid;

    @FXML
    private Label totalSalesViewDetails;

    @FXML
    private Label creditNotPaidViewDetails;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotalSales;

    @FXML
    private Label lblRev;

    @FXML
    private Label top1;

    @FXML
    private Label top11;

    @FXML
    private Label top2;

    @FXML
    private Label top22;

    @FXML
    private Label top3;

    @FXML
    private Label top33;

    @FXML
    private BarChart<String, Number> barChart;

    private final ReservationModel reservationModel = new ReservationModel();
    private final CreditModel creditModel = new CreditModel();
    private final CarModel carModel = new CarModel();

    @FXML
    void creditNotPaidViewDetailsOnAction(MouseEvent event) {
        navigateTo("/view/CreditsForm.fxml");
    }

    @FXML
    void totalSalesViewDetailsOnAction(MouseEvent event) {
        navigateTo("/view/ReservationsForm.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClockUtil.startClock(lblDate, lblTime);

        try {
            lblTotalSales.setText(reservationModel.getMonthlySales() + " Sales");
            lblCreditNotPaid.setText(creditModel.getCreditNotPaidCount() + " Sales");
            setTopProducts();
            lblRev.setText("Rs " + ReservationsFormController.getYearTotalSaleAmount() + ".00");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void navigateTo(String fxmlPath) {
        try {
            adminDashboardContent.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            adminDashboardContent.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load page...!").show();
            e.printStackTrace();
        }
        loadChart();
    }

    private void setTopProducts() throws SQLException, ClassNotFoundException {
        ArrayList<String> products = ReservationDetailFormController.getTopProducts();

        /*top1.setText(products.get(0));
        top11.setText(carModel.findByLicensePlateNo(products.get(0)).getLicensePlateNo());
        top2.setText(products.get(1));
        top22.setText(carModel.findByLicensePlateNo(products.get(1)).getLicensePlateNo());
        top3.setText(products.get(2));
        top33.setText(carModel.findByLicensePlateNo(products.get(3)).getLicensePlateNo());*/

        String product1 = products.get(0);
        if (product1 != null) {
            top1.setText(product1);
            String licensePlate1 = getLicensePlateForProduct(product1);
            top11.setText(licensePlate1);
        }

        String product2 = products.get(1);
        if (product2 != null) {
            top2.setText(product2);
            String licensePlate2 = getLicensePlateForProduct(product2);
            top22.setText(licensePlate2);
        }

        String product3 = products.get(2);
        if (product3 != null) {
            top3.setText(product3);
            String licensePlate3 = getLicensePlateForProduct(product3);
            top33.setText(licensePlate3);
        }
    }

    private String getLicensePlateForProduct(String licensePlate) {
        try {
            CarDTO carDTO = carModel.findByLicensePlateNoTopProducts(licensePlate);
            return carDTO != null ? carDTO.getLicensePlateNo() : "Unknown License Plate";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error Retrieving License Plate";
        }
    }

    private void loadChart() {
        loadChart(barChart);
    }

    private void loadChart(BarChart barChart) {
        XYChart.Series series = new XYChart.Series();
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        try {
            int count = 0;
            for (Double income : ReservationsFormController.getIncomeMonthly()) {
                series.getData().add(new XYChart.Data(months[count++], income));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        barChart.getData().setAll(series);
    }
}
