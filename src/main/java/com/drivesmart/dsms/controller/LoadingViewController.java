package com.drivesmart.dsms.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class LoadingViewController {

    @FXML
    private ImageView imageView;

    @FXML
    public void initialize() {

        // Schedule the transition to the login page after 3 seconds
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Platform.runLater(() -> {
                    try {
                        Parent loginView = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
                        Stage newStage = new Stage();
                        Scene scene = new Scene(loginView); // Set the width to 800 and height to 600
                        newStage.setScene(scene);
                        newStage.setTitle("DriveSmart");
                        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/icon.png")));
                        newStage.getIcons().add(icon);
                        newStage.resizableProperty().setValue(false);
                        newStage.initStyle(StageStyle.DECORATED); // Restore the title bar
                        newStage.show();

                        // Close the current stage
                        Stage currentStage = (Stage) imageView.getScene().getWindow();
                        currentStage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}