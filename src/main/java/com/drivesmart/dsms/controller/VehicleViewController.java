package com.drivesmart.dsms.controller;

import com.drivesmart.dsms.bo.BOFactory;
import com.drivesmart.dsms.bo.custom.VehicleBO;
import com.drivesmart.dsms.dto.VehicleDTO;
import com.drivesmart.dsms.dto.VehicleStatsDTO;
import com.drivesmart.dsms.util.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class VehicleViewController {

    @FXML
    public AnchorPane MainPane;

    @FXML
    public Button addVehicleBtn;

    @FXML
    private AnchorPane addEmployeePane;

    @FXML
    private TextField makeTF;

    @FXML
    private TextField modelTF;

    @FXML
    private TextField plateTF;

    @FXML
    private Label totalVehicalLLabel;

    @FXML
    private ChoiceBox<String> transmissionDropBox;

    @FXML
    private ChoiceBox<String> typeDropBox;

    @FXML
    private VBox vehicleVbox;

    VehicleBO vehicleBO = (VehicleBO) BOFactory.getInstance().getBO(BOFactory.BOType.VEHICLE);

    private VehicleDTO vehicleDTO;

    public VehicleViewController() throws SQLException {
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        // Initialize the transmission drop-down
        transmissionDropBox.getItems().addAll("Automatic", "Manual");

        // Initialize the type drop-down
        typeDropBox.getItems().addAll("motorcycle","car", "van", "truck", "bus");

        // Update the vehicle list
        updateVehicleList();

        addEmployeePane.setVisible(false);
        MainPane.setVisible(true);
    }

    @FXML
    void addVehicleBtnOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        // Get the entered data
        String make = makeTF.getText();
        String model = modelTF.getText();
        String plate = plateTF.getText();
        String transmission = transmissionDropBox.getValue();
        String type = typeDropBox.getValue();

        // Validate the data
        if (make.isEmpty() || model.isEmpty() || plate.isEmpty() || transmission == null || type == null) {
            System.out.println("Please fill in all fields.");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowTime = now.format(formatter);

        VehicleDTO vehicle = new VehicleDTO(type, make, model, transmission, plate);
        VehicleStatsDTO vehicleStatsDTO = new VehicleStatsDTO(vehicleBO.getVehicleId(plate), "Available", nowTime);

        if(vehicleDTO != null){
            if (vehicleBO.updateVehicle(vehicle, vehicleDTO.getLicensePlate())) {
                System.out.println("Vehicle updated successfully.");
                AlertUtil.showSimpleAlert("Vehicle Updated", "Vehicle updated successfully.", vehicle.getLicensePlate()+" vehicle has been updated successfully.");
            } else {
                System.out.println("Failed to update vehicle.");
                AlertUtil.showErrorAlert("Failed to Update Vehicle", "Failed to update vehicle.", "An error occurred while updating the vehicle.");
            }
        }else {
            if(vehicleBO.addVehicleWithStats(vehicle, vehicleStatsDTO)){
                System.out.println("Vehicle saved successfully.");
                AlertUtil.showSimpleAlert("Vehicle Saved", "Vehicle saved successfully.", vehicle.getLicensePlate()+" vehicle has been saved successfully.");
            } else {
                System.out.println("Failed to save vehicle.");
                AlertUtil.showErrorAlert("Failed to Save Vehicle", "Failed to save vehicle.", "An error occurred while saving the vehicle.");
            }
        }

        // Clear text fields
        makeTF.clear();
        modelTF.clear();
        plateTF.clear();
        transmissionDropBox.setValue(null);
        typeDropBox.setValue(null);

        // Refresh the vehicle list
        updateVehicleList();

        addEmployeePane.setVisible(false);
        MainPane.setVisible(true);
    }

    @FXML
    void addVehicleButtonOnAction(ActionEvent event) {
        addEmployeePane.setVisible(true);
        MainPane.setVisible(false);
    }

    @FXML
    void backOnAction(MouseEvent event) {
        makeTF.clear();
        modelTF.clear();
        plateTF.clear();
        transmissionDropBox.setValue(null);
        typeDropBox.setValue(null);

        addEmployeePane.setVisible(false);
        MainPane.setVisible(true);
    }

    private void updateVehicleList() throws SQLException, ClassNotFoundException {
        // Fetch the updated list of vehicles (this method should be implemented in your model)
        List<VehicleDTO> vehicles = vehicleBO.getAllVehicles();

        // Clear the current list
        vehicleVbox.getChildren().clear();

        // Add each vehicle to the VBox (this method should be implemented to create a UI representation of a vehicle)
        for (VehicleDTO vehicle : vehicles) {
            vehicleVbox.getChildren().add(createVehicleCard(vehicle));
        }

        // Update the total vehicle count
        totalVehicalLLabel.setText(String.valueOf(vehicles.size()));
    }

    private Node createVehicleCard(VehicleDTO vehicle) throws SQLException, ClassNotFoundException {
        VBox vehicleCard = new VBox();
        vehicleCard.setSpacing(5);
        vehicleCard.setStyle("-fx-padding: 15; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-insets: 10; -fx-border-radius: 10; -fx-border-color: #007acc; -fx-background-color: #2e2e2e;");

        Label makeModelLabel = new Label("Make & Model: " + vehicle.getMake() + " " + vehicle.getModel());
        Label plateLabel = new Label("License Plate: " + vehicle.getLicensePlate());
        Label transmissionLabel = new Label("Transmission: " + vehicle.getTransmission());
        Label typeLabel = new Label("Type: " + vehicle.getVehicleType());
        Label statusLabel = new Label("Status: " + vehicleBO.getStatus(vehicle.getLicensePlate()));

        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> handleEditVehicle(vehicle));

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            try {
                handleDeleteVehicle(vehicle);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button updateStatusButton = new Button("Update Status");
        updateStatusButton.setOnAction(event -> {
            try {
                handleUpdateVehicleStatus(vehicle);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        HBox buttonBox = new HBox(10, editButton, deleteButton, updateStatusButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        vehicleCard.getChildren().addAll(makeModelLabel, plateLabel, transmissionLabel, typeLabel, statusLabel, buttonBox);

        return vehicleCard;
    }

    private void handleUpdateVehicleStatus(VehicleDTO vehicle) throws SQLException, ClassNotFoundException {
        // Show a confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update Vehicle Status");
        alert.setHeaderText("Are you sure you want to update the status of this vehicle?");
        alert.setContentText("Vehicle: " + vehicle.getLicensePlate());

        // Wait for the user's response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Update the vehicle status
            String newStatus = vehicleBO.toggleStatus(vehicle.getLicensePlate());
            if (newStatus != null) {
                System.out.println("Vehicle status updated to: " + newStatus);
                AlertUtil.showSimpleAlert("Status Updated", "Vehicle status updated successfully.", "The status of vehicle " + vehicle.getLicensePlate() + " has been updated to " + newStatus + ".");

                // Refresh the vehicle list
                updateVehicleList();
            } else {
                System.out.println("Failed to update vehicle status.");
                AlertUtil.showErrorAlert("Failed to Update Status", "Failed to update vehicle status.", "An error occurred while updating the status of vehicle " + vehicle.getLicensePlate() + ".");
            }
        }
    }

    private void handleEditVehicle(VehicleDTO vehicle) {
        addVehicleBtn.setText("Update Vehicle");

    // Set the current vehicle details in the form
    makeTF.setText(vehicle.getMake());
    modelTF.setText(vehicle.getModel());
    plateTF.setText(vehicle.getLicensePlate());
    transmissionDropBox.setValue(vehicle.getTransmission());
    typeDropBox.setValue(vehicle.getVehicleType());

    // Show the add/edit pane
    addEmployeePane.setVisible(true);
    MainPane.setVisible(false);

    vehicleDTO = vehicle;
}

private void handleDeleteVehicle(VehicleDTO vehicle) throws SQLException, ClassNotFoundException {
    // Delete the vehicle from the database
    if (vehicleBO.deleteVehicle(String.valueOf(vehicle))) {
        System.out.println("Vehicle deleted successfully.");
        AlertUtil.showSimpleAlert("Vehicle Deleted", "Vehicle deleted successfully.", vehicle.getLicensePlate()+" vehicle has been deleted successfully.");
    } else {
        System.out.println("Failed to delete vehicle.");
        AlertUtil.showErrorAlert("Failed to Delete Vehicle", "Failed to delete vehicle.", "An error occurred while deleting the vehicle.");
    }
    // Refresh the vehicle list
    updateVehicleList();
}

}