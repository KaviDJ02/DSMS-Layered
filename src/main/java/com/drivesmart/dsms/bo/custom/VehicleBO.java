package com.drivesmart.dsms.bo.custom;

import com.drivesmart.dsms.bo.SuperBO;
import com.drivesmart.dsms.dto.VehicleDTO;
import com.drivesmart.dsms.dto.VehicleStatsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface VehicleBO extends SuperBO {
    ArrayList<VehicleDTO> getAllVehicles() throws SQLException, ClassNotFoundException;
    boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException, ClassNotFoundException;
    boolean updateVehicle(VehicleDTO vehicleDTO, String oldPlate) throws SQLException, ClassNotFoundException;
    boolean deleteVehicle(String licensePlate) throws SQLException, ClassNotFoundException;
    VehicleDTO searchVehicle(String licensePlate) throws SQLException, ClassNotFoundException;
    String getStatus(String licensePlate) throws SQLException, ClassNotFoundException;
    int getVehicleId(String licensePlate) throws SQLException, ClassNotFoundException;
    boolean addVehicleWithStats(VehicleDTO vehicleDTO, VehicleStatsDTO vehicleStatsDTO) throws SQLException, ClassNotFoundException;
    String toggleStatus(String licensePlate) throws SQLException, ClassNotFoundException;
}