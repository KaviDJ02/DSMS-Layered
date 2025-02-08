package com.drivesmart.dsms.dao;

import com.drivesmart.dsms.dao.custom.impl.*;

import java.sql.SQLException;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }
    public static DAOFactory getInstance() {
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }

    public enum DAOType {
        EMAIL, EMPLOYEE, PACKAGE, PAYMENT, SESSION, STUDENT, VEHICLE, USER, RESOURCES
    }
    public SuperDAO getDAO(DAOType type) throws SQLException {
        switch (type) {
            case EMAIL:
                return new EmailDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case PACKAGE:
                return new PackageDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case SESSION:
                return new SessionDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
            case USER:
                return new UserDAOImpl();
            case RESOURCES:
                return new ResourcesDAOImpl();
            default:
                return null;
        }
    }

}
