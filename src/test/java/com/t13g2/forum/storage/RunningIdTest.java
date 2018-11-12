package com.t13g2.forum.storage;

import org.junit.Assert;
import org.junit.Test;

import com.t13g2.forum.storage.forum.RunningId;

/**
 * Tests running Id
 */
public class RunningIdTest {
    @Test
    public void testRunningId() {
        int currentId = RunningId.getInstance().nextId();
        int nextId = RunningId.getInstance().nextId();
        Assert.assertEquals(nextId, currentId + 1);
    }
}
