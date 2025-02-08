package com.drivesmart.dsms.bo.custom;

import com.drivesmart.dsms.bo.SuperBO;
import com.drivesmart.dsms.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException;
    boolean saveUser(UserDTO userDTO) throws SQLException, ClassNotFoundException;
    boolean updateUser(UserDTO userDTO) throws SQLException, ClassNotFoundException;
    boolean deleteUser(String username) throws SQLException, ClassNotFoundException;
    UserDTO searchUser(String username) throws SQLException, ClassNotFoundException;
    int getUserCount() throws SQLException, ClassNotFoundException;
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
}