package com.drivesmart.dsms.dao.custom;

import com.drivesmart.dsms.dao.CrudDAO;
import com.drivesmart.dsms.dto.PackageEnrollmentDTO;
import com.drivesmart.dsms.entity.Package;

import java.sql.SQLException;
import java.util.List;

public interface PackageDAO extends CrudDAO<Package> {
    int getPackageCount() throws SQLException;
    int getPackageId(String name) throws SQLException;
    boolean enrollPackage(PackageEnrollmentDTO packageEnrollmentDTO) throws SQLException;
    String getMostPopularPackage() throws SQLException;
    String getLeastPopularPackage() throws SQLException;
    List<String> getStudentsNames() throws SQLException;
    List<String> getPackageNames() throws SQLException;
}

