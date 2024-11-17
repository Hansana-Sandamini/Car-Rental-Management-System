module com.example.rdfcarrentals {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.management;
    requires net.sf.jasperreports.core;
    requires fontawesomefx;

    opens com.example.rdfcarrentals.controller to javafx.fxml;
    opens com.example.rdfcarrentals.tm to javafx.base;
    exports com.example.rdfcarrentals;
}