package com.drivesmart.dsms.dao.custom.impl;

import com.drivesmart.dsms.dao.custom.PackageDAO;
import com.drivesmart.dsms.dto.PackageDTO;
import com.drivesmart.dsms.dto.PackageEnrollmentDTO;
import com.drivesmart.dsms.entity.Package;
import com.drivesmart.dsms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageDAOImpl implements PackageDAO {

    @Override
    public ArrayList<Package> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Package> packages = new ArrayList<>();
        String sql = "SELECT * FROM package"; // Adjust the table name and columns as per your database schema
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            packages.add(new Package(
                    resultSet.getString("package_name"),
                    resultSet.getString("description"),
                    resultSet.getInt("duration"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("employee_id")
            ));
        }

        return packages;
    }

    @Override
    public boolean save(Package dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO package (package_name, description, duration, price, employee_id) VALUES (?, ?, ?, ?, ?)";
        try {
            return CrudUtil.execute(sql, dto.getPackageName(), dto.getDescription(), dto.getDuration(), dto.getPrice(), dto.getEmployeeId());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Package dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE package SET package_name = ?, description = ?, duration = ?, price = ?, employee_id = ? WHERE package_id = ?";
        try {
            return CrudUtil.execute(sql, dto.getPackageName(), dto.getDescription(), dto.getDuration(), dto.getPrice(), dto.getEmployeeId(), dto.getPackageId());
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM package WHERE package_id = ?";
        try {
            return CrudUtil.execute(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Package search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int getPackageCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM package"; // Adjust the table name and columns as per your database schema
        ResultSet resultSet = CrudUtil.execute(sql);
        resultSet.next();

        return resultSet.getInt(1);
    }

    @Override
    public int getPackageId(String name) throws SQLException {
        String sql = "SELECT package_id FROM package WHERE package_name = ?";
        try (ResultSet resultSet = CrudUtil.execute(sql, name)) {
            if (resultSet.next()) {
                return resultSet.getInt("package_id");
            }
        }
        return -1;
    }

    @Override
    public boolean enrollPackage(PackageEnrollmentDTO packageEnrollmentDTO) {
        String sql = "INSERT INTO package_enrollment (student_id, package_id, enrollment_date) VALUES (?, ?, ?)";
        try {
            return CrudUtil.execute(sql, packageEnrollmentDTO.getStudentId(), packageEnrollmentDTO.getPackageId(), packageEnrollmentDTO.getEnrollmentDate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getMostPopularPackage() {
        String query = "SELECT p.package_name, COUNT(e.package_id) AS enrollment_count FROM Package p JOIN package_enrollment e ON p.package_id = e.package_id GROUP BY p.package_name ORDER BY enrollment_count DESC LIMIT 1;";
        try (ResultSet resultSet = CrudUtil.execute(query)) {
            if (resultSet.next()) {
                String result = resultSet.getString("package_name");
                if(result != null){
                    return result;
                }else return " ";            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return " ";
    }

    @Override
    public String getLeastPopularPackage() {
        String query = "SELECT p.package_name, COUNT(e.package_id) AS enrollments FROM Package p LEFT JOIN package_enrollment e ON p.package_id = e.package_id GROUP BY p.package_id, p.package_name ORDER BY enrollments ASC LIMIT 1;";
        try (ResultSet resultSet = CrudUtil.execute(query)) {
            if (resultSet.next()) {
                String result = resultSet.getString("package_name");
                if(result != null){
                    return result;
                }else return " ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return " ";
    }

    @Override
    public List<String> getStudentsNames() {
        List<String> students = new ArrayList<>();
        String sql = "SELECT name FROM student JOIN package_enrollment pe on student.student_id = pe.student_id WHERE pe.status = 'active'";
        try (ResultSet resultSet = CrudUtil.execute(sql)) {
            while (resultSet.next()) {
                students.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public List<String> getPackageNames() {
        List<String> packages = new ArrayList<>();
        String sql = "SELECT package_name FROM package";
        try (ResultSet resultSet = CrudUtil.execute(sql)) {
            while (resultSet.next()) {
                packages.add(resultSet.getString("package_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }
}
