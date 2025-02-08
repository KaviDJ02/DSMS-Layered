package com.drivesmart.dsms.dao.custom;

import com.drivesmart.dsms.dao.CrudDAO;
import com.drivesmart.dsms.entity.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SessionDAO extends CrudDAO<Session> {
    int getSessionCount(String date) throws SQLException, ClassNotFoundException;
    List<String> getStudentsNames() throws SQLException, ClassNotFoundException;
    List<String> getInstructorsNames() throws SQLException, ClassNotFoundException;
    List<String> getVehicles() throws SQLException, ClassNotFoundException;
    int getStudentId(String name) throws SQLException, ClassNotFoundException;
    int getInstructorId(String name) throws SQLException, ClassNotFoundException;
    int getVehicleId(String vehiclePlate) throws SQLException, ClassNotFoundException;
    String getStudentName(int studentId) throws SQLException, ClassNotFoundException;
    String getInstructorName(int instructorId) throws SQLException, ClassNotFoundException;
    String getVehicleModel(int vehicleId) throws SQLException, ClassNotFoundException;
}