package com.t13g2.forum.storage.forum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

/**
 * json storage file.
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
