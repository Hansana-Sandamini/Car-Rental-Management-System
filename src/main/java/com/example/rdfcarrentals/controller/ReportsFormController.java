package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ReportsFormController implements Initializable {

    @FXML
    private Button btnDurationReportGenerate;

    @FXML
    private Button btnMonthOverviewGenerate;

    @FXML
    private Button btnMonthReportGenerate;

    @FXML
    private Button btnYearOverviewGenerate;

    @FXML
    private Button btnYearReportGenerate;

    @FXML
    private AnchorPane reportsContent;

    @FXML
    private DatePicker txtFrom;

    @FXML
    private DatePicker txtTo;

    @FXML
    void btnDurationReportGenerateOnAction(ActionEvent event) {
        try {
            LocalDate fromDate = txtFrom.getValue();
            LocalDate toDate = txtTo.getValue();

            if (fromDate != null && toDate != null) {
                new Alert(Alert.AlertType.ERROR, "Please select both 'From' and 'To' dates.").show();
                return;
            }

            java.sql.Date sqlFromDate = java.sql.Date.valueOf(fromDate);
            java.sql.Date sqlToDate = java.sql.Date.valueOf(toDate);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("txtFrom", sqlFromDate);
            parameters.put("txtTo", sqlToDate);

            Connection connection = DBConnection.getInstance().getConnection();
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/DurationReport.jrxml"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        parameters,
                        connection
            );
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Report..!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Data Empty..!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Report..!").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnMonthOverviewGenerateOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("p_Date", LocalDate.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/MonthOverview.jrxml"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Monthly Overview Report..!").show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Data Empty..!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Report..!").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnMonthReportGenerateOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("p_Date", LocalDate.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/MonthReport.jrxml"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Report..!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Data Empty..!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Report..!").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnYearOverviewGenerateOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("p_Date", LocalDate.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/YearOverview.jrxml"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate Yearly Overview Report.").show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Data Empty..!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Report..!").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnYearReportGenerateOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("p_Date", LocalDate.now().toString());

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/YearReport.jrxml"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Report..!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Data Empty..!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Report..!").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
