module com.example.rdfcarrentals {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;


    opens com.example.rdfcarrentals.controller to javafx.fxml;
    exports com.example.rdfcarrentals;
}