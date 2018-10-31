package com.t13g2.forum.storage.forum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;

public class JsonFileStorage implements IStorage {
    @Override
    public <T> T read(Class clazz) {
        try {
            List<String> lines = Files.readAllLines(
                Paths.get(StorageMapping.getInstance().getFileName(clazz))
            );
            String json = String.join("", lines);
            return (T) new Gson().fromJson(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void write(Object object) {
        String json = new Gson().toJson(object);
        try {
            Files.write(Paths.get(StorageMapping.getInstance().getFileName(object.getClass())), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
