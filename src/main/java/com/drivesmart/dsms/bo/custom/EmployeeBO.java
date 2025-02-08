package com.drivesmart.dsms.bo.custom;

import com.drivesmart.dsms.bo.SuperBO;
import com.drivesmart.dsms.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException;
    boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;
    boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;
    boolean deleteEmployee(String nic) throws SQLException, ClassNotFoundException;
    EmployeeDTO searchEmployee(String id) throws SQLException, ClassNotFoundException;
    int getEmployeeCount() throws SQLException, ClassNotFoundException;
    String getEmployeeName(String userId) throws SQLException, ClassNotFoundException;
    boolean changeEmail(String userId, String email) throws SQLException, ClassNotFoundException;
    List<String> getAllActiveEmpNames() throws SQLException, ClassNotFoundException;
    String getEmployeeId(String name) throws SQLException, ClassNotFoundException;
    int getEmpId(String userId) throws SQLException, ClassNotFoundException;
    String getEmpName(String userId);
}