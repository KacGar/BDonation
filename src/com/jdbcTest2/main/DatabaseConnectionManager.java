package com.jdbcTest2.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DatabaseConnectionManager {

    private DatabaseConnectionManager(){}

    private final static ResourceBundle dbProperties = ResourceBundle.getBundle("com.jdbcTest2.main.database");
    private final static String url = dbProperties.getString("jdbc.url");
    private final static String username = dbProperties.getString("jdbc.user");
    private final static String password = dbProperties.getString("jdbc.password");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }
}
