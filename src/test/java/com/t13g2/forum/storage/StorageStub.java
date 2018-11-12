//@@author Meowzz95
package com.t13g2.forum.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.t13g2.forum.storage.forum.IStorage;

/**
 * Defines a stub for Storage
 */
public class StorageStub implements IStorage {
    private static StorageStub instance;

    private HashMap<Class, Object> storage;

    private StorageStub() {
        storage = new HashMap<>();
    }

    public static StorageStub getInstance() {
        if (instance == null) {
            instance = new StorageStub();
        }
        return instance;
    }


    @Override
    public <T> T read(Class clazz) {
        return (T) this.copyObject(storage.get(clazz));
    }

    @Override
    public void write(Object object) {
        storage.put(object.getClass(), this.copyObject(object));
    }

    // @Override
    // public Object read(Class clazz) {
    //    return this.copyObject(storage.get(clazz));
    // }
    //
    // @Override
    // public void remove(Class clazz) {
    //    storage.remove(clazz);
    // }
    //
    // @Override
    // public void handleSourceChange(IEvent changeEvent) {
    //
    // }

    public void refreshInstantce() {
        instance = new StorageStub();
    }

    /**
     *
     */
    private Object copyObject(Object objSource) {
        Object objDest = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(objSource);
            oos.flush();
            oos.close();
            bos.close();
            byte[] byteData = bos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
            try {
                objDest = new ObjectInputStream(bais).readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objDest;

    }
}
