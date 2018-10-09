package com.t13g2.forum.storage.forum;

import java.io.InputStream;
import java.io.OutputStream;

public interface IStorage {
    public void writeToDisk(OutputStream outputStream);
    public void readFromDisk(InputStream inputStream);
    public void handleChange(IEvent changeEvent);
}
