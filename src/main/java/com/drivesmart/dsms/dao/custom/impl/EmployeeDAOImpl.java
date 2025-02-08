package com.drivesmart.dsms.dao.custom.impl;

import com.drivesmart.dsms.dao.custom.EmployeeDAO;
import com.drivesmart.dsms.dto.EmployeeDTO;
import com.drivesmart.dsms.entity.Employee;
import com.drivesmart.dsms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {


    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee"; // Adjust the table name and columns as per your database schema
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            employees.add(new Employee(
                    resultSet.getInt("user_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("phone"),
                    resultSet.getString("nic"),
                    resultSet.getString("position"),
                    resultSet.getString("salary")
            ));
        }

        return (ArrayList<Employee>) employees;
    }

    @Override
    public boolean save(Employee dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO employee (name, email, phone, nic, position, salary) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            CrudUtil.execute(sql, dto.getName(), dto.getEmail(), dto.getPhone(), dto.getNic(), dto.getPosition(), dto.getSalary());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Employee dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String nic) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM employee WHERE nic = ?";
        try {
            CrudUtil.execute(sql, nic);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Employee dto, String nic) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE employee SET name = ?, email = ?, phone = ?, nic = ?, position = ?, salary = ? WHERE nic = ?";
        try {
            CrudUtil.execute(sql, dto.getName(), dto.getEmail(), dto.getPhone(), dto.getNic(), dto.getPosition(), dto.getSalary(), nic);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> getAllActiveEmpNames(){
        List<String> empNames = new ArrayList<>();
        String sql = "SELECT name FROM employee join user on employee.user_id = user.user_id WHERE user.status = 'active'";
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()) {
                empNames.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empNames;
    }

    @Override
    public String getEmployeeId(String name){
        String sql = "SELECT employee_id FROM employee WHERE name = '"+name+"'";
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getEmpId(String userId) throws SQLException {
        String sql = "SELECT employee_id FROM employee WHERE user_id = '"+userId+"'";
        ResultSet resultSet = CrudUtil.execute(sql);
        System.out.println(sql);
        try {
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public String getEmpName(String userId) throws SQLException {
        String sql = "SELECT name FROM employee WHERE user_id = '"+userId+"'";
        ResultSet resultSet = CrudUtil.execute(sql);
        try {
            while (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean changeEmail(String userId, String email) throws SQLException {
        String sql = "UPDATE employee SET email = '"+email+"' WHERE user_id = '"+userId+"'";

        try {
            CrudUtil.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getEmployeeCount() {
        return 0;
    }
}
