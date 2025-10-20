package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    private static final String CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS users (" +
            "id INT PRIMARY KEY AUTO_INCREMENT," +
            "name VARCHAR(50) NOT NULL," +
            "last_name VARCHAR(50) NOT NULL," +
            "age INT NOT NULL)";
    private static final String DROP_SQL =
            "DROP TABLE IF EXISTS users";
    private static final String SAVE_SQL =
            "INSERT INTO users (name, last_name, age) values (?, ?, ?)";
    private static final String DELETE_SQL =
            "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_SQL =
            "SELECT id, name, last_name, age FROM users";
    private static final String CLEAN_SQL = "TRUNCATE TABLE users";
    public UserDaoJDBCImpl(Connection connection) {
        this.connection = connection;
    }
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании таблицы users.\n", e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_SQL);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении таблицы users.\n", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении пользователя.\n", e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(DELETE_SQL)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пользователя.\n", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка пользователей.\n", e);
        }
        System.out.println(users);
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.execute(CLEAN_SQL);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при очистке таблицы users.\n", e);
        }
    }
}
