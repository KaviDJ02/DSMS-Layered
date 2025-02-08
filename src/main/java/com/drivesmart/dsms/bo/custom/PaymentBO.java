package com.drivesmart.dsms.bo.custom;

import com.drivesmart.dsms.bo.SuperBO;
import com.drivesmart.dsms.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException;
    boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    boolean updatePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;
    boolean deletePayment(String id) throws SQLException, ClassNotFoundException;
    PaymentDTO searchPayment(String id) throws SQLException, ClassNotFoundException;
    String getStudentPhone(int studentId) throws SQLException, ClassNotFoundException;
    String getTodayPayments(String format) throws SQLException, ClassNotFoundException;
    double getDueAmount(int studentId) throws SQLException, ClassNotFoundException;
    int getPendingCount() throws SQLException, ClassNotFoundException;
}