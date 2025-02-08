package com.drivesmart.dsms.bo.custom.impl;

import com.drivesmart.dsms.bo.custom.VehicleBO;
import com.drivesmart.dsms.dao.DAOFactory;
import com.drivesmart.dsms.dao.custom.VehicleDAO;
import com.drivesmart.dsms.dto.VehicleDTO;
import com.drivesmart.dsms.dto.VehicleStatsDTO;
import com.drivesmart.dsms.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleBOImpl implements VehicleBO {
    private final VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.VEHICLE);

    public VehicleBOImpl() throws SQLException {
    }

    @Override
    public ArrayList<VehicleDTO> getAllVehicles() throws SQLException, ClassNotFoundException {
        ArrayList<Vehicle> vehicles = vehicleDAO.getAll();
        ArrayList<VehicleDTO> vehicleDTOs = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            vehicleDTOs.add(new VehicleDTO(vehicle.getVehicleType(), vehicle.getMake(), vehicle.getModel(), vehicle.getTransmission(), vehicle.getLicensePlate()));
        }
        return vehicleDTOs;
    }

    @Override
    public boolean saveVehicle(VehicleDTO vehicleDTO) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle(vehicleDTO.getVehicleType(), vehicleDTO.getMake(), vehicleDTO.getModel(), vehicleDTO.getTransmission(), vehicleDTO.getLicensePlate());
        return vehicleDAO.save(vehicle);
    }

    @Override
    public boolean updateVehicle(VehicleDTO vehicleDTO, String oldPlate) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle(vehicleDTO.getVehicleType(), vehicleDTO.getMake(), vehicleDTO.getModel(), vehicleDTO.getTransmission(), vehicleDTO.getLicensePlate());
        return vehicleDAO.updateVehicle(vehicleDTO, oldPlate);
    }

    @Override
    public boolean deleteVehicle(String licensePlate) throws SQLException, ClassNotFoundException {
        return vehicleDAO.deleteVehicle(new VehicleDTO(null, null, null, null, licensePlate));
    }

    @Override
    public VehicleDTO searchVehicle(String licensePlate) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = vehicleDAO.search(licensePlate);
        if (vehicle != null) {
            return new VehicleDTO(vehicle.getVehicleType(), vehicle.getMake(), vehicle.getModel(), vehicle.getTransmission(), vehicle.getLicensePlate());
        }
        return null;
    }

    @Override
    public String getStatus(String licensePlate) throws SQLException, ClassNotFoundException {
        return vehicleDAO.getStatus(licensePlate);
    }

    @Override
    public int getVehicleId(String licensePlate) throws SQLException, ClassNotFoundException {
        return vehicleDAO.getVehicleId(licensePlate);
    }

    @Override
    public boolean addVehicleWithStats(VehicleDTO vehicleDTO, VehicleStatsDTO vehicleStatsDTO) throws SQLException, ClassNotFoundException {
        return vehicleDAO.addVehicleWithStats(vehicleDTO, vehicleStatsDTO);
    }

    @Override
    public String toggleStatus(String licensePlate) throws SQLException, ClassNotFoundException {
        return vehicleDAO.toggleStatus(licensePlate);
    }
}