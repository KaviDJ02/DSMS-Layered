package com.drivesmart.dsms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PackageCardController {

    @FXML
    public Label packagePrice;

    @FXML
    public Button editButton;

    @FXML
    public Button deleteBtton;

    @FXML
    public Label durationLabel;

    @FXML
    private Label courseNameLabel;

    @FXML
    private Label courseDescriptionLabel;

    @FXML
    private Button enrollButton;

    public void setCourseName(String courseName) {
        courseNameLabel.setText(courseName);
    }

    public void setCourseDescription(String courseDescription) {
        courseDescriptionLabel.setText(courseDescription);
    }

    public void setEnrollButtonAction(Runnable action) {
        enrollButton.setOnAction(event -> action.run());
    }

    public void setEditButtonAction(Runnable action) {
        editButton.setOnAction(event -> action.run());
    }

    public void setDeleteButtonAction(Runnable action) {
        deleteBtton.setOnAction(event -> action.run());
    }

    public void setPackagePrice(String price) {
        packagePrice.setText(price);
    }

    public void setDurationLabel(String duration) {
        durationLabel.setText(duration);
    }


}