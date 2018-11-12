package com.t13g2.forum.testutil;

import com.t13g2.forum.model.forum.Comment;

//@@author HansKoh
/**
 * A utility class to help with building Comment objects.
 */
public class CommentBuilder {

    public static final String DEFAULT_COMMENT_CONTENT = "This is a new comment";

    private int threadId;
    private String commentContent;

    public CommentBuilder() {
        this.commentContent = DEFAULT_COMMENT_CONTENT;
    }

    /**
     * Initializes the CommentBuilder with the data of {@code commentToCopy}.
     */
    public CommentBuilder(Comment commentToCopy) {
        this.threadId = commentToCopy.getThreadId();
        this.commentContent = commentToCopy.getContent();
    }

    /**
     * Sets the {@code threadId} of the {@code Comment} that we are building.
     */
    public CommentBuilder withThreadId(int threadId) {
        this.threadId = threadId;
        return this;
    }

    /**
     * Sets the {@code cContent} of the {@code Comment} that we are building.
     */
    public CommentBuilder withContent(String cContent) {
        this.commentContent = cContent;
        return this;
    }

    /**
     * @return a comment object with default comment content
     */
    public Comment build() {
        this.commentContent = DEFAULT_COMMENT_CONTENT;
        return new Comment(threadId, commentContent);
    }
}
