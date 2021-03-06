package com.epam.javalab.hotelproject.service;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseServiceImplTest {
    private final DatabaseService databaseService = DatabaseServiceImpl.getInstance();

    @Test
    public void test() throws Exception {
        Connection connection = databaseService.takeConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM sql11188080.test");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
        }
        resultSet.close();
        statement.close();
        databaseService.closeConnection(connection);
        databaseService.dispose();
    }
}