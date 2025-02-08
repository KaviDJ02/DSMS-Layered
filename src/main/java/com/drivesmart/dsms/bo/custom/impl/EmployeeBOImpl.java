package com.drivesmart.dsms.bo.custom.impl;

import com.drivesmart.dsms.bo.custom.EmployeeBO;
import com.drivesmart.dsms.dao.DAOFactory;
import com.drivesmart.dsms.dao.custom.EmployeeDAO;
import com.drivesmart.dsms.dto.EmployeeDTO;
import com.drivesmart.dsms.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {
    private final EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    public EmployeeBOImpl() throws SQLException {
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees = employeeDAO.getAll();
        ArrayList<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDTOs.add(new EmployeeDTO(employee.getUserId(), employee.getName(), employee.getEmail(), employee.getPhone(), employee.getNic(), employee.getPosition(), employee.getSalary()));
        }
        return employeeDTOs;
    }

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        Employee employee = new Employee(employeeDTO.getUserId(), employeeDTO.getName(), employeeDTO.getEmail(), employeeDTO.getPhone(), employeeDTO.getNic(), employeeDTO.getPosition(), employeeDTO.getSalary());
        return employeeDAO.save(employee);
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        Employee employee = new Employee(employeeDTO.getUserId(), employeeDTO.getName(), employeeDTO.getEmail(), employeeDTO.getPhone(), employeeDTO.getNic(), employeeDTO.getPosition(), employeeDTO.getSalary());
        return employeeDAO.update(employee);
    }

    @Override
    public boolean deleteEmployee(String nic) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(nic);
    }

    @Override
    public EmployeeDTO searchEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(id);
        if (employee != null) {
            return new EmployeeDTO(employee.getUserId(), employee.getName(), employee.getEmail(), employee.getPhone(), employee.getNic(), employee.getPosition(), employee.getSalary());
        }
        return null;
    }

    @Override
    public int getEmployeeCount() throws SQLException, ClassNotFoundException {
        return employeeDAO.getEmployeeCount();
    }

    @Override
    public String getEmployeeName(String userId) throws SQLException, ClassNotFoundException {
        return employeeDAO.getEmpName(userId);
    }

    @Override
    public boolean changeEmail(String userId, String email) throws SQLException, ClassNotFoundException {
        return employeeDAO.changeEmail(userId, email);
    }

    @Override
    public List<String> getAllActiveEmpNames() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAllActiveEmpNames();
    }

    @Override
    public String getEmployeeId(String name) throws SQLException, ClassNotFoundException {
        return employeeDAO.getEmployeeId(name);
    }

    @Override
    public int getEmpId(String userId) throws SQLException, ClassNotFoundException {
        return employeeDAO.getEmpId(userId);
    }

    @Override
    public String getEmpName(String userId) {
        return "";
    }
}