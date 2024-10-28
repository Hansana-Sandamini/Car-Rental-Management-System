module com.example.rdfcarrentals {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;


    opens com.example.rdfcarrentals.controller to javafx.fxml;
    opens com.example.rdfcarrentals.tm to javafx.base;
    exports com.example.rdfcarrentals;
}