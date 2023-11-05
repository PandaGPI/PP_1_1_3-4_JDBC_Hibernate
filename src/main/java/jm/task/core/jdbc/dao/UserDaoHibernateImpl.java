package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {

            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) {
                    Transaction transaction = null;
                    try {
                        DatabaseMetaData dbm = connection.getMetaData();
                        ResultSet tables = dbm.getTables(null, "idea", "User", new String[]{"table"});
                        final String sql = "CREATE TABLE user " +
                                "(id BIGINT NOT NULL UNIQUE AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age TINYINT)";
                        if (!tables.next()) {
                            transaction = session.beginTransaction();
                            session.createSQLQuery(sql).executeUpdate();
                            session.getTransaction().commit();
                            session.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        assert transaction != null;
                        transaction.rollback();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Таблица не создана");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {

            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) {
                    Transaction transaction = null;
                    try {
                        DatabaseMetaData dbm = connection.getMetaData();
                        ResultSet tables = dbm.getTables(null, "idea", "User", new String[]{"table"});
                        final String sql = "DROP Table User";
                        if (tables.next()) {
                            transaction = session.beginTransaction();
                            session.createSQLQuery(sql).executeUpdate();
                            session.getTransaction().commit();
                            session.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        assert transaction != null;
                        transaction.rollback();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            assert transaction != null;
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        final String hql = "DELETE from User where id = :user";
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery(hql).setParameter("user", id).executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Таблицы не существует");
            assert transaction != null;
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        final String hql = "FROM User";
        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery(hql).list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Таблицы не существует");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) {
                    Transaction transaction = null;
                    try {
                        DatabaseMetaData dbm = connection.getMetaData();
                        ResultSet tables = dbm.getTables(null, "idea", "User", new String[]{"table"});
                        final String hql = "DELETE from User";
                        if (tables.next()) {
                            transaction = session.beginTransaction();
                            session.createSQLQuery(hql).executeUpdate();
                            session.getTransaction().commit();
                            session.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Таблицы не существует");
                        assert transaction != null;
                        transaction.rollback();
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("Таблицы не существует");
        }
    }
}
