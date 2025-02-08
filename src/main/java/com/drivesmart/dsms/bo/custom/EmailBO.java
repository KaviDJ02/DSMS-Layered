package com.drivesmart.dsms.bo.custom;

import com.drivesmart.dsms.bo.SuperBO;
import com.drivesmart.dsms.dto.EmailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmailBO extends SuperBO {
    ArrayList<EmailDTO> getAllEmails() throws SQLException, ClassNotFoundException;
    boolean saveEmail(EmailDTO emailDTO) throws SQLException, ClassNotFoundException;
    boolean updateEmail(EmailDTO emailDTO) throws SQLException, ClassNotFoundException;
    boolean deleteEmail(String id) throws SQLException, ClassNotFoundException;
    EmailDTO searchEmail(String id) throws SQLException, ClassNotFoundException;
    String getStudentEmail(String name) throws SQLException, ClassNotFoundException;
    int getEmailCount() throws SQLException, ClassNotFoundException;
}
