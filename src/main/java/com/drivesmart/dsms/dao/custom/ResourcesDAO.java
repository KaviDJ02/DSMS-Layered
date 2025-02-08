package com.drivesmart.dsms.dao.custom;

import com.drivesmart.dsms.dao.CrudDAO;
import com.drivesmart.dsms.dto.ResourcesDTO;
import com.drivesmart.dsms.entity.Resources;
import com.drivesmart.dsms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ResourcesDAO extends CrudDAO<Resources> {
    int getResourceCount() throws SQLException, ClassNotFoundException;
    boolean updateResource(String url, ResourcesDTO updatedResource);
    List<String> getTitleNames() throws SQLException;
    String getResourceUrl(String title);
}
