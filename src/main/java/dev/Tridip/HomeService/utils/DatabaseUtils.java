package dev.Tridip.HomeService.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {
    public static ResultSet getData(String query, Object[] arr, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < arr.length; i++) {
            statement.setObject(i + 1, arr[i]);
        }
        return statement.executeQuery();
    }

    public static boolean saveData(String query, Object[] arr, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < arr.length; i++) {
            statement.setObject(i + 1, arr[i]);
        }
        return statement.executeUpdate() > 0;
    }

    public static Long saveAndReturnKey(String query, Object[] arr, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < arr.length; i++) {
            statement.setObject(i + 1, arr[i]);
        }
        int rows = statement.executeUpdate();
        if (rows == 0) throw new SQLException("Insert failed, no rows affected");
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            throw new SQLException("Insert failed, no generated key returned");
        }
    }
}
