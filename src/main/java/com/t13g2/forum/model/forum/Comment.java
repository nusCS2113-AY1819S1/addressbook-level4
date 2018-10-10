package com.t13g2.forum.model.forum;

/**
 * Represents comment under thread in ForumBook
 */
public class Comment extends BaseModel {

    private int threadId;
    private String content;

    public int getThreadId() {
        return threadId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
