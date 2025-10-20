package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Util util = new Util();
        try (Connection connection = util.getConnection()){
            UserDao dao = new UserDaoJDBCImpl(connection);
            UserService service = new UserServiceImpl(dao);
            service.createUsersTable();
            service.saveUser("Anastasia", "Makarova", (byte) 21);
            service.saveUser("Davyd", "Frank", (byte) 20);
            service.saveUser("Kirill", "Somov", (byte) 25);
            service.saveUser("Leonid", "Sergeev", (byte) 23);
            service.getAllUsers();
        service.cleanUsersTable();
        service.dropUsersTable();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
