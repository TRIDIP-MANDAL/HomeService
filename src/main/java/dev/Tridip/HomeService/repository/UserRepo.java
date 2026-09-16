package dev.Tridip.HomeService.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

import dev.Tridip.HomeService.dto.user.UserRequestDto;
import dev.Tridip.HomeService.model.User;
import dev.Tridip.HomeService.mapper.UserMapper;
import dev.Tridip.HomeService.utils.DatabaseUtils;

// take raw data from user model and send it to service layer.

@Repository
public class UserRepo {
    // @Autowired -> field injection will not work here , datasource is declered as
    // final, hence we will do dependency injection through constructor
    private final DataSource dataSource;

    public UserRepo(DataSource dataSource) { // as this class is declared as bean, hence object will be handled by
                                             // springboot
        this.dataSource = dataSource;
    }

    public void create(User user) {
        String query = "INSERT INTO users (name, username, password, email, phone_number, role, address, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            
            Object []arr = UserMapper.createMapper(user);
            DatabaseUtils.saveData(query, arr, connection);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create user", e);
        }
    }

    public User findByMail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection connection = dataSource.getConnection()) {

            ResultSet rs = DatabaseUtils.getData(query, new Object[] { email }, connection);
            List<User> users = UserMapper.getMappedData(rs);
            return users.size() > 0 ? users.get(0) : null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during find user with email", e);
        }
    }

    public User findById(Long id){
        String query = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {

            ResultSet rs = DatabaseUtils.getData(query, new Object[] { id }, connection);
            List<User> users = UserMapper.getMappedData(rs);
            return users.size() > 0 ? users.get(0) : null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during find user with id", e);
        }
    }
    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        try (Connection connection = dataSource.getConnection();) {
            ResultSet rs = DatabaseUtils.getData(query, new Object[] {}, connection);
            return UserMapper.getMappedData(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public User update(Long id, UserRequestDto req) {
        String query = "UPDATE users SET name=?, username=?, phone_number=?, address=? WHERE id=?";
        try (Connection connection = dataSource.getConnection()) {

            Object [] arr = UserMapper.updateMapper(req, id);
            Boolean success = DatabaseUtils.saveData(query, arr, connection);
            if(success){
                return this.findById(id);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update user", e);
        }
    }


    public boolean isOwner(Long targetUserId, Long currentUserId) {
        String query = "SELECT id FROM users WHERE id = ? AND id = ?";
        try (Connection connection = dataSource.getConnection()) {
            ResultSet rs = DatabaseUtils.getData(query, new Object[] { targetUserId, currentUserId }, connection);
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to check user ownership", e);
        }
    }

    public void delete(Long id) {
      // reset the user data to annonyomous
    }
}
