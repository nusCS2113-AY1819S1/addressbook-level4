package com.t13g2.forum.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.t13g2.forum.storage.forum.IEvent;
import com.t13g2.forum.storage.forum.IStorage;

/**
 *
 */
public class IStorageStub implements IStorage {
    private static IStorageStub _instance;

    private HashMap<Class, Object> storage;

    private IStorageStub() {
        storage = new HashMap<>();
    }

    public static IStorageStub getInstance() {
        if (_instance == null) {
            _instance = new IStorageStub();
        }
        return _instance;
    }

    @Override
    public void write(Object object) {
        storage.put(object.getClass(), this.copyObject(object));
    }

    @Override
    public Object read(Class clazz) {
        return this.copyObject(storage.get(clazz));
    }

    @Override
    public void remove(Class clazz) {
        storage.remove(clazz);
    }

    @Override
    public void handleSourceChange(IEvent changeEvent) {

    }

    public void refreshInstantce() {
        _instance = new IStorageStub();
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
