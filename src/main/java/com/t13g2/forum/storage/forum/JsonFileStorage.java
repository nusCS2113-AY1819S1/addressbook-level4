package com.t13g2.forum.storage.forum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
//@@author Meowzz95

/**
 * Provides a way to save/load application data in json format
 */
public class JsonFileStorage implements IStorage {
    private IEncryptor encryptor;

    public JsonFileStorage(IEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public JsonFileStorage() {
        //default encryptor
        this(new SimpleEncryptor());
    }

    /**
     * Reads given class from storage system
     *
     * @param clazz
     * @param <T>
     * @return data read from storage system
     */
    @Override
    public <T> T read(Class clazz) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(StorageMapping.getInstance().getFileName(clazz))));
            if (encryptor != null) {
                json = this.encryptor.decryptToString(json);
            }
            return (T) new Gson().fromJson(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Writes given class to storage system
     * @param object
     */
    @Override
    public void write(Object object) {
        String json = new Gson().toJson(object);
        if (encryptor != null) {
            json = encryptor.encryptToString(json);
        }
        try {
            Files.write(Paths.get(StorageMapping.getInstance().getFileName(object.getClass())), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
