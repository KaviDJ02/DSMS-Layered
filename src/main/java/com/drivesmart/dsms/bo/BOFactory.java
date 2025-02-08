package com.drivesmart.dsms.bo;

import com.drivesmart.dsms.bo.custom.impl.*;
import com.drivesmart.dsms.dao.custom.impl.*;

import java.sql.SQLException;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        EMAIL, EMPLOYEE, PACKAGE, PAYMENT, SESSION, STUDENT, VEHICLE, USER, RESOURCES
    }

    public SuperBO getBO(BOType type) throws SQLException {
        switch (type) {
            case EMAIL:
                return new EmailBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case PACKAGE:
                return new PackageBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case SESSION:
                return new SessionBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            case USER:
                return new UserBOImpl();
            case RESOURCES:
                return new ResourcesBOImpl();
            default:
                return null;
        }
    }
}
