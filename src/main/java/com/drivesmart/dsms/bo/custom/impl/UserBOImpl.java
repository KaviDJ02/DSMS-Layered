package com.drivesmart.dsms.bo.custom.impl;

import com.drivesmart.dsms.bo.custom.UserBO;
import com.drivesmart.dsms.dao.DAOFactory;
import com.drivesmart.dsms.dao.custom.UserDAO;
import com.drivesmart.dsms.dto.UserDTO;
import com.drivesmart.dsms.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    public UserBOImpl() throws SQLException {
    }

    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<User> users = userDAO.getAll();
        ArrayList<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(new UserDTO(user.getUsername(), user.getPassword(), user.getRole(), user.getStatus()));
        }
        return userDTOs;
    }

    @Override
    public boolean saveUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        User user = new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole(), userDTO.getStatus());
        return userDAO.save(user);
    }

    @Override
    public boolean updateUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        User user = new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole(), userDTO.getStatus());
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(String username) throws SQLException, ClassNotFoundException {
        return userDAO.delete(username);
    }

    @Override
    public UserDTO searchUser(String username) throws SQLException, ClassNotFoundException {
        User user = userDAO.search(username);
        if (user != null) {
            return new UserDTO(user.getUsername(), user.getPassword(), user.getRole(), user.getStatus());
        }
        return null;
    }

    @Override
    public int getUserCount() throws SQLException, ClassNotFoundException {
        return userDAO.getUserCount();
    }

    @Override
    public boolean verifyLogin(String username, String password) throws SQLException, ClassNotFoundException {
        return userDAO.verifyLogin(username, password);
    }

    @Override
    public boolean isUserExist(String username) throws SQLException, ClassNotFoundException {
        return userDAO.isUserExist(username);
    }

    @Override
    public String getUserRole(String username) throws SQLException, ClassNotFoundException {
        return userDAO.getUserRole(username);
    }

    @Override
    public String getUserId(String username) throws SQLException, ClassNotFoundException {
        return userDAO.getUserId(username);
    }

    @Override
    public boolean changePassword(String userId, String newPassword) throws SQLException, ClassNotFoundException {
        return userDAO.changePassword(userId, newPassword);
    }

    @Override
    public String getPassword(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.getPassword(userId);
    }

    @Override
    public String getUserMail(String username) throws SQLException, ClassNotFoundException {
        return userDAO.getUserMail(username);
    }

    @Override
    public void updatePassword(String username, String newPassword) throws SQLException, ClassNotFoundException {
        userDAO.updatePassword(username, newPassword);
    }

    @Override
    public boolean deactivateUser(String userId) throws SQLException, ClassNotFoundException {
        return userDAO.deactivateUser(userId);
    }

    @Override
    public boolean addUser(String username, String password, String role, String empId) throws SQLException, ClassNotFoundException {
        return userDAO.addUser(username, password, role, empId);
    }
}