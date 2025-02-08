package com.drivesmart.dsms.dao.custom.impl;

import com.drivesmart.dsms.dao.custom.ResourcesDAO;
import com.drivesmart.dsms.dto.ResourcesDTO;
import com.drivesmart.dsms.entity.Resources;
import com.drivesmart.dsms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResourcesDAOImpl implements ResourcesDAO {

    @Override
    public ArrayList<Resources> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM resources";
        ResultSet resultSet = CrudUtil.execute(sql);
        ArrayList<Resources> resources = new ArrayList<>();
        while (resultSet.next()) {
            resources.add(new Resources(
                    resultSet.getInt("package_id"),
                    resultSet.getString("title"),
                    resultSet.getString("url")
            ));
        }
        return resources;
    }

    @Override
    public boolean save(Resources dto) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO resources (package_id,title,url) VALUES (?, ?, ?);";
        try {
            return CrudUtil.execute(query, dto.getPackageId(), dto.getTitle(), dto.getUrl());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Resources dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String url) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM resources WHERE url = ?";
        try {
            return CrudUtil.execute(sql, url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Resources search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int getResourceCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public boolean updateResource(String url, ResourcesDTO updatedResource) {
        String sql = "UPDATE resources SET title = ?, package_id = ?, url = ? WHERE url = ?";
        try {
            return CrudUtil.execute(sql, updatedResource.getTitle(), updatedResource.getPackageId(), updatedResource.getUrl(), url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<String> getTitleNames() throws SQLException {
        List<String> titles = new ArrayList<>();
        String query = "SELECT title FROM resources";

        try (ResultSet resultSet = CrudUtil.execute(query)) {
            while (resultSet.next()) {
                titles.add(resultSet.getString("title"));
            }
        }

        return titles;

    }

    @Override
    public String getResourceUrl(String title) {
        String sql = "SELECT url FROM resources WHERE title = ?";
        try (ResultSet resultSet = CrudUtil.execute(sql, title)) {
            if (resultSet.next()) {
                return resultSet.getString("url");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
