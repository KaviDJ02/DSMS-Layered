package com.drivesmart.dsms.dao.custom.impl;

import com.drivesmart.dsms.dao.custom.PaymentDAO;
import com.drivesmart.dsms.dto.PaymentDTO;
import com.drivesmart.dsms.entity.Payment;
import com.drivesmart.dsms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payment";
        ResultSet rs = CrudUtil.execute(sql);

        while (rs.next()) {
            Payment payment = new Payment(
                    rs.getInt("student_id"),
                    rs.getDouble("amount"),
                    rs.getDouble("amount_due"),
                    rs.getString("received_date"),
                    rs.getString("status")
            );
            payments.add(payment);
        }
        return payments;
    }

    @Override
    public boolean save(Payment dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO payment (student_id, amount, amount_due, received_date, status) VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, dto.getStudentId(), dto.getAmount(), dto.getAmountDue(), dto.getReceivedDate(), dto.getStatus());

    }

    @Override
    public boolean update(Payment dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Payment search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getStudentPhone(int studentId) {
        String sql = "SELECT phone FROM student WHERE student_id = ?";
        try {
            ResultSet rs = CrudUtil.execute(sql, studentId);
            if (rs.next()) {
                return rs.getString("phone");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @Override
    public String getTodayPayments(String format) {
        String sql = "SELECT SUM(amount) FROM payment WHERE received_date = ?";
        try {
            ResultSet rs = CrudUtil.execute(sql, format);
            if (rs.next()) {
                String result =  rs.getString(1);
                if(result != null){
                    return result;
                }else return "0";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0";
    }

    @Override
    public double getDueAmount(int studentId) {
        String sql = "SELECT amount_due FROM payment WHERE student_id = ? ORDER BY received_date DESC LIMIT 1";
        try {
            ResultSet rs = CrudUtil.execute(sql, studentId);
            if (rs.next()) {
                return rs.getDouble("amount_due");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getPendingCount() {
        String sql = "SELECT COUNT(DISTINCT student_id) AS pending_students_count FROM payment WHERE status = 'pending';";
        try {
            ResultSet rs = CrudUtil.execute(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
