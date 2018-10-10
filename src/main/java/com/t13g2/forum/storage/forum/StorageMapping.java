package com.t13g2.forum.storage.forum;

import java.util.HashMap;

import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.User;

public class StorageMapping {
    private static StorageMapping ourInstance = new StorageMapping();

    public static StorageMapping getInstance() {
        return ourInstance;
    }

    private HashMap<Class, String> classMapFileName;

    private StorageMapping() {
        classMapFileName = new HashMap<>();
        classMapFileName.put(AnnouncementStorage.class, "ann");
        classMapFileName.put(Comment.class, "comment");
        classMapFileName.put(ForumThread.class, "threads");
        classMapFileName.put(Module.class, "modules");
        classMapFileName.put(User.class, "user");
    }

    public String getFileName(Class clazz) {
        return classMapFileName.get(clazz);
    }


}
