package com.drivesmart.dsms.dao.custom;

import com.drivesmart.dsms.dao.CrudDAO;
import com.drivesmart.dsms.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    boolean verifyLogin(String username, String password) throws SQLException, ClassNotFoundException;
    boolean isUserExist(String username) throws SQLException, ClassNotFoundException;
    String getUserRole(String username) throws SQLException, ClassNotFoundException;
    String getUserId(String username) throws SQLException, ClassNotFoundException;
    boolean changePassword(String userId, String newPassword) throws SQLException, ClassNotFoundException;
    String getPassword(String userId) throws SQLException, ClassNotFoundException;
    String getUserMail(String username) throws SQLException, ClassNotFoundException;
    void updatePassword(String username, String newPassword) throws SQLException, ClassNotFoundException;
    boolean deactivateUser(String userId) throws SQLException, ClassNotFoundException;
    boolean addUser(String username, String password, String role, String empId) throws SQLException, ClassNotFoundException;
    int getUserCount() throws SQLException, ClassNotFoundException;
}