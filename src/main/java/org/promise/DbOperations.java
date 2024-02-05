package org.promise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DbOperations {
    public static Statement connectDB() throws SQLException {
        try {
                Connection connection = DriverManager.getConnection(System.getenv("DB_URL"), System.getenv("DB_USERNAME"), System.getenv("DB_PASSWORD"));
                Statement statement = connection.createStatement();
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int allToDB(List<FileDataObject> fullDataList, Statement statement) throws SQLException {
        int dataCount = 0;
        FileDataObject headData = fullDataList.get(0);
        for (FileDataObject row: fullDataList) {
            if (dataCount == 0 )
                dataCount++;
            else {
                StringBuilder insertStr = new StringBuilder("INSERT INTO csv_to_db() VALUES(");

                for (int i = 0; i < headData.getData().length; i++) {
                    insertStr.append("'").append(row.getData()[i].replaceAll("'", "`")).append("',");
                }
                insertStr.insert(22, getHeaderForQuery(headData));
//                System.out.println(insertStr);
                int result = statement.executeUpdate(insertStr.deleteCharAt(insertStr.length() - 1) + ")");
                dataCount += result;
            }
        }
        dataCount--;
        return  dataCount;

    }
    public boolean createTable(FileDataObject fileDataObject, Statement statement) throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS csv_to_db ");
        String sqlCreate = "CREATE TABLE csv_to_db " +
                "(id INTEGER AUTO_INCREMENT PRIMARY KEY, ";
        StringBuilder headSql = new StringBuilder();
        for (int i = 0; i < fileDataObject.getLengthOfData(); i++) {
            headSql.append(fileDataObject.getData()[i].replaceAll("\\s", "_").toLowerCase()).append("_ VARCHAR(255),");
        }
        String headerString = sqlCreate + headSql.deleteCharAt(headSql.length() - 1) + ");";
        int numberOfInsertion = statement.executeUpdate(headerString);
        System.out.println("Created table in given database...");
        System.out.println("============================================ \nInserting data into your database....");
       return  numberOfInsertion == 0;
    }
    private String getHeaderForQuery(FileDataObject fileDataObject){
        StringBuilder headerQuery = new StringBuilder();
        for (String data: fileDataObject.getData()) {
            headerQuery.append(data.replaceAll("\\s", "_").toLowerCase()).append("_,");
        }
        headerQuery.deleteCharAt(headerQuery.length()-1);
        return String.valueOf(headerQuery);
    }
}
