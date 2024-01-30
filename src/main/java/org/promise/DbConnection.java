package org.promise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    public static Statement connectDB() throws SQLException {
        try {
                Connection connection = DriverManager.getConnection(Constants.DATABASEURL, Constants.USERNAME, Constants.PASSWORD);
                Statement statement = connection.createStatement();
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
