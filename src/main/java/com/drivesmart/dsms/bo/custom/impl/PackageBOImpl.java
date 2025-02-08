package com.drivesmart.dsms.bo.custom.impl;

import com.drivesmart.dsms.bo.custom.PackageBO;
import com.drivesmart.dsms.dao.DAOFactory;
import com.drivesmart.dsms.dao.custom.PackageDAO;
import com.drivesmart.dsms.dto.PackageDTO;
import com.drivesmart.dsms.dto.PackageEnrollmentDTO;
import com.drivesmart.dsms.entity.Package;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageBOImpl implements PackageBO {
    private final PackageDAO packageDAO = (PackageDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PACKAGE);

    public PackageBOImpl() throws SQLException {
    }

    @Override
    public ArrayList<PackageDTO> getAllPackages() throws SQLException, ClassNotFoundException {
        ArrayList<Package> packages = packageDAO.getAll();
        ArrayList<PackageDTO> packageDTOs = new ArrayList<>();
        for (Package pkg : packages) {
            packageDTOs.add(new PackageDTO(pkg.getPackageId(), pkg.getPackageName(), pkg.getDescription(), pkg.getDuration(), pkg.getPrice(), pkg.getEmployeeId()));
        }
        return packageDTOs;
    }

    @Override
    public boolean savePackage(PackageDTO packageDTO) throws SQLException, ClassNotFoundException {
        Package pkg = new Package(packageDTO.getPackageId(), packageDTO.getPackageName(), packageDTO.getDescription(), packageDTO.getDuration(), packageDTO.getPrice(), packageDTO.getEmployeeId());
        return packageDAO.save(pkg);
    }

    @Override
    public boolean updatePackage(PackageDTO packageDTO) throws SQLException, ClassNotFoundException {
        Package pkg = new Package(packageDTO.getPackageId(), packageDTO.getPackageName(), packageDTO.getDescription(), packageDTO.getDuration(), packageDTO.getPrice(), packageDTO.getEmployeeId());
        return packageDAO.update(pkg);
    }

    @Override
    public boolean deletePackage(String id) throws SQLException, ClassNotFoundException {
        return packageDAO.delete(id);
    }

    @Override
    public PackageDTO searchPackage(String id) throws SQLException, ClassNotFoundException {
        Package pkg = packageDAO.search(id);
        if (pkg != null) {
            return new PackageDTO(pkg.getPackageId(), pkg.getPackageName(), pkg.getDescription(), pkg.getDuration(), pkg.getPrice(), pkg.getEmployeeId());
        }
        return null;
    }

    @Override
    public int getPackageCount() throws SQLException, ClassNotFoundException {
        return packageDAO.getPackageCount();
    }

    @Override
    public String getPackageId(String name) throws SQLException, ClassNotFoundException {
        return String.valueOf(packageDAO.getPackageId(name));
    }

    @Override
    public boolean enrollPackage(PackageEnrollmentDTO packageEnrollmentDTO) throws SQLException, ClassNotFoundException {
        return packageDAO.enrollPackage(packageEnrollmentDTO);
    }

    @Override
    public String getMostPopularPackage() throws SQLException, ClassNotFoundException {
        return packageDAO.getMostPopularPackage();
    }

    @Override
    public String getLeastPopularPackage() throws SQLException, ClassNotFoundException {
        return packageDAO.getLeastPopularPackage();
    }

    @Override
    public List<String> getStudentsNames() throws SQLException, ClassNotFoundException {
        return packageDAO.getStudentsNames();
    }

    @Override
    public List<String> getPackageNames() throws SQLException, ClassNotFoundException {
        return packageDAO.getPackageNames();
    }
}