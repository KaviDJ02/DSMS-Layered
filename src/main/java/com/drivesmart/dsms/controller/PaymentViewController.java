package com.drivesmart.dsms.controller;

import com.drivesmart.dsms.bo.BOFactory;
import com.drivesmart.dsms.bo.custom.PaymentBO;
import com.drivesmart.dsms.bo.custom.SessionBO;
import com.drivesmart.dsms.dto.PaymentDTO;
import com.drivesmart.dsms.util.AlertUtil;
import com.drivesmart.dsms.util.WhatsappUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.TextFields;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class PaymentViewController {

    @FXML
    private TextField studentField;
    @FXML
    private TextField amountReceivedField;
    @FXML
    private VBox paymentVbox; // Use VBox to hold the cards
    @FXML
    public Label receivedFundLabel;
    @FXML
    private Label latePaymentCount;

    private ObservableList<PaymentDTO> paymentData = FXCollections.observableArrayList();

    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);
    SessionBO sessionBO = (SessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);

    public PaymentViewController() throws SQLException {
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        try {
            loadPayments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateReceivedFundLabel();
        updateLatePaymentCount();

        List<String> students = sessionBO.getStudentsNames();
        TextFields.bindAutoCompletion(studentField, students);
    }

    @FXML
    public void handleMarkPaymentReceived(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String studentName = studentField.getText();
        double amountReceived = Double.parseDouble(amountReceivedField.getText());

        int studentId = sessionBO.getStudentId(studentName);

        PaymentDTO newPayment = new PaymentDTO(
                studentId,
                amountReceived,
                getDueAmount(studentId, amountReceived),
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                getStatus(studentId, amountReceived)
        );

        try {
            if (paymentBO.savePayment(newPayment)) {
                System.out.println("Payment saved successfully");
                AlertUtil.showSimpleAlert("Payment Saved", "Payment saved successfully", "Payment saved successfully");

//                TODO: another mail for student who has no Due Amount

                new Thread(() -> {
                    try {
//                        String content = new EmailTemplateUtil().getTemplateContent("PaymentReceipt.html");
//                        content = content.replace("{studentName}", paymentModel.getStudentName(newPayment.getStudentId()))
//                                .replace("{amount}", String.valueOf(newPayment.getAmount()))
//                                .replace("{receivedDate}", newPayment.getReceivedDate())
//                                .replace("{dueAmount}", String.valueOf(newPayment.getAmountDue()));
//                        JavaMailUtil.sendMail(paymentModel.getStudentEmail(newPayment.getStudentId()), "Payment Receipt", content);
//
//                        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//
//
//                        EmailModel.saveEmail(new EmailDTO("Welcome to DriveSmart", "WelcomeStudentEmail", formattedDate, newPayment.getStudentId(), 2));

                        boolean isSend = WhatsappUtil.sendMessage(paymentBO.getStudentPhone(newPayment.getStudentId()), "Thank you for your payment of Rs " + newPayment.getAmount() + " on " + newPayment.getReceivedDate() + ". Your due amount is Rs " + newPayment.getAmountDue() + ". DriveSmart");

                        System.out.println("Whatsapp message sent: " + isSend);

                    } catch (MessagingException | SQLException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).start();


            } else {
                System.out.println("Error: Payment not saved");
                AlertUtil.showErrorAlert("Error", "Payment not saved", "Payment not saved");
            }
            paymentData.add(newPayment);
            addPaymentCard(newPayment); // Add new payment card
            updateReceivedFundLabel();
            updateLatePaymentCount();
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPayments() throws SQLException, ClassNotFoundException {
        paymentData.setAll(paymentBO.getAllPayments());
        paymentData.forEach(this::addPaymentCard); // Add cards for each payment
    }

    private void addPaymentCard(PaymentDTO payment) {
        HBox card = new HBox();
        card.setStyle("-fx-background-color: #2b2b2b; -fx-padding: 5; -fx-border-color: #3c3f41; -fx-border-radius: 5; -fx-background-radius: 5; -fx-spacing: 10;");

        Label studentIdLabel = new Label("Student ID: " + payment.getStudentId());
        studentIdLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #a9b7c6;");

        Label paidAmountLabel = new Label("Paid Amount: " + payment.getAmount());
        paidAmountLabel.setStyle("-fx-text-fill: #a9b7c6;");

        Label receivedDateLabel = new Label("Received Date: " + payment.getReceivedDate());
        receivedDateLabel.setStyle("-fx-text-fill: #a9b7c6;");

        Label dueAmountLabel = new Label("Due Amount: " + payment.getAmountDue());
        dueAmountLabel.setStyle("-fx-text-fill: #a9b7c6;");

        Label statusLabel = new Label("Status: " + payment.getStatus());
        statusLabel.setStyle("-fx-text-fill: #a9b7c6;");

        card.getChildren().addAll(
                studentIdLabel,
                paidAmountLabel,
                receivedDateLabel,
                dueAmountLabel,
                statusLabel
        );

        paymentVbox.getChildren().add(card);
    }

    private void updateReceivedFundLabel() throws SQLException, ClassNotFoundException {
        receivedFundLabel.setText("Rs "+paymentBO.getTodayPayments(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
    }

    private void updateLatePaymentCount() throws SQLException, ClassNotFoundException {
        latePaymentCount.setText(String.valueOf(paymentBO.getPendingCount()));
    }

    public double getDueAmount(int studentId, double amount) throws SQLException, ClassNotFoundException {

        double dueAmount = paymentBO.getDueAmount(studentId);

        if(dueAmount != -1) {
            return dueAmount - amount;
        }
        System.out.println("Error: Due amount not found");
        return 0;

    }

    public String getStatus(int studentId, double amountReceived) throws SQLException, ClassNotFoundException {
        {
            double amountDue = getDueAmount(studentId, amountReceived);
            if (amountDue == 0) {
                return "paid";
            } else if (amountDue > 0) {
                return "pending";
            } else {
                return "overdue";
            }
        }
    }

    private void clearFields() {
        studentField.clear();
        amountReceivedField.clear();
    }
}