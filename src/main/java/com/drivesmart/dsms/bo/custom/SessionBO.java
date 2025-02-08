package com.drivesmart.dsms.bo.custom;

import com.drivesmart.dsms.bo.SuperBO;
import com.drivesmart.dsms.dto.SessionDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SessionBO extends SuperBO {
    ArrayList<SessionDTO> getAllSessions(String clickedDay) throws SQLException, ClassNotFoundException;
    boolean saveSession(SessionDTO sessionDTO) throws SQLException, ClassNotFoundException;
    boolean updateSession(SessionDTO sessionDTO) throws SQLException, ClassNotFoundException;
    boolean deleteSession(String id) throws SQLException, ClassNotFoundException;
    SessionDTO searchSession(String id) throws SQLException, ClassNotFoundException;
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