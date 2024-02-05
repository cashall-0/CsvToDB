package org.promise;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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

    public List<FileDataObject> returnDataList(Scanner sc){
        List<FileDataObject> fullDataList = new ArrayList<>();
        int counter  = 1;
        System.out.println("Trying to write data into a list object");
        while (sc.hasNextLine())  //returns a boolean value
        {
            String[] rowHolder = sc.nextLine().split(",", -1);
            FileDataObject fileDataObject = new FileDataObject(counter, rowHolder.length);
            fileDataObject.setData(rowHolder);
            fullDataList.add(fileDataObject);
        }
        System.out.println("finished writing data into list");
        return fullDataList;

    }

}
