package com.drivesmart.dsms.dao.custom.impl;

import com.drivesmart.dsms.dao.custom.SessionDAO;
import com.drivesmart.dsms.dto.SessionDTO;
import com.drivesmart.dsms.entity.Session;
import com.drivesmart.dsms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionDAOImpl implements SessionDAO {
    @Override
    public ArrayList<Session> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    public List<Session> getAllSessions(String date) {
        List<Session> sessions = new ArrayList<>();
        String query = "SELECT * FROM session WHERE session_date = ?";
        System.out.println("Query: " + query);
        System.out.println("Date: " + date);

        try (ResultSet resultSet = CrudUtil.execute(query,date)) {
            while (resultSet.next()) {
                String sessionDate = String.valueOf(resultSet.getDate("session_date"));
                int vehicleId = resultSet.getInt("vehicle_id");
                int employeeId = resultSet.getInt("employee_id");
                int studentId = resultSet.getInt("student_id");
                String sessionTime = String.valueOf(resultSet.getTime("session_time"));

                sessions.add(new Session(sessionDate, vehicleId, employeeId, studentId, sessionTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessions;
    }

    @Override
    public boolean save(Session dto) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO session(session_date, vehicle_id, employee_id, student_id, session_time) VALUES(?,?,?,?,?)";
        return CrudUtil.execute(query, dto.getSessionDate(), dto.getVehicleId(), dto.getEmployeeId(), dto.getStudentId(), dto.getSessionTime());
    }

    @Override
    public boolean update(Session dto) throws SQLException, ClassNotFoundException {
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
    public Session search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int getSessionCount(String date) {
        String query = "SELECT COUNT(session_id) FROM session WHERE session_date = ?";
        try (ResultSet resultSet = CrudUtil.execute(query, date)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<String> getStudentsNames() throws SQLException {
        List<String> students = new ArrayList<>();
        String query = "SELECT name FROM student";

        try (ResultSet resultSet = CrudUtil.execute(query)) {
            while (resultSet.next()) {
                students.add(resultSet.getString("name"));
            }
        }

        return students;
    }

    @Override
    public List<String> getInstructorsNames() throws SQLException {
        List<String> instructors = new ArrayList<>();
        String query = "SELECT name FROM employee WHERE position = 'Instructor'";

        try (ResultSet resultSet = CrudUtil.execute(query)) {
            while (resultSet.next()) {
                instructors.add(resultSet.getString("name"));
            }
        }

        return instructors;
    }

    @Override
    public List<String> getVehicles() throws SQLException {
        List<String> vehicles = new ArrayList<>();
        String query = "SELECT plate_number,model FROM vehicle";

        try (ResultSet resultSet = CrudUtil.execute(query)) {
            while (resultSet.next()) {
                String plate = resultSet.getString("plate_number");
                String model = resultSet.getString("model");
                vehicles.add(plate + " - " + model);
            }
        }

        return vehicles;
    }

    @Override
    public int getStudentId(String name) throws SQLException {
        String query = "SELECT student_id FROM student WHERE name = ?";
        try (ResultSet resultSet = CrudUtil.execute(query, name)) {
            if (resultSet.next()) {
                return resultSet.getInt("student_id");
            }
        }
        return -1;
    }

    @Override
    public int getInstructorId(String name) throws SQLException {
        String query = "SELECT employee_id FROM employee WHERE name = ?";
        try (ResultSet resultSet = CrudUtil.execute(query, name)) {
            if (resultSet.next()) {
                return resultSet.getInt("employee_id");
            }
        }
        return -1;
    }

    @Override
    public int getVehicleId(String vehiclePlate) throws SQLException {
        String plate = vehiclePlate.split(" - ")[0];

        String query = "SELECT vehicle_id FROM vehicle WHERE plate_number = ?";
        try (ResultSet resultSet = CrudUtil.execute(query, plate)) {
            if (resultSet.next()) {
                return resultSet.getInt("vehicle_id");
            }
        }
        return -1;
    }

    @Override
    public String getStudentName(int studentId) throws SQLException {
        String query = "SELECT name FROM student WHERE student_id = ?";
        try (ResultSet resultSet = CrudUtil.execute(query, studentId)) {
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        }
        return "";
    }

    @Override
    public String getInstructorName(int instructorId) throws SQLException {
        String query = "SELECT name FROM employee WHERE employee_id = ?";
        try (ResultSet resultSet = CrudUtil.execute(query, instructorId)) {
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        }
        return "";
    }

    @Override
    public String getVehicleModel(int vehicleId) throws SQLException {
        String query = "SELECT model FROM vehicle WHERE vehicle_id = ?";
        try (ResultSet resultSet = CrudUtil.execute(query, vehicleId)) {
            if (resultSet.next()) {
                return resultSet.getString("model");
            }
        }
        return "";
    }

}
