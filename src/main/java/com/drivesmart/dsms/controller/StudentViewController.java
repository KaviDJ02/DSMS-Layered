package com.drivesmart.dsms.controller;

import com.drivesmart.dsms.bo.BOFactory;
import com.drivesmart.dsms.bo.custom.EmailBO;
import com.drivesmart.dsms.bo.custom.SessionBO;
import com.drivesmart.dsms.bo.custom.StudentBO;
import com.drivesmart.dsms.dto.EmailDTO;
import com.drivesmart.dsms.dto.StudentDTO;
import com.drivesmart.dsms.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class StudentViewController {

    @FXML
    public Label errorLabel;

    @FXML
    public Button backBtn;

    @FXML
    public Button studentAddBtn;

    @FXML
    public TextField nicTF;

    @FXML
    public TextField addressTF;

    @FXML
    public DatePicker bodTF;

    @FXML
    public TextField phoneTF;

    @FXML
    public TextField emailTF;

    @FXML
    public TextField nameTF;

    @FXML
    public Label MainLabel;

    @FXML
    public AnchorPane studentAdd;

    @FXML
    public Label enrolledStudentLabel;

    @FXML
    public AnchorPane studentDash;

    @FXML
    public Label totalStudentLabel;

    @FXML
    public ListView<HBox> studentListiew;
    public ChoiceBox statusChoiceBox;

    @FXML
    private TextField searchField;

    private ObservableList<StudentDTO> studentData;

    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);

    private static final String CARD_STYLE = """
        -fx-background-color: #2e3b4e;
        -fx-border-color: #007bff;
        -fx-border-width: 2;
        -fx-border-radius: 8;
        -fx-background-radius: 8;
        -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.3), 10, 0, 0, 5);
    """;

    private static final String LABEL_STYLE = """
        -fx-text-fill: #e0e0e0;
        -fx-font-size: 14px;
        -fx-font-weight: bold;
    """;

    private static final String BUTTON_STYLE_TEMPLATE = """
        -fx-background-color: %s;
        -fx-text-fill: #ffffff;
        -fx-font-size: 14px;
        -fx-padding: 8 15;
        -fx-background-radius: 5;
        -fx-border-radius: 5;
        -fx-cursor: hand;
    """;

    public StudentViewController() throws SQLException {
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        studentDash.setVisible(true);
        studentAdd.setVisible(false);

        statusChoiceBox.setItems(FXCollections.observableArrayList("active", "completed", "cancelled"));

        loadStudentData();
        setupSearchFeature();
        setEnrolledStudentLabel();
    }

    private void loadStudentData() {
        try {
            List<StudentDTO> studentList = studentBO.getAllStudents();
            studentData = FXCollections.observableArrayList(studentList);
            displayStudentCards(studentData);
            setTotalStudentLabel(studentList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void displayStudentCards(List<StudentDTO> studentList) {
        ObservableList<HBox> studentCards = FXCollections.observableArrayList();
        for (StudentDTO student : studentList) {
            HBox card = createStudentCard(student);
            studentCards.add(card);
        }
        studentListiew.setItems(studentCards);
    }

    private HBox createStudentCard(StudentDTO student) {
        HBox card = new HBox(10);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15));
        card.setSpacing(20);
        card.setStyle(CARD_STYLE);

        VBox details = new VBox(8);
        details.setAlignment(Pos.TOP_LEFT);
        details.getChildren().addAll(
                createStyledLabel("Name: " + student.getName()),
                createStyledLabel("Email: " + student.getEmail()),
                createStyledLabel("Phone: " + student.getPhone()),
                createStyledLabel("Address: " + student.getAddress()),
                createStyledLabel("Birthday: " + student.getBirthday()),
                createStyledLabel("NIC: " + student.getNic())
        );

        VBox buttons = new VBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button updateBtn = createStyledButton("Update", "#28a745");
        updateBtn.setOnAction(e -> {
            try {
                updateStudent(student);
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        Button deleteBtn = createStyledButton("Delete", "#dc3545");
        deleteBtn.setOnAction(e -> deleteStudent(student));
        buttons.getChildren().addAll(updateBtn, deleteBtn);

        card.getChildren().addAll(details, buttons);
        HBox.setHgrow(details, Priority.ALWAYS);
        return card;
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle(LABEL_STYLE);
        return label;
    }

    private Button createStyledButton(String text, String backgroundColor) {
        Button button = new Button(text);
        button.setStyle(String.format(BUTTON_STYLE_TEMPLATE, backgroundColor));
        button.setOnMouseEntered(e -> button.setStyle(String.format(BUTTON_STYLE_TEMPLATE + """
            -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.3), 10, 0, 0, 5);
        """, backgroundColor)));
        button.setOnMouseExited(e -> button.setStyle(String.format(BUTTON_STYLE_TEMPLATE, backgroundColor)));
        return button;
    }

    private void updateStudent(StudentDTO student) throws SQLException, ClassNotFoundException {
        studentDash.setVisible(false);
        studentAdd.setVisible(true);

        statusChoiceBox.setDisable(false);

        nameTF.setText(student.getName());
        emailTF.setText(student.getEmail());
        phoneTF.setText(student.getPhone());
        addressTF.setText(student.getAddress());
        bodTF.setValue(LocalDate.parse(student.getBirthday()));
        nicTF.setText(student.getNic());
        statusChoiceBox.setValue(studentBO.getStatus(student.getNic()));

        MainLabel.setText("Update Student");
        nicTF.setDisable(true);

        studentAddBtn.setOnAction(event -> {
            String name = nameTF.getText();
            String email = emailTF.getText();
            String phone = phoneTF.getText();
            String address = addressTF.getText();
            LocalDate bod = bodTF.getValue();
            String nic = nicTF.getText();
            String status = (String) statusChoiceBox.getValue();


            if (!Validate.validateTextField(nameTF, "Name") ||
                    !Validate.validateTextField(emailTF, "Email") ||
                    !Validate.validateTextField(phoneTF, "SriLankanPhoneNumber") ||
                    bod == null ||
                    !Validate.validateTextField(addressTF, "NotNullOrEmpty") ||
                    !Validate.validateTextField(nicTF, "NIC")) {
                errorLabel.setText("Please enter valid data");
                return;
            }

            StudentDTO updatedStudent = new StudentDTO(name, email, phone, address, bod.toString(), nic);

            try {
                if (studentBO.updateStudent(updatedStudent)) {
                    studentBO.setStatus(nic, status);
                    AlertUtil.showSimpleAlert("Success", "Student updated successfully", "Student updated successfully");
                    studentDash.setVisible(true);
                    studentAdd.setVisible(false);
                    loadStudentData();
                } else {
                    AlertUtil.showSimpleAlert("Error", "Student update failed", "Student update failed");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void deleteStudent(StudentDTO student) {
        try {
            if(studentBO.deleteStudent(student.getNic())){
                AlertUtil.showSimpleAlert("Success", "Student deleted successfully", "Student deleted successfully");
            }else {
                AlertUtil.showSimpleAlert("Error", "Student deletion failed", "Student deletion failed");
            }
            loadStudentData();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTotalStudentLabel(int total) {
        totalStudentLabel.setText(String.valueOf(total));
    }

    private void setupSearchFeature() {
        FilteredList<StudentDTO> filteredData = new FilteredList<>(studentData, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(student -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                return student.getName().toLowerCase().contains(searchKeyword) ||
                        student.getEmail().toLowerCase().contains(searchKeyword) ||
                        student.getPhone().toLowerCase().contains(searchKeyword) ||
                        student.getAddress().toLowerCase().contains(searchKeyword) ||
                        student.getBirthday().toLowerCase().contains(searchKeyword) ||
                        student.getNic().toLowerCase().contains(searchKeyword);
            });

            displayStudentCards(filteredData);
        });
    }

    public void addBtnOnAction(ActionEvent actionEvent) {
        statusChoiceBox.setValue("Active");
        statusChoiceBox.setDisable(true);

        studentDash.setVisible(false);
        studentAdd.setVisible(true);
    }

    EmailBO emailBO = (EmailBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMAIL);

    public void studentAddBtnonAction(ActionEvent actionEvent) {
        String name = nameTF.getText();
        String email = emailTF.getText();
        String phone = phoneTF.getText();
        String address = addressTF.getText();
        LocalDate bod = bodTF.getValue();
        String nic = nicTF.getText();

        if (!Validate.validateTextField(nameTF, "Name") ||
                !Validate.validateTextField(emailTF, "Email") ||
                !Validate.validateTextField(phoneTF, "SriLankanPhoneNumber") ||
                bod == null ||
                !Validate.validateTextField(addressTF, "NotNullOrEmpty") ||
                !Validate.validateTextField(nicTF, "NIC")) {
            errorLabel.setText("Please enter valid data");
            return;
        }

        StudentDTO studentDTO = new StudentDTO(name, email, phone, address, bod.toString(), nic);

        try {
            studentBO.saveStudent(studentDTO);
            loadStudentData();

            AlertUtil.showSimpleAlert("Success", "Student added successfully", "Student added successfully");

            new Thread(() -> {
                try {
                    String content = new EmailTemplateUtil().getTemplateContent("WelcomeStudentEmail.html");
                    content = content.replace("{Student Name}", name);
                    JavaMailUtil.sendMail(email, "Welcome to DriveSmart", content);

                    SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);
                    emailBO.saveEmail(new EmailDTO("Welcome to DriveSmart", "WelcomeStudentEmail", String.valueOf(new Date()), sessionBO.getStudentId(name), 1));
                } catch (MessagingException | SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).start();

            studentDash.setVisible(true);
            studentAdd.setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void backBtnOnAction(ActionEvent actionEvent) {
        nameTF.clear();
        emailTF.clear();
        phoneTF.clear();
        addressTF.clear();
        bodTF.setValue(null);
        nicTF.clear();

        studentDash.setVisible(true);
        studentAdd.setVisible(false);
    }

    void setEnrolledStudentLabel() throws SQLException, ClassNotFoundException {
        enrolledStudentLabel.setText(String.valueOf(studentBO.getActiveStudentCount()));
    }

}