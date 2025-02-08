package com.drivesmart.dsms.controller;

import com.drivesmart.dsms.util.CardUtil;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DashBoardViewController {

    @FXML
    private Label courseLabel;

    @FXML
    private AnchorPane coursesCard;

    @FXML
    private Label currentStudentLabel;

    @FXML
    private AnchorPane currentStudentsCard;

    @FXML
    private AnchorPane dashboardAP;

    @FXML
    private Label employeeLabel;

    @FXML
    private AnchorPane employeesCard;

    @FXML
    private Label instructorLabel;

    @FXML
    private AnchorPane instructorsCard;

    @FXML
    private Label latePaymentLabel;

    @FXML
    private AnchorPane latePaymentsCard;

    @FXML
    private Label receivedPaymentLabel;

    @FXML
    private AnchorPane receivedPaymentsCard;

    @FXML
    private Label sentEmailLabel;

    @FXML
    private AnchorPane sentEmailsCard;

    @FXML
    private Label todaysSessionLabel;

    @FXML
    private AnchorPane todaysSessionsCard;

    @FXML
    private Label vehicleLabel;

    @FXML
    private AnchorPane vehiclesCard;

    @FXML
    void initialize() {
        applyFadeTransition(currentStudentsCard, 1, 0);
        applyFadeTransition(instructorsCard, 1, 100);
        applyFadeTransition(vehiclesCard, 1, 200);
        applyFadeTransition(coursesCard, 1, 100);
        applyFadeTransition(todaysSessionsCard, 1, 200);
        applyFadeTransition(sentEmailsCard, 1, 300);
        applyFadeTransition(employeesCard, 1, 200);
        applyFadeTransition(receivedPaymentsCard, 1, 300);
        applyFadeTransition(latePaymentsCard, 1, 400);

        setDashboardCards();
    }


    private void applyFadeTransition(AnchorPane card, double durationInSeconds, int delayInMillis) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds), card);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setDelay(Duration.millis(delayInMillis));
        fadeIn.play();
    }

    private void setTransitions() {
        applyFadeTransition(currentStudentsCard, 1, 0);
        applyFadeTransition(instructorsCard, 1, 100);
        applyFadeTransition(vehiclesCard, 1, 200);
        applyFadeTransition(coursesCard, 1, 100);
        applyFadeTransition(todaysSessionsCard, 1, 200);
        applyFadeTransition(sentEmailsCard, 1, 300);
        applyFadeTransition(employeesCard, 1, 200);
        applyFadeTransition(receivedPaymentsCard, 1, 300);
        applyFadeTransition(latePaymentsCard, 1, 400);
    }

    public void setDashboardCards() {
        try {
            int courseCount = CardUtil.getRowCount("SELECT * FROM package");
            int instructorCount = CardUtil.getRowCount("SELECT * FROM employee WHERE position = 'instructor'");
            int vehicleCount = CardUtil.getRowCount("SELECT * FROM vehicle");
            int studentCount = CardUtil.getRowCount("SELECT * FROM student");
            int sessionCount = CardUtil.getRowCount("SELECT * FROM session");
            int emailCount = CardUtil.getRowCount("SELECT * FROM email");
            int employeeCount = CardUtil.getRowCount("SELECT * FROM employee");
            int receivedPaymentCount = CardUtil.getRowCount("SELECT * FROM payment WHERE status = 'paid'");
            int latePaymentCount = CardUtil.getRowCount("SELECT COUNT(DISTINCT student_id) AS pending_students_count FROM payment WHERE status = 'pending';");

            courseLabel.setText(String.valueOf(courseCount));
            instructorLabel.setText(String.valueOf(instructorCount));
            vehicleLabel.setText(String.valueOf(vehicleCount));
            currentStudentLabel.setText(String.valueOf(studentCount));
            todaysSessionLabel.setText(String.valueOf(sessionCount));
            sentEmailLabel.setText(String.valueOf(emailCount));
            employeeLabel.setText(String.valueOf(employeeCount));
            receivedPaymentLabel.setText(String.valueOf(receivedPaymentCount));
            latePaymentLabel.setText(String.valueOf(latePaymentCount));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}


