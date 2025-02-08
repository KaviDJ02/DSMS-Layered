package com.drivesmart.dsms.dao.custom;

import com.drivesmart.dsms.dao.CrudDAO;
import com.drivesmart.dsms.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO extends CrudDAO<Employee> {
    int getEmployeeCount() throws SQLException, ClassNotFoundException;
    String getEmpName(String userId) throws SQLException, ClassNotFoundException;
    boolean changeEmail(String userId, String email) throws SQLException, ClassNotFoundException;
    List<String> getAllActiveEmpNames();
    String getEmployeeId(String name);
    int getEmpId(String userId) throws SQLException;
    boolean update(Employee dto, String nic) throws SQLException, ClassNotFoundException;
}