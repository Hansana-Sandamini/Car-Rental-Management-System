package com.example.rdfcarrentals.controller;

import com.example.rdfcarrentals.db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ReportsFormController {

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

    }

    @FXML
    void btnMonthOverviewGenerateOnAction(ActionEvent event) {

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

}
