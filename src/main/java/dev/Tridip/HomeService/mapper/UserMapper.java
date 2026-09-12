package dev.Tridip.HomeService.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.Tridip.HomeService.dto.user.UserRequestDto;
import dev.Tridip.HomeService.model.User;

public class UserMapper {
  public static Object[] createMapper(User user){
    return new Object[] {
                    user.getName(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getRole(),
                    user.getAddress(),
                    user.getIsActive()
            };
  }
  public static List<User> getMappedData(ResultSet rs) throws SQLException {
    List<User> list = new ArrayList<>();
    while (rs.next()) {
      User user = new User(
          rs.getLong("id"),
          rs.getString("name"),
          rs.getString("username"),
          rs.getString("password"),
          rs.getString("email"),
          rs.getString("phone_number"),
          rs.getString("role"),
          rs.getString("address"),
          rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
          rs.getBoolean("is_active"));
      list.add(user);
    }
    return list;
  }

  public static Object[] updateMapper(UserRequestDto user, Long id) {
    return new Object[] {
        user.getName(),
        user.getUsername(),
        user.getPhoneNumber(),
        user.getAddress(),
        id
    };
  }
}
