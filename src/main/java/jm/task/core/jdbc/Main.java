package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Anastasia", "Makarova", (byte) 21);
        service.saveUser("Davyd", "Frank", (byte) 20);
        service.saveUser("Kirill", "Somov", (byte) 25);
        service.saveUser("Leonid", "Sergeev", (byte) 23);
        service.getAllUsers();
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
