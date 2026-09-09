package dev.Tridip.HomeService.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DatabaseUtils {
    public static ResultSet getData(String query, DataSource dataSource, String []arr, Connection connection ) throws SQLException {
       PreparedStatement statement = connection.prepareStatement(query);
       for(int i = 0; i<arr.length; i++){
         statement.setString(i+1, arr[i]);
       }
       return statement.executeQuery();
    } 
}