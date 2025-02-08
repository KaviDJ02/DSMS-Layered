package com.drivesmart.dsms.dao.custom;

import com.drivesmart.dsms.dao.CrudDAO;
import com.drivesmart.dsms.entity.Student;

import java.sql.SQLException;

public interface StudentDAO extends CrudDAO<Student> {
    int getStudentCount() throws SQLException, ClassNotFoundException;
    String getStatus(String nic) throws SQLException, ClassNotFoundException;
    void setStatus(String nic, String status) throws SQLException, ClassNotFoundException;
    int getActiveStudentCount() throws SQLException, ClassNotFoundException;
    String getStudentName(int studentId) throws SQLException, ClassNotFoundException;
    String getStudentEmail(String name) throws SQLException, ClassNotFoundException;
}