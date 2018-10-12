package com.t13g2.forum.storage.forum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 */
public class RunningId {
    public static final String FILE_NAME = "i";
    private static RunningId ourInstance = new RunningId();

    private RunningId() {

    }

    public static RunningId getInstance() {
        return ourInstance;
    }

    /**
     * @param currentId
     */
    private void writeToFile(int currentId) {
        try {
            Files.write(Paths.get(FILE_NAME), String.valueOf(currentId + 1).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    private int readFromFile() {
        try {
            String text = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
            return Integer.valueOf(text);
        } catch (IOException e) {
            //TODO: handle error
            e.printStackTrace();
        }
        return -1;
    }

    private void init() {
        if (!Files.exists(Paths.get(FILE_NAME))) {
            writeToFile(0);
        }
    }

    /**
     * @return
     */
    public int nextId() {
        int id = readFromFile();
        writeToFile(id);
        return id;
    }
}
