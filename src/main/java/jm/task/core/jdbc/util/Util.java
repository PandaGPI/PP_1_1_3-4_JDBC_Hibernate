package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

public class Util {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/idea";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASS = "kp0kyc86ufsin";

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASS);
            Class.forName(DB_DRIVER);
            System.out.println("Database connected!");
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return connection;
    }



//    // Connect to MySQL
//    public static Connection getMySQLConnection() throws SQLException,
//            ClassNotFoundException {
//
//        final String hostName = "localhost";
//        final String dbName = "idea";
//        final String userName = "root";
//        final String password = "kp0kyc86ufsin";
//
//        return getMySQLConnection(hostName, dbName, userName, password);
//    }
//
//    public static Connection getMySQLConnection(String hostName, String dbName,
//                                                String userName, String password) throws SQLException, ClassNotFoundException {
//
//        Class.forName("com.mysql.jdbc.Driver");
//        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
//
//        try (Connection conn = DriverManager.getConnection(connectionURL, userName, password)) {
//            System.out.println("Database connected!");
//            return conn;
//        } catch (SQLException e) {
//            throw new IllegalStateException("Cannot connect the database!", e);
//        }
//    }
}
