//@@Meowzz95
package com.t13g2.forum.storage;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;

import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.storage.forum.AnnouncementStorage;
import com.t13g2.forum.storage.forum.IStorage;
import com.t13g2.forum.storage.forum.JsonFileStorage;
import com.t13g2.forum.storage.forum.StorageMapping;

public class FileStorageTest {
    @Test
    public void readAndWrite() {
        JsonFileStorage fileStorage = new JsonFileStorage();
        StorageMapping.getInstance().addMapping(DummyClass.class, "dummyClass");
        DummyClass dummy = new DummyClass();
        dummy.setName("hehe");
        fileStorage.write(dummy);

        DummyClass dummyFromDisk = fileStorage.read(DummyClass.class);
        Assert.assertEquals(dummy.getName(), dummyFromDisk.getName());

    }

    @Test
    public void readWriteWithActualStorage() {
        IStorage fileStorage = new JsonFileStorage();
        Announcement announcement = new Announcement("hey", "ha");
        AnnouncementStorage announcementStorage = new AnnouncementStorage();
        announcementStorage.getList().add(announcement);
        fileStorage.write(announcementStorage);

        AnnouncementStorage readBack = fileStorage.read(AnnouncementStorage.class);
        Announcement readBackAnnouncement = readBack.getList().get(0);
        Assert.assertEquals(announcement.getId(), readBackAnnouncement.getId());
        Assert.assertEquals(announcement.getTitle(), readBackAnnouncement.getTitle());
        Assert.assertEquals(announcement.getContent(), readBackAnnouncement.getContent());


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
