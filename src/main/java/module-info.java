module com.example.rdfcarrentals {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rdfcarrentals.controller to javafx.fxml;
    exports com.example.rdfcarrentals;
}