//@@Meowzz95
package com.t13g2.forum.testutil;

import com.t13g2.forum.storage.IStorageStub;
import com.t13g2.forum.storage.forum.ForumBookStorage;

/**
 *
 */
public class ForumBookStorageBuilder {

    public ForumBookStorageBuilder() {
        IStorageStub.getInstance().refreshInstantce();
    }

    public ForumBookStorage build() {
        return new ForumBookStorage(IStorageStub.getInstance());
    }
}
