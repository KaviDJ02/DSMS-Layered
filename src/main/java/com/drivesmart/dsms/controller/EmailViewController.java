package com.drivesmart.dsms.controller;

import com.drivesmart.dsms.bo.BOFactory;
import com.drivesmart.dsms.bo.custom.EmailBO;
import com.drivesmart.dsms.bo.custom.ResourcesBO;
import com.drivesmart.dsms.bo.custom.SessionBO;
import com.drivesmart.dsms.bo.custom.StudentBO;
import com.drivesmart.dsms.util.EmailTemplateUtil;
import com.drivesmart.dsms.util.JavaMailUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import org.controlsfx.control.textfield.TextFields;
import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.List;

public class EmailViewController {

    @FXML
    public AnchorPane mainVew;
    public TextField templatesTF;
    public TextField studentNameTF;
    public TextField filesTF;
    @FXML
    public Label totalResrcCount;
    @FXML
    private Label totalSentMailsCount;
    @FXML
    private WebView webView;

    EmailTemplateUtil emailTemplateUtil = new EmailTemplateUtil();

    EmailBO emailBO = (EmailBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMAIL);
    ResourcesBO resourcesBO = (ResourcesBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESOURCES);
    SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);
    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);

    public EmailViewController() throws SQLException {
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        List<String> templates = emailTemplateUtil.getTemplates();
        TextFields.bindAutoCompletion(templatesTF, templates);

        List<String> students = sessionBO.getStudentsNames();
        TextFields.bindAutoCompletion(studentNameTF, students);

        List<String> packNames = resourcesBO.getTitleNames();
        TextFields.bindAutoCompletion(filesTF, packNames);

        totalResrcCount.setText(String.valueOf(resourcesBO.getTitleNames().size()));
        setEmailCount();



    }

    public void sendBtnOnAction(ActionEvent actionEvent) throws SQLException, MessagingException, ClassNotFoundException {
        String studentEmail = studentBO.getStudentEmail(studentNameTF.getText());
        String url = resourcesBO.getResourceUrl(filesTF.getText());

        if(studentNameTF.getText().isEmpty() || filesTF.getText().isEmpty()) {
            System.out.println("Please fill all the fields.");
        }
        else {
            String content = emailTemplateUtil.getTemplateContent("Resource Template.html");
            content = content.replace("{url}", url);

            JavaMailUtil.sendMail(studentEmail, "DriveSmart - Resource", content);

            System.out.println("Email sent to: " + studentEmail);
        }
    }

    public void viewTempBtnOnAction(ActionEvent actionEvent) {
        String template = templatesTF.getText();

        if (template.isEmpty()) {
            System.out.println("No template selected.");
        }else {
            String content = emailTemplateUtil.getTemplateContent(template);
            webView.getEngine().loadContent(content);
        }
    }

    public void setEmailCount() throws SQLException, ClassNotFoundException {
        totalSentMailsCount.setText(String.valueOf(emailBO.getEmailCount()));

    }
}