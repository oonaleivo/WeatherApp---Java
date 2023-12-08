
package fi.tuni.prog3.weatherapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
 * Test class for the {@link ReadFile} class.
 */
public class ReadFileTest {
    
    /**
     * Tests the writeToFile and readFromFile methods of the ReadFile class.
     */
    @Test
    public void testWriteAndReadFromFile() {
        // Arrange
        ReadFile readFile = new ReadFile();
        String testData = "Test data for file write and read.";

        // Act
        readFile.setDataToWrite(testData);
        boolean writeSuccess = false;
        boolean readSuccess = false;

        try {
            writeSuccess = readFile.writeToFile("testFile.txt");
            readSuccess = readFile.readFromFile("weatherData");
        } catch (IOException e) {
        }

        // Assert
        assertTrue(writeSuccess, "File write operation should be successful");
        assertTrue(readSuccess, "File read operation should be successful");
    }

    /**
     * Cleans up folders and files created during the test.
     */
    @AfterAll
    public static void cleanup() {
        // Clean up folders and files created during the test
        try {
            Files.walkFileTree(Path.of("weatherData"), EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor());
        } catch (IOException e) {
        }
    }
    
    /**
     * SimpleFileVisitor implementation for deleting files and directories.
     */
    private static class SimpleFileVisitor extends java.nio.file.SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
        }
        
        /**
         * Deletes a visited directory after all its entries have been visited.
         *
         * @param dir The path of the directory to be deleted.
         * @param exc An I/O exception that occurred during the visit.
         * @return The file visit result.
         * @throws IOException If an I/O error occurs.
         */
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
        }
    }
}
