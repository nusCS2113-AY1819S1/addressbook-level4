package com.t13g2.forum.storage.forum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 *
 */
public class StorageMapping {
    private static StorageMapping ourInstance;
    private static String dataFolder;
    private HashMap<Class, String> classMapFileName;

    private StorageMapping() {
        initDataFolder();
        classMapFileName = new HashMap<>();
        classMapFileName.put(AnnouncementStorage.class, Paths.get(dataFolder, "ann").toString());
        classMapFileName.put(CommentStorage.class, Paths.get(dataFolder, "comment").toString());
        classMapFileName.put(ForumThreadStorage.class, Paths.get(dataFolder, "threads").toString());
        classMapFileName.put(ModuleStorage.class, Paths.get(dataFolder, "modules").toString());
        classMapFileName.put(UserStorage.class, Paths.get(dataFolder, "user").toString());
        classMapFileName.put(RunningId.class, Paths.get(dataFolder, "i").toString());
    }

    public static StorageMapping getInstance() {
        if (ourInstance == null) {
            ourInstance = new StorageMapping();
        }
        return ourInstance;
    }

    public String getFileName(Class clazz) {
        return classMapFileName.get(clazz);
    }

    /**
     *
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
