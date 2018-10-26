//@@Meowzz95
package com.t13g2.forum.storage;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;

import com.t13g2.forum.storage.forum.FileStorage;
import com.t13g2.forum.storage.forum.StorageMapping;

public class FileStorageTest {
    @Test
    public void readAndWrite() {
        FileStorage fileStorage = new FileStorage();
        StorageMapping.getInstance().addMapping(DummyClass.class, "dummyClass");
        DummyClass dummy = new DummyClass();
        dummy.setName("hehe");
        fileStorage.write(dummy);

        DummyClass dummyFromDisk = (DummyClass) fileStorage.read(DummyClass.class);
        Assert.assertEquals(dummy.getName(), dummyFromDisk.getName());

        fileStorage.remove(DummyClass.class);

    }


    static class DummyClass implements Serializable {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
