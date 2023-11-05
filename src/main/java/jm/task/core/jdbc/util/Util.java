package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/idea";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASS = "kp0kyc86ufsin";
    private static final String DB_DIALECT = "org.hibernate.dialect.MySQL5Dialect";

    //JDBC connection
    public static Connection getConnection() {
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

    //Hibernate connection
    static {
//        Configuration configuration = new Configuration();

        Properties properties = new Properties();
        properties.put(Environment.DRIVER, DB_DRIVER);
        properties.put(Environment.URL, DB_URL);
        properties.put(Environment.USER, DB_USERNAME);
        properties.put(Environment.PASS, DB_PASS);
        properties.put(Environment.DIALECT, DB_DIALECT);

        properties.put(Environment.SHOW_SQL, "true");

        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        properties.put(Environment.HBM2DDL_AUTO, "validate");

//        configuration.setProperties(properties);

//        configuration.addAnnotatedClass(User.class);

        final StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(properties).build();
        try {
            Metadata metadata =
//                    configuration.buildSessionFactory();
//                    configuration.buildSessionFactory(serviceRegistry);
                    new MetadataSources(serviceRegistry).addAnnotatedClass(User.class).getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();

        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
