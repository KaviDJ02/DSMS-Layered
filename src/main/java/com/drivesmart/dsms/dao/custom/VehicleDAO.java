package com.drivesmart.dsms.dao.custom;

import com.drivesmart.dsms.dao.CrudDAO;
import com.drivesmart.dsms.dto.VehicleDTO;
import com.drivesmart.dsms.dto.VehicleStatsDTO;
import com.drivesmart.dsms.entity.Vehicle;

import java.sql.SQLException;

public interface VehicleDAO extends CrudDAO<Vehicle> {
    boolean updateVehicle(VehicleDTO vehicle, String oldPlate) throws SQLException, ClassNotFoundException;
    boolean deleteVehicle(VehicleDTO vehicle) throws SQLException, ClassNotFoundException;
    String getStatus(String licensePlate) throws SQLException, ClassNotFoundException;
    int getVehicleId(String licensePlate) throws SQLException, ClassNotFoundException;
    boolean addVehicleWithStats(VehicleDTO vehicle, VehicleStatsDTO vehicleStats) throws SQLException, ClassNotFoundException;
    String toggleStatus(String licensePlate) throws SQLException, ClassNotFoundException;
}