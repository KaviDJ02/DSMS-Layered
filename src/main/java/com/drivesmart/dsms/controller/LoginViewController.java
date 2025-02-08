package com.drivesmart.dsms.controller;

import com.drivesmart.dsms.bo.BOFactory;
import com.drivesmart.dsms.bo.custom.UserBO;
import com.drivesmart.dsms.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.Stack;

public class LoginViewController {

    @FXML
    private Label RPmsgLabel, enterCodeMsgLabel, forgotPWLabel, forgotPWMsgLabel, msgLabel;

    @FXML
    private AnchorPane backLoginPane, enterCodePane, forgotPWPane, loginPane, newPasswordPane;

    @FXML
    private TextField enterCodeTextField, newPWTextField, reNewPWTextField, usernameInput, usernameTextAreaFP;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private FontIcon feedbackIcon;

    private final ForgotPWCodeGenerate forgotPWCodeGenerate = new ForgotPWCodeGenerate();
    private final Stack<AnchorPane> paneStack = new Stack<>();
    private String code = "";

    public LoginViewController() throws SQLException {
    }

    @FXML
    void initialize() {
        paneStack.push(loginPane);
        showMessage("Welcome to DriveSmart", "bi-emoji-smile-fill");
        loginPane.setVisible(true);

        passwordInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginBtnOnAction(new ActionEvent());
            }
        });
    }

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    public void loginBtnOnAction(ActionEvent actionEvent) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Username or Password cannot be empty", "bi-emoji-expressionless-fill");
            return;
        }

        try {
            if (userBO.isUserExist(username)) {
                if (userBO.verifyLogin(username, password)) {
                    UserSession.getInstance().setUserRole(userBO.getUserRole(username));
                    UserSession.getInstance().setUserId(userBO.getUserId(username));
                    System.out.println("User Role: " + UserSession.getInstance().getUserRole());
                    System.out.println("User ID: " + UserSession.getInstance().getUserId());
                    paneStack.clear();
                    showMessage("Login Successful", "bi-emoji-laughing-fill");
                    loadMainDashboard();
                } else {
                    showMessage("Invalid Password", "bi-emoji-frown-fill");
                }
            } else {
                showMessage("User not found", "bi-emoji-neutral-fill");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("An error occurred. Please try again.", "bi-exclamation-circle-fill");
        }
    }

    private void loadMainDashboard() throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainDashView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) msgLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    public void sendCodeButtonOnAction(ActionEvent actionEvent) {
        forgotPWMsgLabel.setText("Wait . . .");
        String username = usernameTextAreaFP.getText();
        code = forgotPWCodeGenerate.generateCode();

        if (!username.isEmpty()) {
            try {
                if (userBO.isUserExist(username)) {
                    forgotPWMsgLabel.setText("Sending . . .");
                    String mail = userBO.getUserMail(username);
                    JavaMailUtil.sendMail(mail, "Password Reset Code - DriveSmart dsms", EmailTemplateUtil.getPasswordResetEmailContent(code));
                    enterCodeMsgLabel.setText("Code sent to " + mail);
                    System.out.println("Code: " + code);
                    System.out.println("Mail: " + mail);
                    switchPane(forgotPWPane, enterCodePane);
                } else {
                    forgotPWMsgLabel.setText("User not found");
                }
            } catch (SQLException | MessagingException | ClassNotFoundException e) {
                e.printStackTrace();
                forgotPWMsgLabel.setText("An error occurred. Please try again.");
            }
        } else {
            forgotPWMsgLabel.setText("Username cannot be empty");
        }
    }

    @FXML
    void EnterCodeButtonOnAction(ActionEvent event) {
        String codeInput = enterCodeTextField.getText();
        if (codeInput.equals(code)) {
            code = "";
            System.out.println("Code verified");
            switchPane(enterCodePane, newPasswordPane);
            RPmsgLabel.setText("Don't forget this time :)");
        } else {
            enterCodeMsgLabel.setText("Invalid code");
        }
    }

    @FXML
    void backLoginOnAction(ActionEvent event) {
        loginPane.setVisible(true);
        backLoginPane.setVisible(false);
        paneStack.clear();
    }

    @FXML
    void backOnAction(MouseEvent event) {
        if (!paneStack.isEmpty()) {
            AnchorPane currentPane = paneStack.pop();
            currentPane.setVisible(false);
            if (!paneStack.isEmpty()) {
                AnchorPane previousPane = paneStack.peek();
                previousPane.setVisible(true);
            }
        }
    }

    @FXML
    void forgotPWMouseOnClick(MouseEvent event) {
        switchPane(loginPane, forgotPWPane);
    }

    @FXML
    void resetPasswodOnAction(ActionEvent event) {
        String newPassword = newPWTextField.getText();
        String reNewPassword = reNewPWTextField.getText();

        if (newPassword.isEmpty() || reNewPassword.isEmpty()) {
            RPmsgLabel.setText("Password cannot be empty");
            return;
        }

        if (!newPassword.equals(reNewPassword)) {
            RPmsgLabel.setText("Passwords do not match");
            return;
        }

        try {
            userBO.updatePassword(usernameTextAreaFP.getText(), newPassword);
            RPmsgLabel.setText("Password updated successfully");
            switchPane(newPasswordPane, backLoginPane);
        } catch (SQLException e) {
            e.printStackTrace();
            RPmsgLabel.setText("An error occurred. Please try again.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMessage(String message, String iconLiteral) {
        msgLabel.setText(message);
        feedbackIcon.setIconLiteral(iconLiteral);
        feedbackIcon.setVisible(true);
    }

    private void switchPane(AnchorPane currentPane, AnchorPane nextPane) {
        paneStack.push(nextPane);
        currentPane.setVisible(false);
        nextPane.setVisible(true);
    }
}