package com.drivesmart.dsms.controller;

import com.drivesmart.dsms.dto.EmployeeDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EmployeeCardController {

    @FXML
    private Label employeePhoneLabel;
    @FXML
    private Label employeeEmailLabel;
    @FXML
    private Label employeePositionLabel;
    @FXML
    private Label employeeNameLabel;

    private EmployeeDTO employee;
    private EmployeeViewController parentController;

    public void setEmployeeData(EmployeeDTO employee, EmployeeViewController parentController) {
        this.employee = employee;
        this.parentController = parentController;
        employeeNameLabel.setText(employee.getName());
        employeePositionLabel.setText(employee.getPosition());
        employeeEmailLabel.setText(employee.getEmail());
        employeePhoneLabel.setText(employee.getPhone());
    }

    @FXML
    public void editOnAction(ActionEvent actionEvent) {
        parentController.editEmployee(employee);
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        parentController.deleteEmployee(employee);
    }
}