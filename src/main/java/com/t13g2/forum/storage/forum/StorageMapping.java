//@@author Meowzz95
package com.t13g2.forum.storage.forum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Provides mapping of Java entity class and data file path
 */
public class StorageMapping {
    private static StorageMapping ourInstance;
    private static String dataFolder;
    private HashMap<Class, String> classMapFileName;

    private StorageMapping() {
        initDataFolder();
        classMapFileName = new HashMap<>();
        classMapFileName.put(AnnouncementStorage.class, Paths.get(dataFolder, "ann.json").toString());
        classMapFileName.put(CommentStorage.class, Paths.get(dataFolder, "comment.json").toString());
        classMapFileName.put(ForumThreadStorage.class, Paths.get(dataFolder, "threads.json").toString());
        classMapFileName.put(ModuleStorage.class, Paths.get(dataFolder, "modules.json").toString());
        classMapFileName.put(UserStorage.class, Paths.get(dataFolder, "user.json").toString());
        classMapFileName.put(RunningId.class, Paths.get(dataFolder, "i").toString());
    }

    public static StorageMapping getInstance() {
        if (ourInstance == null) {
            ourInstance = new StorageMapping();
        }
        return ourInstance;
    }

    /**
     * Gets data file path based given Java class
     *
     * @param clazz of type Class
     * @return String
     */
    public String getFileName(Class clazz) {
        return classMapFileName.get(clazz);
    }

    /**
     * Adds mapping infomation
     *
     * @param clazz of type Class
     * @param path  of type String
     */
    public void addMapping(Class clazz, String path) {
        classMapFileName.put(clazz, path);
    }


    /**
     * Checks the existence of data folder and creates it if not exist
     */
    private void initDataFolder() {
        dataFolder = Paths.get(Paths.get("").toString(), "forumData").toString();
        if (!Files.exists(Paths.get(dataFolder))) {
            try {
                Files.createDirectory(Paths.get(dataFolder));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
