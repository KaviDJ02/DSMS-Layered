package com.drivesmart.dsms.bo.custom.impl;

import com.drivesmart.dsms.bo.custom.EmailBO;
import com.drivesmart.dsms.dao.DAOFactory;
import com.drivesmart.dsms.dao.custom.EmailDAO;
import com.drivesmart.dsms.dto.EmailDTO;
import com.drivesmart.dsms.entity.Email;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmailBOImpl implements EmailBO {
    private final EmailDAO emailDAO = (EmailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMAIL);

    public EmailBOImpl() throws SQLException {
    }

    @Override
    public ArrayList<EmailDTO> getAllEmails() throws SQLException, ClassNotFoundException {
        ArrayList<Email> emails = emailDAO.getAll();
        ArrayList<EmailDTO> emailDTOs = new ArrayList<>();
        for (Email email : emails) {
            emailDTOs.add(new EmailDTO(email.getSubject(), email.getTitle(), email.getSentDate(), email.getStudentId(), email.getTemplateId()));
        }
        return emailDTOs;
    }

    @Override
    public boolean saveEmail(EmailDTO emailDTO) throws SQLException, ClassNotFoundException {
        Email email = new Email(emailDTO.getSubject(), emailDTO.getTitle(), emailDTO.getSentDate(), emailDTO.getStudentId(), emailDTO.getTemplateId());
        return emailDAO.save(email);
    }

    @Override
    public boolean updateEmail(EmailDTO emailDTO) throws SQLException, ClassNotFoundException {
        Email email = new Email(emailDTO.getSubject(), emailDTO.getTitle(), emailDTO.getSentDate(), emailDTO.getStudentId(), emailDTO.getTemplateId());
        return emailDAO.update(email);
    }

    @Override
    public boolean deleteEmail(String id) throws SQLException, ClassNotFoundException {
        return emailDAO.delete(id);
    }

    @Override
    public EmailDTO searchEmail(String id) throws SQLException, ClassNotFoundException {
        Email email = emailDAO.search(id);
        if (email != null) {
            return new EmailDTO(email.getSubject(), email.getTitle(), email.getSentDate(), email.getStudentId(), email.getTemplateId());
        }
        return null;
    }

    @Override
    public String getStudentEmail(String name) {
        return emailDAO.getStudentEmail(name);
    }

    @Override
    public int getEmailCount() {
        return emailDAO.getEmailCount();
    }
}