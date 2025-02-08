package com.drivesmart.dsms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainDashViewController {

    @FXML
    public Button sessionBtn;

    @FXML
    public Button emailBtn;

    @FXML
    public Button paymentBtn;

    @FXML
    public Button employeeBtn;

    @FXML
    public Button vehicleBtn;

    @FXML
    private AnchorPane content;

    @FXML
    private Button courseBtn;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button studentBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    void initialize() {
        navigateTo("/view/DashboardView.fxml");
        activeButton(dashboardBtn);
    }

    @FXML
    void courseBtnOnAction(ActionEvent event) {
        navigateTo("/view/PackageView.fxml");
        activeButton(courseBtn);

    }

    @FXML
    void dashboardBtnOnAction(ActionEvent event) {
        navigateTo("/view/DashboardView.fxml");
        activeButton(dashboardBtn);

    }

    @FXML
    void logoutBtnOnAction(ActionEvent event) {
        try {
            // Load the LoginView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Set the new scene
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void studentBtnOnAction(ActionEvent event) {
        navigateTo("/view/StudentView.fxml");
        activeButton(studentBtn);

    }


    public void navigateTo(String fxmlPath) {
        try {
            content.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

//  -------- Loaded anchor edges are bound to the content anchor --------
//      (1) Bind the loaded FXML to all edges of the content anchorPane
            load.prefWidthProperty().bind(content.widthProperty());
            load.prefHeightProperty().bind(content.heightProperty());

//      (2) Bind the loaded FXML to all edges of the AnchorPane
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setRightAnchor(load, 0.0);
            AnchorPane.setBottomAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);

            content.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    public void activeButton(Button button) {
        List<Button> buttons = Arrays.asList(
                courseBtn,
                dashboardBtn,
                studentBtn,
                settingsBtn,
                sessionBtn,
                vehicleBtn,
                employeeBtn,
                paymentBtn,
                emailBtn);

        buttons.forEach(b -> b.setStyle(null));

        button.setStyle("-fx-background-color: #34495e");
    }

    public void settingsBtnOnAction(ActionEvent actionEvent) {
        navigateTo("/view/SettingsView.fxml");
        activeButton(settingsBtn);
    }

    public void sessionBtnOnAction(ActionEvent actionEvent) {
        navigateTo("/view/SessionView.fxml");
        activeButton(sessionBtn);
    }

    public void vehicleBtnOnAction(ActionEvent actionEvent) {
        navigateTo("/view/VehicleView.fxml");
        activeButton(vehicleBtn);
    }

    public void employeeBtnOnAction(ActionEvent actionEvent) {
        navigateTo("/view/EmployeeView.fxml");
        activeButton(employeeBtn);
    }

    public void paymentBtnOnAction(ActionEvent actionEvent) {
        navigateTo("/view/PaymentView.fxml");
        activeButton(paymentBtn);
    }

    public void emailBtnOnAction(ActionEvent actionEvent) {
        navigateTo("/view/EmailView.fxml");
        activeButton(emailBtn);
    }
}