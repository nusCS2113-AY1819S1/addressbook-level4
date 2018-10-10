package com.t13g2.forum.model.forum;

/**
 * Represents comment under thread in ForumBook
 */
public class Comment extends BaseModel {
    public static final String MESSAGE_COMMENT_CONSTRAINTS =
            "Comment should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String COMMENT_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private int threadId;
    private String content;

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
