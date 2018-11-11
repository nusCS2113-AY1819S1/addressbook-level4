package com.t13g2.forum.storage.forum;
//@@author Meowzz95

/**
 * Provides a way to access storage system
 */
public interface IStorage {
    /**
     * Reads given class from storage system
     *
     * @param clazz
     * @param <T>
     * @return data read from storage system
     */
    <T> T read(Class clazz);

    /**
     * Writes given class to storage system
     * @param object
     */
    void write(Object object);
}
