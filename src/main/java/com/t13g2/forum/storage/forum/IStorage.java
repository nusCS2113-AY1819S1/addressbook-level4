package com.t13g2.forum.storage.forum;

/**
 * IStorage
 */
public interface IStorage {
    <T> T read(Class clazz);

    void write(Object object);
}
