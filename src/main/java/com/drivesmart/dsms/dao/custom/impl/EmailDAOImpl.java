package com.drivesmart.dsms.dao.custom.impl;

import com.drivesmart.dsms.dao.custom.EmailDAO;
import com.drivesmart.dsms.dto.EmailDTO;
import com.drivesmart.dsms.entity.Email;
import com.drivesmart.dsms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmailDAOImpl implements EmailDAO {


    @Override
    public ArrayList<Email> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Email dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO email (student_id, template_id, subject, title, sent_date) VALUES(?, ?, ?, ?, ?)";
        try {
            CrudUtil.execute(sql, dto.getStudentId(), dto.getTemplateId(), dto.getSubject(), dto.getTitle(), dto.getSentDate());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Email dto) throws SQLException, ClassNotFoundException {
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
    public Email search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getStudentEmail(String name) {
        String sql = "SELECT email FROM student WHERE name = ?";
        try (ResultSet resultSet = CrudUtil.execute(sql, name)) {
            if (resultSet.next()) {
                return resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getEmailCount() {
        String query = "SELECT COUNT(email_id) FROM email WHERE MONTH(sent_date) = MONTH(CURRENT_DATE()) AND YEAR(sent_date) = YEAR(CURRENT_DATE())";
        try (ResultSet resultSet = CrudUtil.execute(query)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
