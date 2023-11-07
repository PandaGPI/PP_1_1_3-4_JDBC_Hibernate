package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        final String sqlQuery = "CREATE TABLE IF NOT EXISTS idea.user " +
                "(id BIGINT NOT NULL UNIQUE AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age TINYINT)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void dropUsersTable() {
        final String sqlQuery = "DROP TABLE IF EXISTS idea.user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Таблица  не удалена");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String sqlQuery = "INSERT INTO idea.user (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в таблицу");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void removeUserById(long id) {
        final String sqlQuery = "DELETE FROM idea.user WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользовутель удален");
        } catch (SQLException e) {
            System.out.println("Пользователь не удален из таблицы");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        final String sqlQuery = "SELECT ID, name, lastName, age FROM idea.user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultUsers = preparedStatement.executeQuery(sqlQuery);
            while (resultUsers.next()) {
                User user = new User();
                user.setId(resultUsers.getLong("id"));
                user.setName(resultUsers.getString("name"));
                user.setLastName(resultUsers.getString("lastName"));
                user.setAge(resultUsers.getByte("age"));
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return users;
    }

    public void cleanUsersTable() {
        final String sqlQuery = "DELETE FROM idea.user";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Таблица не отчищена");
        }
    }
}
