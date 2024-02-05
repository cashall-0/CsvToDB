package org.promise;


public class FileDataObject {
    //this is an object that holds a row data and row number
    private int row;
    private int lengthOfData;
    private String [] data = new String[lengthOfData];

    public FileDataObject(int row,int lengthOfData) {
        this.row = row;
        this.lengthOfData = lengthOfData;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String [] getData() {
        return data;
    }

    public void setData(String [] data) {
        this.data = data;
    }

    public int getLengthOfData() {
        return lengthOfData;
    }

    public void setLengthOfData(int lengthOfData) {
        this.lengthOfData = lengthOfData;
    }
}
