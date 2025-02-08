package com.drivesmart.dsms.controller;

import com.drivesmart.dsms.bo.BOFactory;
import com.drivesmart.dsms.bo.custom.EmployeeBO;
import com.drivesmart.dsms.bo.custom.PackageBO;
import com.drivesmart.dsms.bo.custom.SessionBO;
import com.drivesmart.dsms.dto.PackageDTO;
import com.drivesmart.dsms.dto.PackageEnrollmentDTO;
import com.drivesmart.dsms.util.UserPermissions;
import com.drivesmart.dsms.util.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PackageViewController {

    public Button addNewPackageBtn;
    @FXML
    private Label totalPackageCount;

    @FXML
    private Label mostPopularPackage;

    @FXML
    private Label leastPopularPackage;

    @FXML
    private AnchorPane addPackageView;

    @FXML
    private AnchorPane mainView;

    @FXML
    private TextField pkgNameTF;

    @FXML
    private TextField durationTF;

    @FXML
    private TextField priceTF;

    @FXML
    private TextArea descriptionTF;

    @FXML
    private AnchorPane enrollPane;

    @FXML
    private TextField studentNameTF;

    @FXML
    private Label packageNameLabel;

    @FXML
    private VBox courseContainer;

    private PackageDTO selectedPackage;


    PackageBO packageBO = (PackageBO) BOFactory.getInstance().getBO(BOFactory.BOType.PACKAGE);

    public PackageViewController() throws SQLException {
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        loadCourses();
        mainView.setVisible(true);
        addPackageView.setVisible(false);
        enrollPane.setVisible(false);
        setTotalPackageLabel();
        addNewPackageBtn.setText("Add New Package");
        setPackageFavorite();

        List<String> students = packageBO.getStudentsNames();
        TextFields.bindAutoCompletion(studentNameTF, students);
    }

    private void loadCourses() throws SQLException, ClassNotFoundException {
        courseContainer.getChildren().clear();
        List<PackageDTO> courses = packageBO.getAllPackages();
        for (PackageDTO course : courses) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CourseCard.fxml"));
                AnchorPane courseCard = loader.load();

                PackageCardController controller = loader.getController();
                controller.setCourseName(course.getPackageName());
                controller.setCourseDescription(course.getDescription());
                controller.setPackagePrice("Rs " + course.getPrice());
                controller.setDurationLabel(course.getDuration() + " Days");

                controller.setEnrollButtonAction(() -> enrollInCourse(course));
                controller.setEditButtonAction(() -> {
                    try {
                        editCourse(course);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                controller.setDeleteButtonAction(() -> {
                    try {
                        deleteCourse(course);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });

                courseContainer.getChildren().add(courseCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void enrollInCourse(PackageDTO course) {
        System.out.println("Enrolled in " + course.getPackageName());
        mainView.setVisible(false);
        enrollPane.setVisible(true);
        packageNameLabel.setText(course.getPackageName());
    }

    private void setTotalPackageLabel() throws SQLException, ClassNotFoundException {
        int count = packageBO.getPackageCount();
        totalPackageCount.setText(String.valueOf(count));
    }

    @FXML
    private void addPackageOnAction(ActionEvent actionEvent) {
        if(!UserPermissions.isAdmin()){
            return;
        }
        addPackageView.setVisible(true);
        mainView.setVisible(false);
    }

    SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);

    @FXML
    private void enrollBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PackageEnrollmentDTO packageEnrollmentDTO = new PackageEnrollmentDTO();

        int studentId = sessionBO.getStudentId(studentNameTF.getText());
        int packageId = Integer.parseInt((packageBO.getPackageId(packageNameLabel.getText())));
        String date = java.time.LocalDate.now().toString();

        packageEnrollmentDTO.setStudentId(studentId);
        packageEnrollmentDTO.setPackageId(packageId);
        packageEnrollmentDTO.setEnrollmentDate(date);

        if (packageBO.enrollPackage(packageEnrollmentDTO)) {
            System.out.println("Enrollment Successfully");
        } else {
            System.out.println("Enrollment Failed");
        }
    }

    @FXML
    private void backOnAction(MouseEvent mouseEvent) {
        pkgNameTF.clear();
        descriptionTF.clear();
        durationTF.clear();
        priceTF.clear();

        mainView.setVisible(true);
        enrollPane.setVisible(false);
        addPackageView.setVisible(false);
    }

    String cName = null;

    private void editCourse(PackageDTO course) throws SQLException {
        if(!UserPermissions.isAdmin()){
            return;
        }

        System.out.println("Edit " + course.getPackageName());
        selectedPackage = course;
        packageNameLabel.setText(course.getPackageName());
        mainView.setVisible(false);
        addPackageView.setVisible(true);
        enrollPane.setVisible(false);

        pkgNameTF.setText(course.getPackageName());
        descriptionTF.setText(course.getDescription());
        durationTF.setText(String.valueOf(course.getDuration()));
        priceTF.setText(String.valueOf(course.getPrice()));

        addNewPackageBtn.setText("Update Package");
        cName = course.getPackageName();
        System.out.println(cName);
    }

    private void deleteCourse(PackageDTO course) throws SQLException, ClassNotFoundException {
        if(!UserPermissions.isAdmin()){
            return;
        }

        System.out.println("Delete " + course.getPackageName());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Package");
        alert.setHeaderText("Are you sure you want to delete " + course.getPackageName() + "?");
        alert.setContentText("This action cannot be undone.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            if (packageBO.deletePackage(packageBO.getPackageId(course.getPackageName()))) {
                System.out.println("Package Deleted Successfully");
            } else {
                System.out.println("Package Deletion Failed");
            }
            loadCourses();
        }
        setTotalPackageLabel();
        setPackageFavorite();
    }

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    @FXML
    private void addPackageBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PackageDTO packageDTO = new PackageDTO();

        packageDTO.setPackageName(pkgNameTF.getText());
        packageDTO.setDescription(descriptionTF.getText());
        packageDTO.setDuration(Integer.parseInt(durationTF.getText()));
        packageDTO.setPrice(Double.parseDouble(priceTF.getText()));
        packageDTO.setEmployeeId(employeeBO.getEmpId(UserSession.getInstance().getUserId()));

        if (selectedPackage != null) {
            packageDTO.setPackageId(selectedPackage.getPackageId());
            if (packageBO.updatePackage(packageDTO)) {
                System.out.println("Package Updated Successfully");
            } else {
                System.out.println("Package Updating Failed");
            }
            selectedPackage = null;
        } else {
            if (packageBO.savePackage(packageDTO)) {
                System.out.println("Package Added Successfully");
            } else {
                System.out.println("Package Adding Failed");
            }
        }

        pkgNameTF.clear();
        descriptionTF.clear();
        durationTF.clear();
        priceTF.clear();

        loadCourses();
        mainView.setVisible(true);
        addPackageView.setVisible(false);
        setTotalPackageLabel();
    }

    public void setPackageFavorite() throws SQLException, ClassNotFoundException {
        if(packageBO.getMostPopularPackage() == null){
            mostPopularPackage.setText("No Data");
        }else {
            mostPopularPackage.setText(packageBO.getMostPopularPackage());
        }

        if(packageBO.getLeastPopularPackage() == null){
            leastPopularPackage.setText("No Data");
        }else {
            leastPopularPackage.setText(packageBO.getLeastPopularPackage());
        }

    }

}