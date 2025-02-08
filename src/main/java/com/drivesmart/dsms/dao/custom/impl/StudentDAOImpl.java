package com.drivesmart.dsms.dao.custom.impl;

import com.drivesmart.dsms.dao.custom.StudentDAO;
import com.drivesmart.dsms.dto.StudentDTO;
import com.drivesmart.dsms.entity.Student;
import com.drivesmart.dsms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public ArrayList<Student> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student"; // Adjust the table name and columns as per your database schema
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            students.add(new Student(
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("phone"),
                    resultSet.getString("address"),
                    resultSet.getString("birthday"),
                    resultSet.getString("nic")
            ));
        }
        return students;
    }

    @Override
    public boolean save(Student dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO student (name, email, phone, address, birthday, nic) VALUES(?,?,?,?,?,?)";
        return CrudUtil.execute(sql, dto.getName(), dto.getEmail(), dto.getPhone(), dto.getAddress(), dto.getBirthday(), dto.getNic());
    }

    @Override
    public boolean update(Student dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE student SET name=?, email=?, phone=?, address=?, birthday=? WHERE nic=?";
        return CrudUtil.execute(sql, dto.getName(), dto.getEmail(), dto.getPhone(), dto.getAddress(), dto.getBirthday(), dto.getNic());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM student WHERE nic=?"; // Adjust the table name and columns as per your database schema
        return CrudUtil.execute(sql, nic);
    }

    @Override
    public Student search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getStatus(String nic) {
        String sql = "SELECT status FROM package_enrollment JOIN student s on package_enrollment.student_id = s.student_id WHERE s.nic = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(sql, nic);
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "not enrolled";
    }

    @Override
    public void setStatus(String nic, String status) throws SQLException {
        String sql = "UPDATE package_enrollment JOIN student s on package_enrollment.student_id = s.student_id SET status = ? WHERE s.nic = ?";
        CrudUtil.execute(sql, status, nic);
    }

    @Override
    public int getActiveStudentCount() {
        String sql = "SELECT COUNT(*) FROM package_enrollment WHERE status = 'active'";
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getStudentName(int studentId) {
        String sql = "SELECT name FROM student WHERE student_id = ?";
        try {
            ResultSet rs = CrudUtil.execute(sql, studentId);
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "student";
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
    public int getStudentCount() throws SQLException, ClassNotFoundException {
        return 0;
    }
}
