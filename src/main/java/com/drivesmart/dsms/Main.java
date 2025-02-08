package com.drivesmart.dsms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoadingView.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("DriveSmart");
        primaryStage.setScene(new Scene(root));
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/icon.png")));
        primaryStage.getIcons().add(icon);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}