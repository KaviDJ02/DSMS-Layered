package com.drivesmart.dsms.bo.custom.impl;

import com.drivesmart.dsms.bo.custom.PaymentBO;
import com.drivesmart.dsms.dao.DAOFactory;
import com.drivesmart.dsms.dao.custom.PaymentDAO;
import com.drivesmart.dsms.dto.PaymentDTO;
import com.drivesmart.dsms.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);

    public PaymentBOImpl() throws SQLException {
    }

    @Override
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> payments = paymentDAO.getAll();
        ArrayList<PaymentDTO> paymentDTOs = new ArrayList<>();
        for (Payment payment : payments) {
            paymentDTOs.add(new PaymentDTO(payment.getStudentId(), payment.getAmount(), payment.getAmountDue(), payment.getReceivedDate(), payment.getStatus()));
        }
        return paymentDTOs;
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        Payment payment = new Payment(paymentDTO.getStudentId(), paymentDTO.getAmount(), paymentDTO.getAmountDue(), paymentDTO.getReceivedDate(), paymentDTO.getStatus());
        return paymentDAO.save(payment);
    }

    @Override
    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        Payment payment = new Payment(paymentDTO.getStudentId(), paymentDTO.getAmount(), paymentDTO.getAmountDue(), paymentDTO.getReceivedDate(), paymentDTO.getStatus());
        return paymentDAO.update(payment);
    }

    @Override
    public boolean deletePayment(String id) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(id);
    }

    @Override
    public PaymentDTO searchPayment(String id) throws SQLException, ClassNotFoundException {
        Payment payment = paymentDAO.search(id);
        if (payment != null) {
            return new PaymentDTO(payment.getStudentId(), payment.getAmount(), payment.getAmountDue(), payment.getReceivedDate(), payment.getStatus());
        }
        return null;
    }

    @Override
    public String getStudentPhone(int studentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.getStudentPhone(studentId);
    }

    @Override
    public String getTodayPayments(String format) throws SQLException, ClassNotFoundException {
        return paymentDAO.getTodayPayments(format);
    }

    @Override
    public double getDueAmount(int studentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.getDueAmount(studentId);
    }

    @Override
    public int getPendingCount() throws SQLException, ClassNotFoundException {
        return paymentDAO.getPendingCount();
    }
}