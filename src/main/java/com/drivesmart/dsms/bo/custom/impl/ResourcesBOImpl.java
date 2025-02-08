package com.drivesmart.dsms.bo.custom.impl;

import com.drivesmart.dsms.bo.custom.ResourcesBO;
import com.drivesmart.dsms.dao.DAOFactory;
import com.drivesmart.dsms.dao.custom.ResourcesDAO;
import com.drivesmart.dsms.dto.ResourcesDTO;
import com.drivesmart.dsms.entity.Resources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResourcesBOImpl implements ResourcesBO {
    private final ResourcesDAO resourcesDAO = (ResourcesDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RESOURCES);

    public ResourcesBOImpl() throws SQLException {
    }

    @Override
    public ArrayList<ResourcesDTO> getAllResources() throws SQLException, ClassNotFoundException {
        ArrayList<Resources> resources = resourcesDAO.getAll();
        ArrayList<ResourcesDTO> resourcesDTOs = new ArrayList<>();
        for (Resources resource : resources) {
            resourcesDTOs.add(new ResourcesDTO(resource.getPackageId(), resource.getTitle(), resource.getUrl()));
        }
        return resourcesDTOs;
    }

    @Override
    public boolean saveResource(ResourcesDTO resourceDTO) throws SQLException, ClassNotFoundException {
        Resources resource = new Resources(resourceDTO.getPackageId(), resourceDTO.getTitle(), resourceDTO.getUrl());
        return resourcesDAO.save(resource);
    }

    @Override
    public boolean updateResource(ResourcesDTO resourceDTO) throws SQLException, ClassNotFoundException {
        Resources resource = new Resources(resourceDTO.getPackageId(), resourceDTO.getTitle(), resourceDTO.getUrl());
        return resourcesDAO.update(resource);
    }

    @Override
    public boolean deleteResource(String id) throws SQLException, ClassNotFoundException {
        return resourcesDAO.delete(id);
    }

    @Override
    public ResourcesDTO searchResource(String id) throws SQLException, ClassNotFoundException {
        Resources resource = resourcesDAO.search(id);
        if (resource != null) {
            return new ResourcesDTO(resource.getPackageId(), resource.getTitle(), resource.getUrl());
        }
        return null;
    }

    @Override
    public int getResourceCount() throws SQLException, ClassNotFoundException {
        return resourcesDAO.getResourceCount();
    }

    @Override
    public boolean updateResource(String url, ResourcesDTO updatedResource) throws SQLException {
        return resourcesDAO.updateResource(url, updatedResource);
    }

    @Override
    public List<String> getTitleNames() throws SQLException {
        return resourcesDAO.getTitleNames();
    }

    @Override
    public String getResourceUrl(String title) throws SQLException {
        return resourcesDAO.getResourceUrl(title);
    }
}