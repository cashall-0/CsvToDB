package org.promise;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileOperations fileOperations = new FileOperations();
        DbOperations dbOperations = new DbOperations();
        Path fileName = fileOperations.getFileName();
        try {
            List<FileDataObject> fileDataObjects = fileOperations.returnDataList(FileOperations.scanFile(fileName));
            if(dbOperations.createTable(fileDataObjects.get(0),DbOperations.connectDB())) {
                int insertedData = dbOperations.allToDB(fileDataObjects, DbOperations.connectDB());
                if(insertedData > 0)
                    System.out.println(insertedData + " rows of Data inserted into Database with table name \"csv_to_db\"!!!");
            } else
                System.out.println("Couldn't create the database table!!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                DbOperations.connectDB().close();
                FileOperations.scanFile(fileName).close();
            } catch (SQLException e) {
                System.out.println("failed to close!!!");
            }
        }
    }
}