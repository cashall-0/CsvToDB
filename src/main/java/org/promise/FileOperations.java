package org.promise;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

public class FileOperations {
    public static Scanner scanFile(Path filePath) {
        try {
                FileReader fileReader = new FileReader(filePath.toFile());
                Scanner sc = new Scanner(fileReader);
            return sc;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Path getFileName() {
        System.out.println("Enter file name (e.g \"notes.csv\") :  ");
        Scanner scanner = new Scanner(System.in);
        String userFile = scanner.nextLine();
        Path filePath = Path.of("./" + userFile);
        boolean exists = Files.exists(filePath);
        if (!exists) {
            System.out.println("The file does not exist!!!!!!             " + filePath);
            System.exit(0);
        }
        return filePath;
    }

    public void csvToDBOperation(Statement statement, Scanner sc) throws SQLException {
        String[] headerList = new String[0];
        int dataCount = 0;
        statement.executeUpdate("DROP TABLE IF EXISTS csv_to_db ");
        String sqlCreate = "CREATE TABLE csv_to_db " +
                "(id INTEGER AUTO_INCREMENT PRIMARY KEY, ";
        sc.useDelimiter(",");   //sets the delimiter pattern
        String[] cc;
        boolean header = true;
        StringBuilder headSql = new StringBuilder();
        LocalTime start = LocalTime.of(0,0,0);
        while (sc.hasNextLine())  //returns a boolean value
        {
            //find and returns the next complete token from this scanner
            cc = sc.nextLine().split(",");
            if (header) {
                String[] headerListTemp = new String[cc.length];
                for (int i = 0; i < cc.length; i++) {
                    headSql.append(cc[i].replaceAll("\\s", "_").toLowerCase()).append("_ VARCHAR(255),");
                    headerListTemp[i] = cc[i].replaceAll("\\s", "_").toLowerCase() + "_";
                }
                String f = sqlCreate + headSql.deleteCharAt(headSql.length() - 1) + ");";
                statement.executeUpdate(f);
                System.out.println("Created table in given database...");
                System.out.println("============================================ \nInserting data into your database....");
                start = LocalTime.now();
                headerList = headerListTemp;
                header = false;
            } else {
                StringBuilder insertStr = new StringBuilder("insert into csv_to_db() values(");
                StringBuilder heads = new StringBuilder();
                for (int i = 0; i < headerList.length; i++
                ) {
                    insertStr.append("'").append(cc[i].replaceAll("'", "`")).append("', ");
                    heads.append(headerList[i]).append(",");
                }
                heads.deleteCharAt(heads.length() - 1);
                insertStr.insert(22, heads);
                int result = statement.executeUpdate(insertStr.deleteCharAt(insertStr.length() - 2) + ")");
                dataCount += result;
            }
        }
        System.out.println(dataCount + " rows of Data inserted into Database with table name \"csv_to_db\"!!!");
        LocalTime end = LocalTime.now();
        Duration between = Duration.between(start, end);
        System.out.println("Finished in " + between.toMinutesPart()+ " min, " + between.toSecondsPart()+"."+ between.toNanosPart()+" secs");
    }
}
