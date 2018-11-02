//@@Meowzz95
package com.t13g2.forum.storage;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.ForumBookStorage;
import com.t13g2.forum.testutil.ForumBookStorageBuilder;

public class ForumBookStorageTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void announceInited() {
        Assert.assertNotNull(new ForumBookStorageBuilder().build().getAnnouncements());
    }

    @Test
    public void commentInited() {
        Assert.assertNotNull(new ForumBookStorageBuilder().build().getComments());
    }

    @Test
    public void threadInited() {
        Assert.assertNotNull(new ForumBookStorageBuilder().build().getForumThreads());
    }

    @Test
    public void userInited() {
        Assert.assertNotNull(new ForumBookStorageBuilder().build().getUsers());
    }

    @Test
    public void modulesInited() {
        Assert.assertNotNull(new ForumBookStorageBuilder().build().getModules());
    }

    @Test
    public void announcementTest() {
        ForumBookStorageBuilder builder = new ForumBookStorageBuilder();

        ForumBookStorage fs = builder.build();
        Announcement announcement = new Announcement("title", "content");

        fs.getAnnouncements().getList().add(announcement);
        fs.getAnnouncements().setDirty();
        fs.commit();

        ForumBookStorage freshFs = builder.build();
        Assert.assertEquals(freshFs.getAnnouncements().getList().get(0).getId(), announcement.getId());
        Assert.assertEquals(freshFs.getAnnouncements().getList().get(0).getTitle(), announcement.getTitle());
        Assert.assertEquals(freshFs.getAnnouncements().getList().get(0).getContent(), announcement.getContent());
    }

    // @Test
    // public void announcementNoDirty() {
    //
    //     ForumBookStorageBuilder builder = new ForumBookStorageBuilder();
    //
    //     ForumBookStorage fs = builder.build();
    //     Announcement announcement = new Announcement("title", "content");
    //
    //     fs.getAnnouncements().getList().add(announcement);
    //     fs.commit();
    //
    //     ForumBookStorage freshFs = builder.build();
    //     thrown.expect(IndexOutOfBoundsException.class);
    //     freshFs.getAnnouncements().getList().get(0);
    // }

    @Test
    public void announcementNoCommit() {

        ForumBookStorageBuilder builder = new ForumBookStorageBuilder();

        ForumBookStorage fs = builder.build();
        Announcement announcement = new Announcement("title", "content");

        fs.getAnnouncements().getList().add(announcement);
        fs.getAnnouncements().setDirty();

        ForumBookStorage freshFs = builder.build();
        thrown.expect(IndexOutOfBoundsException.class);
        freshFs.getAnnouncements().getList().get(0);
    }

    @Test
    public void commentTest() {
        ForumBookStorageBuilder builder = new ForumBookStorageBuilder();

        ForumBookStorage fs = builder.build();
        Comment comment = new Comment();
        comment.setContent("a");
        comment.setThreadId(5);

        fs.getComments().getList().add(comment);
        fs.getComments().setDirty();
        fs.commit();

        ForumBookStorage freshFs = builder.build();
        Assert.assertEquals(freshFs.getComments().getList().get(0).getId(), comment.getId());
        Assert.assertEquals(freshFs.getComments().getList().get(0).getContent(), comment.getContent());
        Assert.assertEquals(freshFs.getComments().getList().get(0).getThreadId(), comment.getThreadId());
    }

    @Test
    public void commentNoCommit() {
        ForumBookStorageBuilder builder = new ForumBookStorageBuilder();

        ForumBookStorage fs = builder.build();
        Comment comment = new Comment();
        comment.setContent("a");
        comment.setThreadId(5);

        fs.getComments().getList().add(comment);
        fs.getComments().setDirty();


        ForumBookStorage freshFs = builder.build();
        thrown.expect(IndexOutOfBoundsException.class);
        freshFs.getComments().getList().get(0);
    }

    // @Test
    // public void commentNoDirty() {
    //     ForumBookStorageBuilder builder = new ForumBookStorageBuilder();
    //
    //     ForumBookStorage fs = builder.build();
    //     Comment comment = new Comment();
    //     comment.setContent("a");
    //     comment.setThreadId(5);
    //
    //     fs.getComments().getList().add(comment);
    //     fs.commit();
    //
    //
    //     ForumBookStorage freshFs = builder.build();
    //     thrown.expect(IndexOutOfBoundsException.class);
    //     freshFs.getComments().getList().get(0);
    // }

    @Test
    public void threadTest() {
        ForumBookStorageBuilder builder = new ForumBookStorageBuilder();

        ForumBookStorage fs = builder.build();
        ForumThread forumThread = new ForumThread();
        forumThread.setTitle("b");
        forumThread.setModuleId(6);

        fs.getForumThreads().getList().add(forumThread);
        fs.getForumThreads().setDirty();
        fs.commit();

        ForumBookStorage freshFs = builder.build();
        Assert.assertEquals(freshFs.getForumThreads().getList().get(0).getId(), forumThread.getId());
        Assert.assertEquals(freshFs.getForumThreads().getList().get(0).getTitle(), forumThread.getTitle());
        Assert.assertEquals(freshFs.getForumThreads().getList().get(0).getModuleId(), forumThread.getModuleId());
    }

    @Test
    public void threadNoCommit() {
        ForumBookStorageBuilder builder = new ForumBookStorageBuilder();

        ForumBookStorage fs = builder.build();
        ForumThread forumThread = new ForumThread();
        forumThread.setTitle("b");
        forumThread.setModuleId(6);

        fs.getForumThreads().getList().add(forumThread);
        fs.getForumThreads().setDirty();


        ForumBookStorage freshFs = builder.build();
        thrown.expect(IndexOutOfBoundsException.class);
        freshFs.getForumThreads().getList().get(0);
    }

    // @Test
    // public void threadNoDirty() {
    //     ForumBookStorageBuilder builder = new ForumBookStorageBuilder();
    //
    //     ForumBookStorage fs = builder.build();
    //     ForumThread forumThread = new ForumThread();
    //     forumThread.setTitle("b");
    //     forumThread.setModuleId(6);
    //
    //     fs.getForumThreads().getList().add(forumThread);
    //     fs.commit();
    //
    //
    //     ForumBookStorage freshFs = builder.build();
    //     thrown.expect(IndexOutOfBoundsException.class);
    //     freshFs.getForumThreads().getList().get(0);
    // }

    @Test
    public void userTest() {
        ForumBookStorageBuilder builder = new ForumBookStorageBuilder();

        ForumBookStorage fs = builder.build();
        User user = new User();
        user.setUsername("uname");
        user.setPassword("hehe");


        fs.getUsers().getList().add(user);
        fs.getUsers().setDirty();
        fs.commit();

        ForumBookStorage freshFs = builder.build();
        Assert.assertEquals(freshFs.getUsers().getList().get(0).getId(), user.getId());
        Assert.assertEquals(freshFs.getUsers().getList().get(0).getUsername(), user.getUsername());
        Assert.assertEquals(freshFs.getUsers().getList().get(0).getPassword(), user.getPassword());

    }

    @Test
    public void userNoCommit() {
        ForumBookStorageBuilder builder = new ForumBookStorageBuilder();

        ForumBookStorage fs = builder.build();
        User user = new User();
        user.setUsername("uname");
        user.setPassword("hehe");


        fs.getUsers().getList().add(user);
        fs.getUsers().setDirty();


        ForumBookStorage freshFs = builder.build();
        thrown.expect(IndexOutOfBoundsException.class);
        freshFs.getUsers().getList().get(0);
    }

    // @Test
    // public void userNoDirty() {
    //     ForumBookStorageBuilder builder = new ForumBookStorageBuilder();
    //
    //     ForumBookStorage fs = builder.build();
    //     User user = new User();
    //     user.setUsername("uname");
    //     user.setPassword("hehe");
    //
    //
    //     fs.getUsers().getList().add(user);
    //     fs.commit();
    //
    //     ForumBookStorage freshFs = builder.build();
    //     thrown.expect(IndexOutOfBoundsException.class);
    //     freshFs.getUsers().getList().get(0);
    // }

    @Test
    public void moduleTest() {
        ForumBookStorageBuilder builder = new ForumBookStorageBuilder();

        ForumBookStorage fs = builder.build();
        Module module = new Module("mTitle", "mCode");


        fs.getModules().getList().add(module);
        fs.getModules().setDirty();
        fs.commit();

        ForumBookStorage freshFs = builder.build();
        Assert.assertEquals(freshFs.getModules().getList().get(0).getId(), module.getId());
        Assert.assertEquals(freshFs.getModules().getList().get(0).getTitle(), module.getTitle());
        Assert.assertEquals(freshFs.getModules().getList().get(0).getModuleCode(), module.getModuleCode());
    }

    @Test
    public void moduleNoCommit() {
        ForumBookStorageBuilder builder = new ForumBookStorageBuilder();

        ForumBookStorage fs = builder.build();
        Module module = new Module("mTitle", "mCode");


        fs.getModules().getList().add(module);
        fs.getModules().setDirty();

        ForumBookStorage freshFs = builder.build();
        thrown.expect(IndexOutOfBoundsException.class);
        freshFs.getModules().getList().get(0);
    }

    // @Test
    // public void moduleNoDirty() {
    //     ForumBookStorageBuilder builder = new ForumBookStorageBuilder();
    //
    //     ForumBookStorage fs = builder.build();
    //     Module module = new Module("mTitle", "mCode");
    //
    //
    //     fs.getModules().getList().add(module);
    //     fs.commit();
    //
    //     ForumBookStorage freshFs = builder.build();
    //     thrown.expect(IndexOutOfBoundsException.class);
    //     freshFs.getModules().getList().get(0);
    // }
}
