package iwish.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseController {
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/iwish", "root", "1234");
    }

    public static void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
