package com.example.rdfcarrentals;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("/view/WelcomeForm.fxml"));
        Scene scene = new Scene(load);
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("/asserts/WelcomePage.jpeg")));
        Image welcomePageImage = new Image(getClass().getResourceAsStream("/asserts/WelcomePage.jpeg"));
        stage.setScene(scene);
        stage.show();
    }
}
