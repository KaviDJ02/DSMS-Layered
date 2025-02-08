package com.drivesmart.dsms.util;

import com.drivesmart.dsms.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CardUtil {

    public static int getRowCount(String query) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        int rowCount = 0;
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement.executeQuery(query)) {
            rowCount = getRowCount(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public static int getRowCount(ResultSet resultSet) {
        int size = 0;
        try {
            resultSet.last();
            size = resultSet.getRow();
            resultSet.beforeFirst();
        } catch (SQLException e) {
            return 0;
        }
        return size;
    }

}