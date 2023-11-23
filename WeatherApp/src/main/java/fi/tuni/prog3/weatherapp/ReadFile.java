/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author oonam
 */
public class ReadFile implements iReadAndWriteToFile {

    private String dataToWrite; // Data to be written to the file

    public void setDataToWrite(String data) {
        this.dataToWrite = data;
    }

    @Override
    public String readFromFile(String fileName) throws IOException {
        Path filePath = Path.of(fileName);
        return Files.readString(filePath);
    }

    @Override
    public boolean writeToFile(String fileName) throws IOException {
        if (dataToWrite == null) {
            throw new IllegalStateException("No data set to write.");
        }
        Path filePath = Path.of(fileName);
        Files.writeString(filePath, dataToWrite, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        return true;
    }
}
