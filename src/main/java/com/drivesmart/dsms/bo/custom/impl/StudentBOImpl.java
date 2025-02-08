package com.drivesmart.dsms.bo.custom.impl;

import com.drivesmart.dsms.bo.custom.StudentBO;
import com.drivesmart.dsms.dao.DAOFactory;
import com.drivesmart.dsms.dao.custom.StudentDAO;
import com.drivesmart.dsms.dto.StudentDTO;
import com.drivesmart.dsms.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentBOImpl implements StudentBO {
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);

    public StudentBOImpl() throws SQLException {
    }

    @Override
    public ArrayList<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException {
        ArrayList<Student> students = studentDAO.getAll();
        ArrayList<StudentDTO> studentDTOs = new ArrayList<>();
        for (Student student : students) {
            studentDTOs.add(new StudentDTO(student.getName(), student.getEmail(), student.getPhone(), student.getAddress(), student.getBirthday(), student.getNic()));
        }
        return studentDTOs;
    }

    @Override
    public boolean saveStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        Student student = new Student(studentDTO.getName(), studentDTO.getEmail(), studentDTO.getPhone(), studentDTO.getAddress(), studentDTO.getBirthday(), studentDTO.getNic());
        return studentDAO.save(student);
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        Student student = new Student(studentDTO.getName(), studentDTO.getEmail(), studentDTO.getPhone(), studentDTO.getAddress(), studentDTO.getBirthday(), studentDTO.getNic());
        return studentDAO.update(student);
    }

    @Override
    public boolean deleteStudent(String id) throws SQLException, ClassNotFoundException {
        return studentDAO.delete(id);
    }

    @Override
    public StudentDTO searchStudent(String id) throws SQLException, ClassNotFoundException {
        Student student = studentDAO.search(id);
        if (student != null) {
            return new StudentDTO(student.getName(), student.getEmail(), student.getPhone(), student.getAddress(), student.getBirthday(), student.getNic());
        }
        return null;
    }

    @Override
    public int getStudentCount() throws SQLException, ClassNotFoundException {
        return studentDAO.getStudentCount();
    }

    @Override
    public String getStatus(String nic) throws SQLException, ClassNotFoundException {
        return studentDAO.getStatus(nic);
    }

    @Override
    public void setStatus(String nic, String status) throws SQLException, ClassNotFoundException {
        studentDAO.setStatus(nic, status);
    }

    @Override
    public int getActiveStudentCount() throws SQLException, ClassNotFoundException {
        return studentDAO.getActiveStudentCount();
    }

    @Override
    public String getStudentName(int studentId) throws SQLException, ClassNotFoundException {
        return studentDAO.getStudentName(studentId);
    }

    @Override
    public String getStudentEmail(String name) throws SQLException, ClassNotFoundException {
        return studentDAO.getStudentEmail(name);
    }
}