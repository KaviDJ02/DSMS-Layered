package com.drivesmart.dsms.controller;

import com.drivesmart.dsms.bo.BOFactory;
import com.drivesmart.dsms.bo.custom.*;
import com.drivesmart.dsms.db.DBConnection;
import com.drivesmart.dsms.dto.EmployeeDTO;
import com.drivesmart.dsms.dto.ResourcesDTO;
import com.drivesmart.dsms.dto.UserDTO;
import com.drivesmart.dsms.util.AlertUtil;
import com.drivesmart.dsms.util.UserPermissions;
import com.drivesmart.dsms.util.UserSession;
import com.drivesmart.dsms.util.Validate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class SettingsViewController {

    public AnchorPane supportPane;
    public AnchorPane aboutPane;
    public AnchorPane databasePane;
    public AnchorPane adminPane;
    public Label userCountLabel;

    public TextField usernameTF;
    public TextField empTF;
    public TextField pwTF;
    public ChoiceBox roleChoiceBox;
    public VBox usersVbox;
    public Label srcMsgLabel;
    public ListView resrcListView;
    public ChoiceBox packageChoiceBox;
    public TextField urlTF;
    public TextField titleTF;
    public Button addSrcBtn;

    @FXML
    private PasswordField ReNewPasswordTF;

    @FXML
    private AnchorPane accountPane;

    @FXML
    private Label emailMsgLabel;

    @FXML
    private PasswordField emailTF;

    @FXML
    private Label nameLabel;

    @FXML
    private AnchorPane navBarPane;

    @FXML
    private PasswordField newPasswordTF;

    @FXML
    private PasswordField oldPasswordTF;

    @FXML
    private Label positionLabel;

    @FXML
    private Label pwMsgLabel;


    UserSession userSession = UserSession.getInstance();

    SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);
    PackageBO packageBO = (PackageBO) BOFactory.getInstance().getBO(BOFactory.BOType.PACKAGE);
    ResourcesBO resourcesBO = (ResourcesBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESOURCES);


    public SettingsViewController() throws SQLException {
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        navBarPane.setVisible(true);
        changePane(accountPane);

        nameLabel.setText(employeeBO.getEmpName(userSession.getUserId()));
        positionLabel.setText(userSession.getUserRole());

        ArrayList<EmployeeDTO> employees = employeeBO.getAllEmployees();
        TextFields.bindAutoCompletion(empTF, employees);

        roleChoiceBox.getItems().addAll("admin", "employee");
        loadUserCards();
        setUserCount();

        packageChoiceBox.getItems().addAll(packageBO.getPackageNames());

        loadResourceCards();
    }

    @FXML
    void ChangePasswordOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String oldPassword = oldPasswordTF.getText();
        String newPassword = newPasswordTF.getText();
        String reNewPassword = ReNewPasswordTF.getText();

        if (!(oldPassword.isEmpty() || newPassword.isEmpty() || reNewPassword.isEmpty())) {
            String currentPassword = userBO.getPassword(userSession.getUserId());
            if (currentPassword.equals(oldPassword)) {
                if (newPassword.equals(reNewPassword)) {
                    if (userBO.changePassword(userSession.getUserId(), newPassword)) {
                        oldPasswordTF.clear();
                        newPasswordTF.clear();
                        ReNewPasswordTF.clear();
                        pwMsgLabel.setText("Password Changed Successfully");
                    } else {
                        pwMsgLabel.setText("Password Change Failed");
                    }
                } else {
                    pwMsgLabel.setText("Passwords do not match");
                    ReNewPasswordTF.clear();
                }
            } else {
                pwMsgLabel.setText("Incorrect old password");
            }
        } else {
            pwMsgLabel.setText("Please fill all the fields");
        }
    }

    public void changePane(AnchorPane pane){
        AnchorPane[] panes = {accountPane, adminPane,databasePane  , supportPane, aboutPane };
        for(AnchorPane p : panes){
            p.setVisible(false);
        }
        pane.setVisible(true);
    }

    public void accountBtnOnAction(ActionEvent actionEvent) {
        changePane(accountPane);
    }

    public void adminButtonOnAction(ActionEvent actionEvent) {
        if(!UserPermissions.isAdmin()){
            return;
        }

        changePane(adminPane);
    }

    public void databaseBtnOnAction(ActionEvent actionEvent) {
        changePane(databasePane);
    }

    public void supportBtnOnAction(ActionEvent actionEvent) {
        changePane(supportPane);
    }

    public void aboutBtnOnAction(ActionEvent actionEvent) {
        changePane(aboutPane);
    }

    public void changeEmailOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String email = emailTF.getText();

        if (!email.isEmpty()) {
            if (employeeBO.changeEmail(userSession.getUserRole(), email)) {
                emailTF.clear();
                emailMsgLabel.setText("Email Changed Successfully");
            } else {
                emailMsgLabel.setText("Email Change Failed");
            }
        } else {
            emailMsgLabel.setText("Please fill all the fields");
        }
    }

    public void addUserBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!UserPermissions.isAdmin()){
            return;
        }

        String username = usernameTF.getText();
        String empId = String.valueOf(employeeBO.getEmployeeId(empTF.getText()));
        String password = pwTF.getText();
        String role = roleChoiceBox.getValue().toString();

        //validate
        if (!Validate.validateTextField(usernameTF, "NotNullOrEmpty") ||
                !Validate.validateTextField(empTF, "NotNullOrEmpty") ||
                !Validate.validateTextField(pwTF, "Password") ||
                roleChoiceBox.getValue() == null) {
            return;
        }

            if (!userBO.isUserExist(username)) {
                if (userBO.addUser(username, password, role, empId)) {
                    usernameTF.clear();
                    empTF.clear();
                    pwTF.clear();
                    roleChoiceBox.setValue(null);
                    System.out.println("User Added Successfully");
                    AlertUtil.showSimpleAlert("User Addition", "User Added Successfully", "The user account has been successfully added.");
                } else {
                    System.out.println("User Addition Failed");
                }
            } else {
                System.out.println("User Already Exist");
                AlertUtil.showErrorAlert("User Addition", "User Already Exist", "The user account already exists.");
            }

        loadUserCards();
        setUserCount();
    }

    public void deactivateBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (userBO.deactivateUser(userSession.getUserId())) {
            System.out.println("User Deactivated");

            AlertUtil.showSimpleAlert("User Deactivation", "User Deactivated", "The user account has been successfully deactivated.");

            // Navigate to the login page
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("User Deactivation Failed");
            AlertUtil.showSimpleAlert("User Deactivation", "User Deactivation Failed", "The user account deactivation failed.");
        }
    }

    @FXML
    private void openLinkedIn() {
        openURL("https://www.linkedin.com");
    }

    @FXML
    private void openFacebook() {
        openURL("https://www.facebook.com");
    }

    @FXML
    private void openWhatsApp() {
        openURL("https://web.whatsapp.com");
    }

    @FXML
    private void openTelegram() {
        openURL("https://telegram.org");
    }

    @FXML
    private void openDiscord() {
        openURL("https://discord.com");
    }

    private void openURL(String url) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | IllegalArgumentException | URISyntaxException e) {
                System.err.println("Failed to open URL: " + url);
                e.printStackTrace();
            }
        } else {
            System.err.println("Desktop not supported on this platform.");
        }
    }
    private Node createUserCard(UserDTO user) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setStyle("-fx-padding: 10; -fx-background-color: #2b2b2b; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-insets: 5; -fx-border-radius: 5; -fx-border-color: #4a90e2; -fx-text-fill: #ffffff;");
        Label usernameLabel = new Label("Username: " + user.getUsername());
        Label roleLabel = new Label("Role: " + user.getRole());
        Label status = new Label("Status: " + user.getStatus());

        card.getChildren().addAll(usernameLabel, roleLabel, status);
        return card;
    }

    public void loadUserCards() {
        usersVbox.getChildren().clear();
        try {
            List<UserDTO> users = userBO.getAllUsers();
            for (UserDTO user : users) {
                Node userCard = createUserCard(user);
                usersVbox.getChildren().add(userCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setUserCount() throws SQLException, ClassNotFoundException {
        userCountLabel.setText(String.valueOf(userBO.getUserCount()));
    }

    public void addSrcBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String src = titleTF.getText();
        String url = urlTF.getText();
        int pkg = Integer.parseInt(packageBO.getPackageId(packageChoiceBox.getValue().toString()));

        if (!Validate.validateTextField(titleTF, "NotNullOrEmpty") ||
                !Validate.validateTextField(urlTF, "NotNullOrEmpty") ||
                packageChoiceBox.getValue() == null) {
            return;
        }

        ResourcesDTO resource = new ResourcesDTO(pkg, url, src);

        if (resourcesBO.saveResource(resource)) {
            titleTF.clear();
            urlTF.clear();
            packageChoiceBox.setValue(null);
            srcMsgLabel.setText("Resource Added Successfully");
        } else {
            srcMsgLabel.setText("Resource Addition Failed");
        }
        loadResourceCards();
    }

    private Node createResourceCard(ResourcesDTO resource) throws SQLException, ClassNotFoundException {
    VBox card = new VBox();
    card.setSpacing(10);
    card.setStyle("-fx-padding: 10; -fx-background-color: #2b2b2b; -fx-border-style: solid inside; -fx-border-width: 2; -fx-border-insets: 5; -fx-border-radius: 5; -fx-border-color: #4a90e2; -fx-text-fill: #ffffff;");

    Label titleLabel = new Label("Title: " + resource.getTitle());
    titleLabel.setStyle("-fx-text-fill: #ffffff;");

    // Retrieve the package name using the package ID
    String packageName = packageBO.getPackageId(String.valueOf(resource.getPackageId()));
    Label packageLabel = new Label("Package: " + packageName);
    packageLabel.setStyle("-fx-text-fill: #ffffff;");

    Button editButton = new Button("Edit");
    editButton.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: #ffffff;");
    editButton.setOnAction(event -> {
        try {
            editResource(resource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    });

    Button deleteButton = new Button("Delete");
    deleteButton.setStyle("-fx-background-color: #e24a4a; -fx-text-fill: #ffffff;");
    deleteButton.setOnAction(event -> {
        try {
            deleteResource(resource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    });

    HBox buttonBox = new HBox(10, editButton, deleteButton);
    card.getChildren().addAll(titleLabel, packageLabel, buttonBox);

    return card;
}

    private void loadResourceCards() {
        resrcListView.getItems().clear();
        try {
            List<ResourcesDTO> resources = resourcesBO.getAllResources();
            for (ResourcesDTO resource : resources) {
                Node resourceCard = createResourceCard(resource);
                resrcListView.getItems().add(resourceCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editResource(ResourcesDTO resource) throws SQLException {
        titleTF.setText(resource.getTitle());
        urlTF.setText(resource.getUrl());
//        packageChoiceBox.setValue(packageBO.get(resource.getPackageId()));

        addSrcBtn.setOnAction(event -> {
            try {
                updateResource(resource);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateResource(ResourcesDTO resource) throws SQLException, ClassNotFoundException {
        String title = titleTF.getText();
        String url = urlTF.getText();
        int pkg = Integer.parseInt(packageBO.getPackageId(packageChoiceBox.getValue().toString()));

        if (!Validate.validateTextField(titleTF, "NotNullOrEmpty") ||
                !Validate.validateTextField(urlTF, "NotNullOrEmpty") ||
                packageChoiceBox.getValue() == null) {
            return;
        }

        ResourcesDTO updatedResource = new ResourcesDTO(pkg, title, url);

        if (resourcesBO.updateResource(resource.getUrl(), updatedResource)) {
            titleTF.clear();
            urlTF.clear();
            packageChoiceBox.setValue(null);
            srcMsgLabel.setText("Resource Updated Successfully");
            loadResourceCards();
        } else {
            srcMsgLabel.setText("Resource Update Failed");
        }
    }

    private void deleteResource(ResourcesDTO resource) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Resource");
        alert.setHeaderText("Are you sure you want to delete this resource?");
        alert.setContentText("Title: " + resource.getTitle());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (resourcesBO.deleteResource(resource.getUrl())) {
//                AlertUtil.showSimpleAlert("Delete Resource", "Resource deleted successfully", "The resource has been deleted.");
                loadResourceCards();
            } else {
                AlertUtil.showErrorAlert("Delete Resource", "Resource deletion failed", "Failed to delete the resource.");
            }
        }
    }

}
