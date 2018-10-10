package com.t13g2.forum.storage.forum;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 */
public class FileStorage implements IStorage {


    @Override
    public void write(Object object) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(StorageMapping.getInstance().getFileName(object.getClass()));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object read(Class clazz) {
        try {
            FileInputStream fileInputStream = new FileInputStream(StorageMapping.getInstance().getFileName(clazz));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object result = objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void handleSourceChange(IEvent changeEvent) {

    }
}
