package org.promise;

import java.nio.file.Path;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        FileOperations fileOperations = new FileOperations();
        Path fileName = fileOperations.getFileName();
        try {
            fileOperations.csvToDBOperation(DbConnection.connectDB(), FileOperations.scanFile(fileName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                DbConnection.connectDB().close();
                FileOperations.scanFile(fileName).close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}