package dev.Tridip.HomeService.repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dev.Tridip.HomeService.model.User;
import dev.Tridip.HomeService.mapper.UserMapper;

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
        try (
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getRole());
            statement.setString(7, user.getAddress());
            statement.setBoolean(8, user.getIsActive());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create user", e);
        }
    }

    public User findByMail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (
                Connection connection = dataSource.getConnection()) {
            List<User> users = UserMapper.getMappedData(query, dataSource, new String[] { email }, connection);
            return users.size() > 0 ? users.get(0) : null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during find user with email", e);
        }
    }


    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        try (
                Connection connection = dataSource.getConnection();) {
            return UserMapper.getMappedData(query, dataSource, new String[] {}, connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void update(){

    }

    public  void delete(){
        
    }
}
