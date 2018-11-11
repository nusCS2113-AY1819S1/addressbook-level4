//@@author Meowzz95
package com.t13g2.forum.storage.forum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Provides auto increment integer Ids for entity classes
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
     * Saves next Id
     * @param currentId
     */
    private void writeToFile(int currentId) {
        try {
            Files.write(Paths.get(StorageMapping.getInstance().getFileName(RunningId.class)),
                String.valueOf(currentId + 1).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads current Id
     * @return
     */
    private int readFromFile() {
        try {
            String text = new String(Files.readAllBytes(Paths.get(StorageMapping.getInstance()
                .getFileName(RunningId.class))));
            return Integer.valueOf(text);
        } catch (IOException e) {
            //TODO: handle error
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Checks the existence of id record, if not, creates one with next Id 0
     */
    private void init() {
        if (!Files.exists(Paths.get(StorageMapping.getInstance().getFileName(RunningId.class)))) {
            writeToFile(0);
        }
    }

    /**
     * Gets Id for an entity
     * @return next auto incremented id
     */
    public int nextId() {
        int id = readFromFile();
        writeToFile(id);
        return id;
    }
}
