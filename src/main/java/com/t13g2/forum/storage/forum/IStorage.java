package com.t13g2.forum.storage.forum;

/**
<<<<<<< HEAD
 * IStorage
=======
 * storage interface.
>>>>>>> da8292eaadbda97d0f97d93a465a249ed24a7543
 */
public interface IStorage {
    <T> T read(Class clazz);

    void write(Object object);
}
