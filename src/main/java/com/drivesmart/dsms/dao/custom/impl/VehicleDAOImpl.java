package com.drivesmart.dsms.dao.custom.impl;

import com.drivesmart.dsms.dao.custom.VehicleDAO;
import com.drivesmart.dsms.db.DBConnection;
import com.drivesmart.dsms.dto.VehicleDTO;
import com.drivesmart.dsms.dto.VehicleStatsDTO;
import com.drivesmart.dsms.entity.Vehicle;
import com.drivesmart.dsms.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOImpl implements VehicleDAO {
    @Override
    public ArrayList<Vehicle> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicle";
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()) {
                vehicles.add(new Vehicle(
                        resultSet.getString("vehicle_type"),
                        resultSet.getString("make"),
                        resultSet.getString("model"),
                        resultSet.getString("transmission"),
                        resultSet.getString("plate_number")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return vehicles;
    }

    @Override
    public boolean save(Vehicle dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO vehicle (vehicle_type, make, model, transmission, plate_number) VALUES (?, ?, ?, ?, ?)";
        try {
            CrudUtil.execute(sql, dto.getVehicleType(), dto.getMake(), dto.getModel(), dto.getTransmission(), dto.getLicensePlate());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Vehicle dto) throws SQLException, ClassNotFoundException {
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
    public Vehicle search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean updateVehicle(VehicleDTO vehicle, String oldPlate) {
        String sql = "UPDATE vehicle SET vehicle_type = ?, make = ?, model = ?, transmission = ?, plate_number = ? WHERE plate_number = ?";
        try {
            CrudUtil.execute(sql, vehicle.getVehicleType(), vehicle.getMake(), vehicle.getModel(), vehicle.getTransmission(), vehicle.getLicensePlate(), oldPlate);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteVehicle(VehicleDTO vehicle) {
        String sql = "DELETE FROM vehicle WHERE plate_number = ?";
        try {
            CrudUtil.execute(sql, vehicle.getLicensePlate());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public String getStatus(String licensePlate) {
        String sql = "SELECT vs.status FROM Vehicle v JOIN Vehicle_Status vs ON v.vehicle_id = vs.vehicle_id WHERE v.plate_number = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(sql, licensePlate);
            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "No Record Found";
    }

    @Override
    public int getVehicleId(String licensePlate) {
        String sql = "SELECT vehicle_id FROM vehicle WHERE plate_number = ?";
        try {
            ResultSet resultSet = CrudUtil.execute(sql, licensePlate);
            if (resultSet.next()) {
                return resultSet.getInt("vehicle_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return -1;
    }

    @Override
    public boolean addVehicleWithStats(VehicleDTO vehicle, VehicleStatsDTO vehicleStats) {
        Connection connection = null;
        PreparedStatement vehicleStmt = null;
        PreparedStatement statsStmt = null;
        ResultSet resultSet = null;

        try {
            // Get the database connection
            connection = DBConnection.getInstance().getConnection();

            // Begin transaction
            connection.setAutoCommit(false);

            // Insert vehicle into the 'vehicle' table
            String insertVehicleQuery = "INSERT INTO vehicle (vehicle_type, make, model, transmission, plate_number) VALUES (?, ?, ?, ?, ?)";
            vehicleStmt = connection.prepareStatement(insertVehicleQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            vehicleStmt.setString(1, vehicle.getVehicleType());
            vehicleStmt.setString(2, vehicle.getMake());
            vehicleStmt.setString(3, vehicle.getModel());
            vehicleStmt.setString(4, vehicle.getTransmission());
            vehicleStmt.setString(5, vehicle.getLicensePlate());
            vehicleStmt.executeUpdate();

            // Retrieve the generated vehicle_id
            resultSet = vehicleStmt.getGeneratedKeys();
            if (!resultSet.next()) {
                throw new SQLException("Failed to retrieve vehicle_id.");
            }
            int vehicleId = resultSet.getInt(1);

            // Insert vehicle stats into the 'vehicle_stats' table
            String insertStatsQuery = "INSERT INTO vehicle_status (vehicle_id, status, timestamp) VALUES (?, ?, ?)";
            statsStmt = connection.prepareStatement(insertStatsQuery);
            statsStmt.setInt(1, vehicleId);
            statsStmt.setString(2, vehicleStats.getStatus());
            statsStmt.setString(3, vehicleStats.getTimestamp());
            statsStmt.executeUpdate();

            // Commit transaction
            connection.commit();

            return true;
        } catch (SQLException e) {
            // Rollback in case of an error
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (vehicleStmt != null) vehicleStmt.close();
                if (statsStmt != null) statsStmt.close();
                if (connection != null) connection.setAutoCommit(true);
//                if (connection != null) connection.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    @Override
    public String toggleStatus(String licensePlate) {
        String sql = "UPDATE vehicle_status SET status = CASE WHEN status = 'available' THEN 'unavailable' ELSE 'available' END WHERE vehicle_id = ?";
        try {
            CrudUtil.execute(sql, getVehicleId(licensePlate));
            return getStatus(licensePlate);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "No Record Found";
    }

}
