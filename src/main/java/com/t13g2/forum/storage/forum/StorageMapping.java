package com.t13g2.forum.storage.forum;

import java.util.HashMap;

public class StorageMapping {
    private static StorageMapping ourInstance = new StorageMapping();

    public static StorageMapping getInstance() {
        return ourInstance;
    }

    private HashMap<Class, String> classMapFileName;

    private StorageMapping() {
        classMapFileName = new HashMap<>();
        classMapFileName.put(AnnouncementStorage.class, "ann");
        classMapFileName.put(CommentStorage.class, "comment");
        classMapFileName.put(ForumThreadStorage.class, "threads");
        classMapFileName.put(ModuleStorage.class, "modules");
        classMapFileName.put(UserStorage.class, "user");
    }

    public String getFileName(Class clazz) {
        return classMapFileName.get(clazz);
    }


}
