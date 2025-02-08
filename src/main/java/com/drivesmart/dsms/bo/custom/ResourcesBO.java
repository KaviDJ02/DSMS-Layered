package com.drivesmart.dsms.bo.custom;

import com.drivesmart.dsms.bo.SuperBO;
import com.drivesmart.dsms.dto.ResourcesDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ResourcesBO extends SuperBO {
    ArrayList<ResourcesDTO> getAllResources() throws SQLException, ClassNotFoundException;
    boolean saveResource(ResourcesDTO resourceDTO) throws SQLException, ClassNotFoundException;
    boolean updateResource(ResourcesDTO resourceDTO) throws SQLException, ClassNotFoundException;
    boolean deleteResource(String id) throws SQLException, ClassNotFoundException;
    ResourcesDTO searchResource(String id) throws SQLException, ClassNotFoundException;
    int getResourceCount() throws SQLException, ClassNotFoundException;
    boolean updateResource(String url, ResourcesDTO updatedResource) throws SQLException;
    List<String> getTitleNames() throws SQLException;
    String getResourceUrl(String title) throws SQLException;
}