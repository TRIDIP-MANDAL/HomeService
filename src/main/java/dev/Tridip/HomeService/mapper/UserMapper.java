package dev.Tridip.HomeService.mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import dev.Tridip.HomeService.model.User;
import dev.Tridip.HomeService.utils.DatabaseUtils;

public class UserMapper {
    public static List<User> getMappedData(String query,DataSource dataSource, String []arr, Connection connection )throws SQLException{
        ResultSet rs = DatabaseUtils.getData(query, dataSource, arr, connection);
        List<User> list = new ArrayList<>();
        while(rs.next()){
        User user = new User(
              rs.getLong("id"),
              rs.getString("name"),
              rs.getString("username"),
              rs.getString("password"),
              rs.getString("email"),
              rs.getString("phone_number"),
              rs.getString("role"),
              rs.getString("address"),
              rs.getTimestamp("created_at")!= null ? rs.getTimestamp("created_at").toLocalDateTime():null,
              rs.getBoolean("is_active")
        );
        list.add(user);
        }
      return list;
    }
}
