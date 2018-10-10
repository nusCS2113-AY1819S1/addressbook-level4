package com.t13g2.forum.storage.forum;

/**
 *
 */
public interface IStorage {
    void write(Object object);

    Object read(Class clazz);

    void handleSourceChange(IEvent changeEvent);
}
