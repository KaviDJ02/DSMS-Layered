package com.drivesmart.dsms.dao.custom;

import com.drivesmart.dsms.dao.CrudDAO;
import com.drivesmart.dsms.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentDAO extends CrudDAO<Payment> {
    ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException;
    boolean save(Payment payment) throws SQLException, ClassNotFoundException;
    boolean update(Payment payment) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    Payment search(String id) throws SQLException, ClassNotFoundException;
    String getStudentPhone(int studentId) throws SQLException, ClassNotFoundException;
    String getTodayPayments(String format) throws SQLException, ClassNotFoundException;
    double getDueAmount(int studentId) throws SQLException, ClassNotFoundException;
    int getPendingCount() throws SQLException, ClassNotFoundException;
}