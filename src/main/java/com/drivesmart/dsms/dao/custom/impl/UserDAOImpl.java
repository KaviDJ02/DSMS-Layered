package com.drivesmart.dsms.dao.custom.impl;

import com.drivesmart.dsms.dao.custom.UserDAO;
import com.drivesmart.dsms.db.DBConnection;
import com.drivesmart.dsms.dto.UserDTO;
import com.drivesmart.dsms.entity.User;
import com.drivesmart.dsms.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    public UserDAOImpl() throws SQLException {
    }

    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM user;";
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute(sql);
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getString("status")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean save(User dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(User dto) throws SQLException, ClassNotFoundException {
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
    public User search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    Connection connection = DBConnection.getInstance().getConnection();

    @Override
    public boolean verifyLogin(String username, String password) {
        String sql = "select count(1) from user where username = '" + username + "' and password = '" + password + "' and status = 'active';";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                return resultSet.getInt(1) == 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isUserExist(String username) throws SQLException {

        String sql = "select count(1) from user where username = '" + username + "' and status = 'active';";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                return resultSet.getInt(1) == 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getUserRole(String username) {
        String sql = "select role from user where username = '" + username + "';";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                return resultSet.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUserId(String username) {
        String sql = "select user_id from user where username = '" + username + "';";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                return resultSet.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean changePassword(String userId, String newPassword) {
        String sql = "update user set password = '" + newPassword + "' where user_id = '" + userId + "';";
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getPassword(String userId) {
        String sql = "select password from user where user_id = '" + userId + "';";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                return resultSet.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUserMail(String username) {
        String sql = "select email from employee join user on employee.user_id = user.user_id where user.username = '" + username + "';";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                return resultSet.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        String sql = "update user set password = '" + newPassword + "' where username = '" + username + "';";
        try {
            CrudUtil.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deactivateUser(String userId) {
        String sql = "update user set status = 'deactivate' where user_id = '" + userId + "';";
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addUser(String username, String password, String role, String empId) {
        Connection connection = null;
        PreparedStatement userStmt = null;
        PreparedStatement updateEmpStmt = null;
        ResultSet resultSet = null;

        try {
            // Get the database connection (replace with your connection logic)
            connection = DBConnection.getInstance().getConnection();

            // Begin transaction
            connection.setAutoCommit(false);

            // Insert user into the 'user' table
            String insertUserQuery = "INSERT INTO user (username, password, role, status) VALUES (?, ?, ?, 'active')";
            userStmt = connection.prepareStatement(insertUserQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, username);
            userStmt.setString(2, password); // Ensure password is hashed before calling this method
            userStmt.setString(3, role);
            userStmt.executeUpdate();

            // Retrieve the generated user_id
            resultSet = userStmt.getGeneratedKeys();
            if (!resultSet.next()) {
                throw new SQLException("Failed to retrieve user_id.");
            }
            int userId = resultSet.getInt(1);

            // Update the employee table with the new user_id
            String updateEmployeeQuery = "UPDATE employee SET user_id = ? WHERE employee_id = ?";
            updateEmpStmt = connection.prepareStatement(updateEmployeeQuery);
            updateEmpStmt.setInt(1, userId);
            updateEmpStmt.setString(2, empId);
            int rowsAffected = updateEmpStmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No employee found with the given employee_id: " + empId);
            }

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
                if (userStmt != null) userStmt.close();
                if (updateEmpStmt != null) updateEmpStmt.close();
                if (connection != null) connection.setAutoCommit(true);
                if (connection != null) connection.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    @Override
    public int getUserCount() {
        String query = "SELECT COUNT(user_id) FROM user where status = 'active';";
        try (ResultSet resultSet = CrudUtil.execute(query)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
