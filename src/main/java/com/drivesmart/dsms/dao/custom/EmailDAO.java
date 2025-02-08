package com.drivesmart.dsms.dao.custom;

import com.drivesmart.dsms.dao.CrudDAO;
import com.drivesmart.dsms.entity.Email;

public interface EmailDAO extends CrudDAO<Email> {
    String getStudentEmail(String name);
    int getEmailCount();
}
