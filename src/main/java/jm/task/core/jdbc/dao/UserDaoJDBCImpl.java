package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.persistence.Table;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private final Connection connection = getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        PreparedStatement preparedStatement;
        final String sqlQuery = "CREATE TABLE User (id int NOT NULL UNIQUE AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age int)";

        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, "idea", "User", new String[] {"table"});
            if (!rs.next()) {
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.executeUpdate();
                System.out.println("Таблица создана");
            } else {
                System.out.println("Таблица уже существует");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        finally {
//            if (preparedStatement != null) {
//                try {
//                    preparedStatement.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
    }

    public void dropUsersTable() {
        PreparedStatement preparedStatement;
        final String sqlQuery = "DROP TABLE idea.user";

        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, "idea", "User", new String[] {"table"});
            if (rs.next()) {
                System.out.println("Таблица существует и будет удалена");
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.executeUpdate();
                System.out.println("Таблица удалена");
            } else {
                System.out.println("Таблица не удалена");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement;
        final String sqlQuery = "INSERT INTO idea.user (name, lastName, age) VALUES (?, ?, ?)";
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, "idea", "User", new String[] {"table"});
            if (rs.next()) {
                preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
                System.out.println("User с именем – " + name + " добавлен в таблицу");
            } else {
                System.out.println("Пользователь не добавлен");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement;
        String sqlQuery = "DELETE FROM idea.user WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Пользовутель удален");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sqlQuery = "SELECT ID, name, lastName, age FROM idea.user";

        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, "idea", "User", new String[] {"table"});
            if (rs.next()) {
                Statement statement = connection.createStatement();
                ResultSet resultUsers = statement.executeQuery(sqlQuery);
                while (resultUsers.next()) {
                    User user = new User();
                    user.setId(resultUsers.getLong("id"));
                    user.setName(resultUsers.getString("name"));
                    user.setLastName(resultUsers.getString("lastName"));
                    user.setAge(resultUsers.getByte("age"));
                    users.add(user);
                }
            } else {
                System.out.println("Таблица не найдена или удалена");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sqlQuery = "DELETE FROM idea.user";
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, "idea", "User", new String[] {"table"});
            if (rs.next()) {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                System.out.println("Таблица существует и будет отчищена");
                preparedStatement.executeUpdate();
                System.out.println("Таблица отчищена");
            } else {
                System.out.println("Таблица не найдена или удалена");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
