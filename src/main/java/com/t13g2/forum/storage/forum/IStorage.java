package com.t13g2.forum.storage.forum;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 */
public interface IStorage {
    void write(OutputStream outputStream);
    void read(InputStream inputStream);
    void handleSourceChange(IEvent changeEvent);
}
