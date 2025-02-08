package com.drivesmart.dsms.bo.custom.impl;

import com.drivesmart.dsms.bo.custom.SessionBO;
import com.drivesmart.dsms.dao.DAOFactory;
import com.drivesmart.dsms.dao.custom.SessionDAO;
import com.drivesmart.dsms.dto.SessionDTO;
import com.drivesmart.dsms.entity.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionBOImpl implements SessionBO {
    private final SessionDAO sessionDAO = (SessionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SESSION);

    public SessionBOImpl() throws SQLException {
    }

    @Override
    public ArrayList<SessionDTO> getAllSessions(String clickedDay) throws SQLException, ClassNotFoundException {
        ArrayList<Session> sessions = sessionDAO.getAll();
        ArrayList<SessionDTO> sessionDTOs = new ArrayList<>();
        for (Session session : sessions) {
            sessionDTOs.add(new SessionDTO(session.getSessionDate(), session.getVehicleId(), session.getEmployeeId(), session.getStudentId(), session.getSessionTime()));
        }
        return sessionDTOs;
    }

    @Override
    public boolean saveSession(SessionDTO sessionDTO) throws SQLException, ClassNotFoundException {
        Session session = new Session( sessionDTO.getSessionDate(), sessionDTO.getVehicleId(), sessionDTO.getEmployeeId(), sessionDTO.getStudentId(), sessionDTO.getSessionTime());
        return sessionDAO.save(session);
    }

    @Override
    public boolean updateSession(SessionDTO sessionDTO) throws SQLException, ClassNotFoundException {
        Session session = new Session(sessionDTO.getSessionDate(), sessionDTO.getVehicleId(), sessionDTO.getEmployeeId(), sessionDTO.getStudentId(), sessionDTO.getSessionTime());
        return sessionDAO.update(session);
    }

    @Override
    public boolean deleteSession(String id) throws SQLException, ClassNotFoundException {
        return sessionDAO.delete(id);
    }

    @Override
    public SessionDTO searchSession(String id) throws SQLException, ClassNotFoundException {
        Session session = sessionDAO.search(id);
        if (session != null) {
            return new SessionDTO(session.getSessionDate(), session.getVehicleId(), session.getEmployeeId(), session.getStudentId(), session.getSessionTime());
        }
        return null;
    }

    @Override
    public int getSessionCount(String date) throws SQLException, ClassNotFoundException {
        return sessionDAO.getSessionCount(date);
    }

    @Override
    public List<String> getStudentsNames() throws SQLException, ClassNotFoundException {
        return sessionDAO.getStudentsNames();
    }

    @Override
    public List<String> getInstructorsNames() throws SQLException, ClassNotFoundException {
        return sessionDAO.getInstructorsNames();
    }

    @Override
    public List<String> getVehicles() throws SQLException, ClassNotFoundException {
        return sessionDAO.getVehicles();
    }

    @Override
    public int getStudentId(String name) throws SQLException, ClassNotFoundException {
        return sessionDAO.getStudentId(name);
    }

    @Override
    public int getInstructorId(String name) throws SQLException, ClassNotFoundException {
        return sessionDAO.getInstructorId(name);
    }

    @Override
    public int getVehicleId(String vehiclePlate) throws SQLException, ClassNotFoundException {
        return sessionDAO.getVehicleId(vehiclePlate);
    }

    @Override
    public String getStudentName(int studentId) throws SQLException, ClassNotFoundException {
        return sessionDAO.getStudentName(studentId);
    }

    @Override
    public String getInstructorName(int instructorId) throws SQLException, ClassNotFoundException {
        return sessionDAO.getInstructorName(instructorId);
    }

    @Override
    public String getVehicleModel(int vehicleId) throws SQLException, ClassNotFoundException {
        return sessionDAO.getVehicleModel(vehicleId);
    }
}