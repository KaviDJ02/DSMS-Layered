package com.drivesmart.dsms.bo.custom;

import com.drivesmart.dsms.bo.SuperBO;
import com.drivesmart.dsms.dto.PackageDTO;
import com.drivesmart.dsms.dto.PackageEnrollmentDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PackageBO extends SuperBO {
    ArrayList<PackageDTO> getAllPackages() throws SQLException, ClassNotFoundException;
    boolean savePackage(PackageDTO packageDTO) throws SQLException, ClassNotFoundException;
    boolean updatePackage(PackageDTO packageDTO) throws SQLException, ClassNotFoundException;
    boolean deletePackage(String id) throws SQLException, ClassNotFoundException;
    PackageDTO searchPackage(String id) throws SQLException, ClassNotFoundException;
    int getPackageCount() throws SQLException, ClassNotFoundException;
    String getPackageId(String name) throws SQLException, ClassNotFoundException;
    boolean enrollPackage(PackageEnrollmentDTO packageEnrollmentDTO) throws SQLException, ClassNotFoundException;
    String getMostPopularPackage() throws SQLException, ClassNotFoundException;
    String getLeastPopularPackage() throws SQLException, ClassNotFoundException;
    List<String> getStudentsNames() throws SQLException, ClassNotFoundException;
    List<String> getPackageNames() throws SQLException, ClassNotFoundException;
}