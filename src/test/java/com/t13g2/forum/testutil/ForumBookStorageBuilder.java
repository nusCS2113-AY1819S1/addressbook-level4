//@@author Meowzz95
package com.t13g2.forum.testutil;

import com.t13g2.forum.storage.StorageStub;
import com.t13g2.forum.storage.forum.ForumBookStorage;

/**
 *
 */
public class ForumBookStorageBuilder {

    public ForumBookStorageBuilder() {
        StorageStub.getInstance().refreshInstantce();
    }

    public ForumBookStorage build() {
        return new ForumBookStorage(StorageStub.getInstance());
    }
}
