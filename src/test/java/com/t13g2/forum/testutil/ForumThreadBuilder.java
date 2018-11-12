package com.t13g2.forum.testutil;

import com.t13g2.forum.model.forum.ForumThread;

//@@author HansKoh
/**
 * A utility class to help with building ForumThread objects.
 */
public class ForumThreadBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS2113";
    public static final String DEFAULT_THREAD_TITLE = "This is a new thread";

    private int moduleId;
    private String threadTitle;

    public ForumThreadBuilder() {
        this.threadTitle = DEFAULT_THREAD_TITLE;
    }

    /**
     * Initializes the ForumThreadBuilder with the data of {@code forumThreadToCopy}.
     */
    public ForumThreadBuilder(ForumThread forumThreadToCopy) {
        this.moduleId = forumThreadToCopy.getModuleId();
        this.threadTitle = forumThreadToCopy.getTitle();
    }

    /**
     * Sets the {@code mName} of the {@code Module} that we are building.
     */
    public ForumThreadBuilder withModuleId(int moduleId) {
        this.moduleId = moduleId;

        return this;
    }

    /**
     * Sets the {@code mTitle} of the {@code Module} that we are building.
     */
    public ForumThreadBuilder withTitle(String tTitle) {
        this.threadTitle = tTitle;
        return this;
    }

    public ForumThread build() {
        return new ForumThread(moduleId, threadTitle);
    }
}
