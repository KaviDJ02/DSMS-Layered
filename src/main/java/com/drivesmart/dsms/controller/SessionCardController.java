package com.drivesmart.dsms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SessionCardController {

    @FXML
    public Label timeLabel;
    @FXML
    private Label studentName;
    @FXML
    private Label instructorName;
    @FXML
    private Label vehicleModel;

    public void setSessionData(String time, String student, String instructor, String vehicle) {
        timeLabel.setText(time);
        studentName.setText(student);
        instructorName.setText(instructor);
        vehicleModel.setText(vehicle);
    }
}