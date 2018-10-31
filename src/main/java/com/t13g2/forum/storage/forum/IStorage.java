package com.t13g2.forum.storage.forum;

/**
 * storage interface.
 */
public interface IStorage {
    <T> T read(Class clazz);

    void write(Object object);
}
