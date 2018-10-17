package com.t13g2.forum.storage.forum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 */
public class RunningId {
    private static RunningId ourInstance = new RunningId();

    private RunningId() {
        init();
    }

    public static RunningId getInstance() {
        return ourInstance;
    }

    /**
     * @param currentId
     */
    private void writeToFile(int currentId) {
        try {
            Files.write(Paths.get(StorageMapping.getInstance().getFileName(RunningId.class)), String.valueOf(currentId + 1).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    private int readFromFile() {
        try {
            String text = new String(Files.readAllBytes(Paths.get(StorageMapping.getInstance().getFileName(RunningId.class))));
            return Integer.valueOf(text);
        } catch (IOException e) {
            //TODO: handle error
            e.printStackTrace();
        }
        return -1;
    }

    private void init() {
        if (!Files.exists(Paths.get(StorageMapping.getInstance().getFileName(RunningId.class)))) {
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
