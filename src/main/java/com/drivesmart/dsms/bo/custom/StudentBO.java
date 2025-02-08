package com.drivesmart.dsms.bo.custom;

import com.drivesmart.dsms.bo.SuperBO;
import com.drivesmart.dsms.dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentBO extends SuperBO {
    ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException;
    boolean saveStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException;
    boolean updateStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException;
    boolean deleteStudent(String id) throws SQLException, ClassNotFoundException;
    StudentDTO searchStudent(String id) throws SQLException, ClassNotFoundException;
    int getStudentCount() throws SQLException, ClassNotFoundException;
    String getStatus(String nic) throws SQLException, ClassNotFoundException;
    void setStatus(String nic, String status) throws SQLException, ClassNotFoundException;
    int getActiveStudentCount() throws SQLException, ClassNotFoundException;
    String getStudentName(int studentId) throws SQLException, ClassNotFoundException;
    String getStudentEmail(String name) throws SQLException, ClassNotFoundException;
}