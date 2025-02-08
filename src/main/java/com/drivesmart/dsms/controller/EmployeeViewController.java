package com.drivesmart.dsms.controller;

import com.drivesmart.dsms.bo.BOFactory;
import com.drivesmart.dsms.bo.custom.EmployeeBO;
import com.drivesmart.dsms.dto.EmployeeDTO;
import com.drivesmart.dsms.util.UserPermissions;
import com.drivesmart.dsms.util.Validate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeViewController {
    @FXML
    public Button addEmpBtn;
    @FXML
    private TextField searchTF;
    @FXML
    private Label employeeCount;
    @FXML
    private AnchorPane MainPane;
    @FXML
    private VBox employeesVbox;
    @FXML
    private ChoiceBox<String> roleDropBox;
    @FXML
    private TextField phoneTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField nameTF;
    @FXML
    private AnchorPane addEmployeePane;
    @FXML
    private TextField nicTF;
    @FXML
    private TextField salaryTF;

    private ObservableList<EmployeeDTO> employeeData = FXCollections.observableArrayList();

    public EmployeeViewController() throws SQLException {
    }

    @FXML
    public void initialize() {
        MainPane.setVisible(true);
        addEmployeePane.setVisible(false);
        try {
            loadEmployeeCards();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        updateEmployeeCount();

        roleDropBox.getItems().addAll("instructor", "manager", "staff");

    }


    @FXML
    public void searchOnAction(ActionEvent actionEvent) {
        String searchText = searchTF.getText().toLowerCase();
        ObservableList<EmployeeDTO> filteredData = FXCollections.observableArrayList();

        for (EmployeeDTO employee : employeeData) {
            if (employee.getName().toLowerCase().contains(searchText) ||
                    employee.getEmail().toLowerCase().contains(searchText) ||
                    employee.getPosition().toLowerCase().contains(searchText)) {
                filteredData.add(employee);
            }
        }

        loadEmployeeCards(filteredData);
    }

    @FXML
    public void backOnAction(MouseEvent mouseEvent) {
        addEmployeePane.setVisible(false);
        MainPane.setVisible(true);
    }

    private void updateEmployeeCount() {
        employeeCount.setText(String.valueOf(employeeData.size()));
    }

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    private void loadEmployeeCards() throws SQLException, ClassNotFoundException {
        List<EmployeeDTO> employees = employeeBO.getAllEmployees();
        employeeData.setAll(employees);
        loadEmployeeCards(employeeData);
    }

    private void loadEmployeeCards(ObservableList<EmployeeDTO> employees) {
        employeesVbox.getChildren().clear();
        HBox hBox = new HBox(10);
        int count = 0;

        for (EmployeeDTO employee : employees) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmployeeCard.fxml"));
                Node employeeCard = loader.load();

                EmployeeCardController controller = loader.getController();
                controller.setEmployeeData(employee, this);

                hBox.getChildren().add(employeeCard);
                count++;

                if (count == 2) {
                    employeesVbox.getChildren().add(hBox);
                    hBox = new HBox(10);
                    count = 0;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!hBox.getChildren().isEmpty()) {
            employeesVbox.getChildren().add(hBox);
        }
    }

    private void clearFields() {
        nameTF.clear();
        emailTF.clear();
        phoneTF.clear();
        roleDropBox.setValue(null);
    }

    public void goAddEmpOnAction(ActionEvent actionEvent) {
        if(!UserPermissions.isAdmin()){
            return;
        }

        MainPane.setVisible(false);
        addEmployeePane.setVisible(true);
    }

    public void addEmpBtnOnAction(ActionEvent actionEvent) {
        addEmpBtn.setText("Add Employee");

        String name = nameTF.getText();
        String email = emailTF.getText();
        String position = roleDropBox.getValue();
        String phone = phoneTF.getText();
        String nic = nicTF.getText();
        String salary = salaryTF.getText();

        if (name.isEmpty() || email.isEmpty() || position == null || phone.isEmpty() || nic.isEmpty() || salary.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        } else if (!Validate.validateTextField(nameTF, "Name") ||
                !Validate.validateTextField(emailTF, "Email") ||
                !Validate.validateTextField(phoneTF, "SriLankanPhoneNumber") ||
                !Validate.validateTextField(nicTF, "NIC") ||
                !Validate.validateTextField(salaryTF, "Integer")) {
            System.out.println("Please enter valid data");
            return;
        }

        EmployeeDTO newEmployee = new EmployeeDTO();
        newEmployee.setName(name);
        newEmployee.setEmail(email);
        newEmployee.setPosition(position);
        newEmployee.setPhone(phone);
        newEmployee.setNic(nic);
        newEmployee.setSalary(salary);

        try {
            employeeBO.saveEmployee(newEmployee);
            employeeData.add(newEmployee);
            updateEmployeeCount();
            loadEmployeeCards(employeeData);
            clearFields();

            System.out.println("Employee added successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }

        MainPane.setVisible(true);
        addEmployeePane.setVisible(false);

    }

    public void editEmployee(EmployeeDTO employee) {
        if(!UserPermissions.isAdmin()){
            return;
        }

        // Set the current employee details in the form
        nameTF.setText(employee.getName());
        emailTF.setText(employee.getEmail());
        phoneTF.setText(employee.getPhone());
        nicTF.setText(employee.getNic());
        roleDropBox.setValue(employee.getPosition());
        salaryTF.setText(employee.getSalary());

        //validations
        if (!Validate.validateTextField(nameTF, "Name") ||
                !Validate.validateTextField(emailTF, "Email") ||
                !Validate.validateTextField(phoneTF, "SriLankanPhoneNumber") ||
                !Validate.validateTextField(nicTF, "NIC") ||
                !Validate.validateTextField(salaryTF, "Integer")) {
            System.out.println("Please enter valid data");
            return;
        }

        // Show the add/edit pane
        MainPane.setVisible(false);
        addEmployeePane.setVisible(true);

        // Set the action for the save button to update the employee
        addEmpBtn.setText("Update Employee");
        addEmpBtn.setOnAction(event -> {
            // Get the updated data
            String name = nameTF.getText();
            String email = emailTF.getText();
            String phone = phoneTF.getText();
            String nic = nicTF.getText();
            String position = roleDropBox.getValue();
            String salary = salaryTF.getText();

            // Validate the data
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || nic.isEmpty() || position == null || salary.isEmpty()) {
                System.out.println("Please fill in all fields.");
                return;
            }

            // Update the employee object
            employee.setName(name);
            employee.setEmail(email);
            employee.setPhone(phone);
            employee.setNic(nic);
            employee.setPosition(position);
            employee.setSalary(salary);

            // Save the updated employee data
            try {
                if (employeeBO.updateEmployee(employee)) {
                    System.out.println("Employee Updated Successfully");
                    loadEmployeeCards();
                    updateEmployeeCount();
                } else {
                    System.out.println("Employee Updating Failed");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            // Hide the add/edit pane
            MainPane.setVisible(true);
            addEmployeePane.setVisible(false);
        });

    }

    public void deleteEmployee(EmployeeDTO employee) {
        if(!UserPermissions.isAdmin()){
            return;
        }

        try {
            if (employeeBO.deleteEmployee(employee.getNic())) {
                System.out.println("Employee Deleted Successfully");
                employeeData.remove(employee);
                loadEmployeeCards();
                updateEmployeeCount();
            } else {
                System.out.println("Employee Deleting Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}